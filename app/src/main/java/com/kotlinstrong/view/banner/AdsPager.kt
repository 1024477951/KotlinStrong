package com.kotlinstrong.view.banner

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class AdsPager(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    constructor(context:Context):this(context,null)

    init {
        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}