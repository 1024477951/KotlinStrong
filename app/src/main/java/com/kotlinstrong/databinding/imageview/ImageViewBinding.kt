package com.kotlinstrong.databinding.imageview

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.kotlinstrong.R
import com.kotlinstrong.stronglib.util.glide.GlideAppUtils

/**  ImageView 视图绑定 */

@BindingAdapter("setResId")
fun setResId(imageView: ImageView, resId: Int){
    GlideAppUtils.load(imageView, resId, R.mipmap.empty)
}