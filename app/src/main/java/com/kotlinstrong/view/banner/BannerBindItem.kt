package com.kotlinstrong.view.banner

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.R
import com.kotlinstrong.bean.Article
import com.kotlinstrong.databinding.ItemHeadAdsBinding
import com.kotlinstrong.databinding.LayoutHeadAdsBinding
import com.kotlinstrong.stronglib.bean.BaseBindItem
import com.kotlinstrong.stronglib.binding.BaseBindViewHolder
import com.kotlinstrong.stronglib.listener.Function

class BannerBindItem(@LayoutRes layoutId: Int) : BaseBindItem(layoutId) {

    private val observableField = ObservableField<String>()

    constructor(url: String) : this(R.layout.item_head_ads){
        observableField.set(url)
    }

    override fun onBindViewHolder(holder: BaseBindViewHolder, position: Int) {
        val binding = holder.binding as ItemHeadAdsBinding

        val url = observableField.get()

        binding.data = url
        binding.click = object : Function<String>{
            override fun call(view: View, t: String) {
                ToastUtils.showShort("banner click success")
            }
        }
    }
}