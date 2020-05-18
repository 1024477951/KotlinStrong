package com.kotlinstrong.home.item

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableField
import com.kotlinstrong.R
import com.kotlinstrong.bean.AppMenuBean
import com.kotlinstrong.databinding.ItemAppMenuChildBinding
import com.kotlinstrong.stronglib.bean.BaseBindItem
import com.kotlinstrong.stronglib.binding.BaseBindViewHolder
import com.kotlinstrong.stronglib.listener.Function

class MenuContentBindItem(@LayoutRes layoutId: Int) : BaseBindItem(layoutId) {

    private val observableField = ObservableField<AppMenuBean>()

    private var click: Function<AppMenuBean>? = null

    constructor(bean: AppMenuBean, click: Function<AppMenuBean>) : this(R.layout.item_app_menu_child){
        observableField.set(bean)
        this.click = click
    }

    override fun onBindViewHolder(holder: BaseBindViewHolder, position: Int) {
        val binding = holder.binding as ItemAppMenuChildBinding

        val bean = observableField.get()

        binding.data = bean
        if (click != null) {
            binding.click = click
        }
    }
}