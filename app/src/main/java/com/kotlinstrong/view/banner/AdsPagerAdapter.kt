package com.kotlinstrong.view.banner

import com.kotlinstrong.stronglib.base.BaseAdapter

open class AdsPagerAdapter<T> : BaseAdapter<T>() {

    var canLoop: Boolean = true

    override fun getItemCount(): Int {
        return if (canLoop) 3 * list!!.size else list!!.size
    }

}