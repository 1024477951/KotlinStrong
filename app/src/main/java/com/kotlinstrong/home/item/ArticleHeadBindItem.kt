package com.kotlinstrong.home.item

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.R
import com.kotlinstrong.bean.Article
import com.kotlinstrong.databinding.LayoutHeadAdsBinding
import com.kotlinstrong.stronglib.bean.BaseBindItem
import com.kotlinstrong.stronglib.binding.BaseBindViewHolder
import com.kotlinstrong.stronglib.listener.Function

class ArticleHeadBindItem(@LayoutRes layoutId: Int) : BaseBindItem(layoutId) {

    private val observableField = ObservableField<MutableList<String>>()

    constructor(list: MutableList<String>) : this(R.layout.layout_head_ads){
        observableField.set(list)
    }

    override fun onBindViewHolder(holder: BaseBindViewHolder, position: Int) {
        val binding = holder.binding as LayoutHeadAdsBinding

        val list = observableField.get()

        binding.data = list?.let { Article(-1, it) }
        binding.click = object : Function<Article>{
            override fun call(view: View, t: Article) {
                ToastUtils.showShort("head click success")
            }
        }
    }

}