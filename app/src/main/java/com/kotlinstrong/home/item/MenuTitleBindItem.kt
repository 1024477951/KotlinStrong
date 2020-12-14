package com.kotlinstrong.home.item

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableField
import com.kotlinstrong.R
import com.kotlinstrong.bean.AppMenuBean
import com.kotlinstrong.stronglib.bean.BaseBindItem

class MenuTitleBindItem(@LayoutRes layoutId: Int) : BaseBindItem(layoutId) {

    val titleField = ObservableField<String>()

    constructor(bean: AppMenuBean) : this(R.layout.item_app_menu_text){
        titleField.set(bean.title)
    }

    override fun onBindViewHolder(position: Int) {

    }

}