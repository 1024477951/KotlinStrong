package com.strong.ui.viewbinding.imageview

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.strong.R
import com.strong.utils.glide.GlideAppUtils

/**  ImageView 视图绑定 */
@BindingAdapter("setResId")
fun setResId(view: ImageView, resId: Int){
    GlideAppUtils.load(view, resId, R.mipmap.icon_def_empty)
}
@BindingAdapter("setImageUrl","imgRadius")
fun setImageUrl(view: ImageView, url: String, radius: Int){
    GlideAppUtils.load(view, url, R.mipmap.icon_def_empty,radius)
}
@BindingAdapter("setCircleUrl")
fun setCircleUrl(view: ImageView, url: String){
    GlideAppUtils.loadCircle(view, url, R.mipmap.icon_def_empty)
}