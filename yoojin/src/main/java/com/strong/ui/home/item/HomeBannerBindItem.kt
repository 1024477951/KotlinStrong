package com.strong.ui.home.item

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableField
import com.strong.R
import com.strong.databinding.ItemHomeBannerBinding
import com.strong.ui.adapter.BaseBindItem
import com.strong.ui.home.bean.BannerBean

class HomeBannerBindItem(@LayoutRes layoutId: Int) : BaseBindItem<ItemHomeBannerBinding>(layoutId) {

    val bannerField = ObservableField<MutableList<BannerBean.BannerData>>()

    constructor(banners: MutableList<BannerBean.BannerData>) : this(R.layout.item_home_banner){
        bannerField.set(banners)
    }

    override fun onBindViewHolder(position: Int, binding: ItemHomeBannerBinding) {
        binding.item = this
    }

}