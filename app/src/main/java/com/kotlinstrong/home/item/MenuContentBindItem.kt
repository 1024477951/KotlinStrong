package com.kotlinstrong.home.item

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.kotlinstrong.R
import com.kotlinstrong.bean.AppMenuBean
import com.kotlinstrong.stronglib.bean.BaseBindItem

class MenuContentBindItem(@LayoutRes layoutId: Int) : BaseBindItem(layoutId) {

    val titleField = ObservableField<String>()
    val resField = ObservableInt()

    constructor(bean: AppMenuBean) : this(R.layout.item_app_menu_child){
        titleField.set(bean.title)
        resField.set(bean.resId)
    }

    override fun onBindViewHolder(position: Int) {

    }
}