package com.kotlinstrong.home.item

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableField
import com.kotlinstrong.R
import com.kotlinstrong.bean.AppMenuBean
import com.kotlinstrong.databinding.ItemAppMenuTextBinding
import com.kotlinstrong.stronglib.bean.BaseBindItem
import com.kotlinstrong.stronglib.binding.BaseBindViewHolder

class MenuTitleBindItem(@LayoutRes layoutId: Int) : BaseBindItem(layoutId) {

    private val observableField = ObservableField<AppMenuBean>()

    constructor(bean: AppMenuBean) : this(R.layout.item_app_menu_text){
        observableField.set(bean)
    }

    override fun onBindViewHolder(holder: BaseBindViewHolder, position: Int) {
        val binding = holder.binding as ItemAppMenuTextBinding

        val bean = observableField.get()

        binding.data = bean
    }

}