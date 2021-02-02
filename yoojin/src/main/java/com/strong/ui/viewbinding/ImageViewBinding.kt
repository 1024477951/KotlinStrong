package com.strong.ui.viewbinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.strong.R
import com.strong.utils.glide.GlideAppUtils

/**  ImageView 视图绑定 */
@BindingAdapter("setResId")
fun ImageView.setResId(resId: Int){
    GlideAppUtils.load(this, resId, R.mipmap.icon_def_empty)
}
@BindingAdapter("setImageUrl","imgRadius")
fun ImageView.setImageUrl(url: String, radius: Int){
    GlideAppUtils.load(this, url, R.mipmap.icon_def_empty,radius)
}
@BindingAdapter("setCircleUrl")
fun ImageView.setCircleUrl(url: String){
    GlideAppUtils.loadCircle(this, url, R.mipmap.icon_def_empty)
}