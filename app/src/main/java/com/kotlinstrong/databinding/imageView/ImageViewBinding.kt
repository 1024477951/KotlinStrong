package com.kotlinstrong.databinding.imageView

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.kotlinstrong.R
import com.kotlinstrong.stronglib.util.glide.GlideAppUtils

/**  ImageView 视图绑定 */


@BindingAdapter("article_url")
fun setArticleUrl(imageView: ImageView, url: String){
    GlideAppUtils.load(imageView,url, R.mipmap.empty)
}