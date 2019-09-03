package com.kotlinstrong.view.banner

import android.content.Context
import android.util.Log
import com.kotlinstrong.stronglib.base.BaseAdapter

open class AdsPagerAdapter<T>(context: Context?, variableId: Int, layoutId: Int,isLoop: Boolean) : BaseAdapter<T>(context, variableId, layoutId) {

    private var canLoop: Boolean = isLoop

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        val realPosition = position % list!!.size
        holder.bindTo(list!![realPosition])
    }

    override fun getItemCount(): Int {
        return if (canLoop) Integer.MAX_VALUE else list!!.size
    }

}