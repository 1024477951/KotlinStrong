package com.kotlinstrong.home.item

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.R
import com.kotlinstrong.bean.Article
import com.kotlinstrong.databinding.ItemTextBinding
import com.kotlinstrong.stronglib.bean.BaseBindItem
import com.kotlinstrong.stronglib.binding.BaseBindViewHolder
import com.kotlinstrong.stronglib.listener.Function

class ArticleContent1BindItem(@LayoutRes layoutId: Int) : BaseBindItem(layoutId) {

    private val observableField = ObservableField<Article>()

    constructor(article: Article) : this(R.layout.item_text){
        observableField.set(article)
    }

    override fun onBindViewHolder(holder: BaseBindViewHolder, position: Int) {
        val binding = holder.binding as ItemTextBinding

        val article = observableField.get()

        binding.data = article
        binding.click = object : Function<Article> {
            override fun call(view: View, t: Article) {
                ToastUtils.showShort("content1 click success")
            }
        }
    }

}