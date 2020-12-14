package com.kotlinstrong.stronglib.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlinstrong.stronglib.bean.BaseBindItem
import com.kotlinstrong.stronglib.binding.BaseBindViewHolder
import com.kotlinstrong.stronglib.listener.Function
import com.kotlinstrong.stronglib.listener.LongFunction

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

    /** 替换数据源 */
    fun setNewList(newList: MutableList<T>) {
        this.list = newList
        notifyDataSetChanged()
    }

    fun addItems(list: MutableList<T>){
        this.list?.addAll(list)
        notifyItemRangeInserted(itemCount - list.size - 1, list.size)
    }

    fun addItem(bean: T?){
        if (bean != null) {
            list?.add(bean)
            notifyItemInserted(itemCount - 1)
        }
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
        getItem(position).onViewRecycled()
    }

}