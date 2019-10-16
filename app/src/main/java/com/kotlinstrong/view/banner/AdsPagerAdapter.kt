package com.kotlinstrong.view.banner

import android.content.Context
import com.kotlinstrong.stronglib.base.BaseAdapter

open class AdsPagerAdapter<T>(context: Context?, variableId: Int, layoutId: Int) : BaseAdapter<T>(context, variableId, layoutId) {

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        val realPosition = position % list!!.size
        holder.bindTo(list!![realPosition])
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

}