package com.kotlinstrong.databinding.imageview

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.kotlinstrong.R
import com.kotlinstrong.stronglib.util.glide.GlideAppUtils

/**  ImageView 视图绑定 */


@BindingAdapter("article_url")
fun setArticleUrl(imageView: ImageView, url: String?){
    url?.let { GlideAppUtils.load(imageView, it, R.mipmap.empty) }
}

@BindingAdapter("article_ads_src")
fun setArticleAdsSrc(imageView: ImageView, url: String?){
    url?.let { GlideAppUtils.load(imageView, it, R.mipmap.empty) }
}