package com.strong.ui.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/** recyclerView绑定适配器 * */
open class BaseAdapter : RecyclerView.Adapter<BaseBindViewHolder> {

    constructor() {
        this.list = ArrayList()
    }

    var list: MutableList<Any>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindViewHolder {
        return BaseBindItem.createViewHolder(parent,viewType)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun getItem(position: Int): BaseBindItem<ViewDataBinding> {
        return list!![position] as BaseBindItem<ViewDataBinding>
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

    /** 替换数据源 */
    fun setItems(newList: MutableList<Any>) {
        this.list?.clear()
        this.list?.addAll(newList)
        notifyDataSetChanged()
    }
    fun setItem(bean: Any) {
        this.list?.clear()
        this.list?.add(bean)
        notifyDataSetChanged()
    }

    fun addItems(list: MutableList<Any>){
        this.list?.addAll(list)
        notifyItemRangeInserted(itemCount - list.size - 1, list.size)
    }

    fun addItem(bean: Any?){
        if (bean != null) {
            list?.add(bean)
            notifyItemInserted(itemCount - 1)
        }
    }

    fun addItem(index: Int,bean: Any?){
        if (bean != null) {
            list?.add(index,bean)
            notifyItemInserted(index)
        }
    }

}