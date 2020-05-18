package com.kotlinstrong.stronglib.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kotlinstrong.stronglib.bean.BaseBindItem
import com.kotlinstrong.stronglib.binding.BaseBindViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/** recyclerView绑定适配器 * */
open class BaseAdapter<T : BaseBindItem> : RecyclerView.Adapter<BaseBindViewHolder> {

    /**
     *  无脑 notifyDataSetChanged()缺点：
     *      不会触发RecyclerView的动画（删除、新增、位移、change动画）
     *      性能较低，刷新了整个RecyclerView , 极端情况下：新老数据集一模一样，效率是最低的。
     *
     *  notifyItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload)
     *      如果payloads list 不为空，那么当前绑定了旧数据的ViewHolder 和Adapter， 可以使用 payload的数据进行一次 高效的部分更新。
如果payload 是空的，Adapter必须进行一次完整绑定（调用两参方法）
        关于“白光一闪”onChange动画， public Object getChangePayload() 这个方法返回不为null的话，onChange采用Partial bind，也就是部分更新，
        就不会出现了。反之就有。
     * */

    constructor() {
        this.list = ArrayList<T>()
    }

    /** 首次加载数据 */
    fun setNewList(newList: MutableList<T>) {
        this.list = newList
        notifyDataSetChanged()
    }

    protected var list: MutableList<T>? = null
    private var dataVersion: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindViewHolder {
        return BaseBindItem.createViewHolder(parent,viewType)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun getItem(position: Int): T {
        return list!![position]
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun onBindViewHolder(holder: BaseBindViewHolder, position: Int) {
        getItem(position).bind(holder,position)
    }

    override fun onViewRecycled(holder: BaseBindViewHolder) {
        super.onViewRecycled(holder)
        val position = holder.adapterPosition
        if (position < 0 || position >= itemCount) {
            return
        }
        getItem(position).onViewRecycled(holder)
    }

    @SuppressLint("StaticFieldLeak", "CheckResult")
    fun setList(update: MutableList<T>?, runnable: Runnable?) {
        dataVersion++
        if (update == null) {
            val oldSize = list!!.size
            list = ArrayList<T>()
            notifyItemRangeRemoved(0, oldSize)
        } else {
            val startVersion = dataVersion
            val oldItems = ArrayList<T>(list as ArrayList)
            Observable.just(DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return oldItems.size
                }

                override fun getNewListSize(): Int {
                    return update.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = oldItems[oldItemPosition]
                    val newItem = update[newItemPosition]
                    return oldItem != null && oldItem == newItem
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = oldItems[oldItemPosition]
                    val newItem = update[newItemPosition]
                    //判断item的内容是否有变化,我的理解是只有notifyItemRangeChanged()才会调用
                    return oldItem != null && oldItem == newItem
                }
            })).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { diffResult ->
                    if (startVersion != dataVersion) {
                        return@subscribe
                    }
                    list = ArrayList(update)
                    diffResult.dispatchUpdatesTo(this@BaseAdapter)
                    /*
                     * public static DiffResult calculateDiff(Callback cb, boolean detectMoves)
                     * 设置给Adapter之前，先调用DiffUtil.calculateDiff()方法，计算出新老数据集转化的最小更新集
                     * 第一个参数是DiffUtil.Callback对象
                     * 第二个参数代表是否检测Item的移动，改为false算法效率更高
                     */
                    runnable?.run()//更新ui
                }
        }
    }

    fun remove(index: Int) {
        this.list!!.removeAt(index)
        notifyItemRangeRemoved(index, 1)
    }

}