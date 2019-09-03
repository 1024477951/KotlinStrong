package com.kotlinstrong.stronglib.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.kotlinstrong.stronglib.listener.LongFunction
import com.kotlinstrong.stronglib.listener.Function
import com.kotlinstrong.stronglib.listener.ViewMap
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/** recyclerView绑定适配器 * */
open class BaseAdapter <T>() : RecyclerView.Adapter<BaseAdapter<T>.RVViewHolder>() {

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

    constructor(context: Context?, variableId: Int, viewMap: ViewMap<T>?) : this() {
        this.context = context
        this.list = ArrayList<T>()
        this.event = SparseArray()
        this.longevent = SparseArray()
        this.viewMap = viewMap
        this.variableId = variableId
    }

    constructor(context: Context?, variableId: Int, layoutId: Int) : this() {
        this.context = context
        this.list = ArrayList<T>()
        this.event = SparseArray()
        this.longevent = SparseArray()
        this.variableId = variableId
        this.layoutId = layoutId
    }
    /** 首次加载数据 */
    fun setNewList(newList: MutableList<T>) {
        this.list = newList
        notifyDataSetChanged()
    }

    private var context: Context? = null
    protected var list: MutableList<T>? = null
    private var variableId: Int = 0
    private var layoutId: Int = 0
    private var dataVersion: Int = 0
    private var viewMap: ViewMap<T>? = null
    private var event: SparseArray<Function<T>>? = null
    private var longevent: SparseArray<LongFunction<T>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return RVViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewMap?.layoutId(list!![position]) ?: layoutId
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.bindTo(list!![position])
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.size > 0) {
            holder.payloadsbindTo(list!![position])
            /* Bundle payload = (Bundle) payloads.get(0);
               for (String key : payload.keySet()) {
                switch (key) {
                    case "desc":
                        //这里可以用payload里的数据，不过data也是新的 也可以用
                        holder.tv2.setText(mDatas.get(position).getDesc());
                        break;
                    case "name":
                        holder.iv.setImageResource(payload.getInt(key));
                        break;
                }
              }
             */
        } else {
            onBindViewHolder(holder, position)
        }
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
            /*
               自动计算新老数据集的差异，并根据差异情况，自动调用刷新方法，而不是直接全部刷新
               DiffUtil内部采用的 Myers’s difference 差分算法，但该算法不能检测移动的item，所以Google在其基础上改进支持检测移动项目，
               但是检测移动项目，会更耗性能,如果我们的list过大，会很耗时，所以我们应该将获取DiffResult的过程放到子线程获取异步中，
               并在主线程中更新RecyclerView
               DiffUtil很适合下拉刷新这种场景
              */
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
                    //用来判断 两个对象是否是相同的Item
                    return oldItem != null && oldItem == newItem
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = oldItems[oldItemPosition]
                    val newItem = update[newItemPosition]
                    //判断item的内容是否有变化,我的理解是只有notifyItemRangeChanged()才会调用
                    return oldItem != null && oldItem == newItem
                }

                //override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                    /* 比较数据，实现定向刷新中的部分刷新，效率最高 */
                    //Bundle payload = new Bundle()
                    //if (!oldItem.getDesc().equals(newItem.getDesc())) {
                    //    payload.putString("decs", newItem.getDesc())
                    //}
                    //if (oldItem.getName() != newItem.getName()) {
                    //    payload.putInt("name", newItem.getName())
                    //}
                    //if (payload.size() == 0)
                    //    return null
                    //return payload
                //    return super.getChangePayload(oldItemPosition, newItemPosition)
                //}
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

    fun setList(update: MutableList<T>?, needDiff: Boolean) {
        if (needDiff) {
            setList(update, null)
        } else {
            if (update == null) {
                this.list = ArrayList()
            } else {
                this.list = update
            }
            notifyItemRangeChanged(0, list!!.size, list)
        }
    }

    fun addList(@IntRange(from = 0) position: Int, add: Collection<T>?) {
        if (add != null && add.isNotEmpty()) {
            list!!.addAll(position, add)
            notifyItemRangeInserted(position, add.size)
        }
    }

    fun addList(add: Collection<T>?) {
        if (add != null && add.isNotEmpty()) {
            list!!.addAll(add)
            notifyItemRangeInserted(list!!.size - add.size, add.size)
        }
    }

    fun addData(index: Int, add: T) {
        list!!.add(index, add)
        notifyDataChanged()
    }

    fun addData(add: T) {
        list!!.add(add)
        notifyDataChanged(add)
    }

    fun remove(index: Int) {
        this.list!!.removeAt(index)
        notifyItemRangeRemoved(index, 1)
    }

    fun remove(del: T) {
        if (this.list!!.contains(del)) {
            remove(this.list!!.indexOf(del))
        }
    }

    fun notifyDataChanged(t: T) {
        if (list!!.contains(t)) {
            notifyItemChanged(list!!.indexOf(t), list!![list!!.indexOf(t)])
        }
    }

    fun notifyDataChanged() {
        notifyItemRangeChanged(0, list!!.size, list)
    }

    inner class RVViewHolder(var binding: ViewDataBinding) : BaseViewHolder(binding.root) {

        fun bindTo(value: T) {
            binding.setVariable(variableId, value)
            for (i in 0 until event!!.size()) {
                binding.setVariable(event!!.keyAt(i), event!!.valueAt(i))
            }
            for (i in 0 until longevent!!.size()) {
                binding.setVariable(longevent!!.keyAt(i), longevent!!.valueAt(i))
            }
            binding.executePendingBindings()
        }

        fun payloadsbindTo(value: T) {
            binding.setVariable(variableId, value)
            for (i in 0 until event!!.size()) {
                binding.setVariable(event!!.keyAt(i), event!!.valueAt(i))
            }
            for (i in 0 until longevent!!.size()) {
                binding.setVariable(longevent!!.keyAt(i), longevent!!.valueAt(i))
            }
            binding.executePendingBindings()
        }
    }

    fun addEvent(variableId: Int, function: Function<T>) {
        event!!.put(variableId, function)
    }

    fun addLongEvent(variableId: Int, function: LongFunction<T>) {
        longevent!!.put(variableId, function)
    }

}