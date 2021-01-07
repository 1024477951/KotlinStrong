package com.strong.ui.adapter

import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindItem<V : ViewDataBinding> {

    val viewType: Int

    abstract fun onBindViewHolder(position: Int,binding: V)

    constructor(@LayoutRes layoutId: Int) {
        viewType = layoutId.hashCode() + this.hashCode()
        layoutsArray.put(viewType, layoutId)
    }

    constructor(view: View) {
        viewType = view.hashCode() + this.hashCode()
        viewsArray.put(viewType, view)
    }

    companion object {

        private val layoutsArray = SparseIntArray()
        private val viewsArray = SparseArray<View>()

        fun createViewHolder(parent: ViewGroup, viewType: Int): BaseBindViewHolder {

            var binding: ViewDataBinding? = null
            val inflater = LayoutInflater.from(parent.context)
            val layoutId = layoutsArray.get(viewType,-1)
            val view = viewsArray.get(viewType)
            when{
                layoutId != -1 -> binding = DataBindingUtil.inflate(inflater, layoutId, parent, false)
                view != null -> binding = DataBindingUtil.bind(view)!!
            }
            return BaseBindViewHolder(binding!!)
        }
    }

    fun bind(holder: BaseBindViewHolder, position: Int) {
        onBindViewHolder(position,holder.binding as V)
    }

    /** 确认被回收，且要放进 RecyclerViewPool 中前 */
    fun onViewRecycled() {}

    /** --RecyclerView 回收机制--
        RecyclerView 在工作时会先将移出屏幕的 ViewHolder 放进一级缓存中，当一级缓存空间已满才会考虑将一级缓存中已有的 ViewHolder 移到 RecyclerViewPool 中去
        所以，并不是所有刚被移出屏幕的 ViewHoder 都会回调该方法
        官方建议我们可以在这里释放一些耗内存资源的工作，如 bitmap
     */

}