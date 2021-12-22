package com.strong.viewbinding

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
fun ImageView.setImageUrl(url: String?, radius: Int){
    url?.let {
        GlideAppUtils.load(this, it, R.mipmap.icon_def_empty,radius)
    }
}
@BindingAdapter("setCircleUrl")
fun ImageView.setCircleUrl(url: String?){
    url?.let {
        GlideAppUtils.loadCircle(this, it, R.mipmap.icon_user_empty)
    }
}