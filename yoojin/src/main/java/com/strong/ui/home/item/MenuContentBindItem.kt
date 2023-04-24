package com.strong.ui.home.item

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.strong.R
import com.strong.databinding.ItemHomeMenuChildBinding
import com.strong.ui.adapter.BaseBindItem
import com.strong.ui.home.click.FunctionClick
import com.strong.ui.home.bean.MenuBean

class MenuContentBindItem(@LayoutRes layoutId: Int) :
    BaseBindItem<ItemHomeMenuChildBinding>(layoutId) {

    val titleField = ObservableField<String>()
    val resField = ObservableInt()
    private lateinit var function: FunctionClick

    constructor(bean: MenuBean, function: FunctionClick) : this(R.layout.item_home_menu_child) {
        titleField.set(bean.title)
        resField.set(bean.resId)
        this.function = function
    }

    override fun onBindViewHolder(position: Int, binding: ItemHomeMenuChildBinding) {
        binding.item = this
    }

    fun itemClick() {
        function.click(resField.get())
    }

}