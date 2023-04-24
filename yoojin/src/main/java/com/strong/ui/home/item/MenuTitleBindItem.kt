package com.strong.ui.home.item

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableField
import com.strong.R
import com.strong.databinding.ItemHomeMenuTextBinding
import com.strong.ui.adapter.BaseBindItem
import com.strong.ui.home.bean.MenuBean

class MenuTitleBindItem(@LayoutRes layoutId: Int) :
    BaseBindItem<ItemHomeMenuTextBinding>(layoutId) {

    val titleField = ObservableField<String>()

    constructor(bean: MenuBean) : this(R.layout.item_home_menu_text) {
        titleField.set(bean.title)
    }

    override fun onBindViewHolder(position: Int, binding: ItemHomeMenuTextBinding) {
        binding.item = this
    }

}