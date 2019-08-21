package com.kotlinstrong.databinding.imageView

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.kotlinstrong.R
import com.kotlinstrong.stronglib.util.glide.GlideAppUtils

class ImageViewBinding {
    companion object {
        val TAG: String = "ImageViewBinding"

        @BindingAdapter("article_url")
        @JvmStatic
        fun setArticleTitle(imageView: ImageView, url: String){
            GlideAppUtils.load(imageView,url, R.mipmap.empty)
        }
    }
}