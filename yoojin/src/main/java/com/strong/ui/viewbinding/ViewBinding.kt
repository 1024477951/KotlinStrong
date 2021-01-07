package com.strong.ui.viewbinding

import android.view.View
import androidx.databinding.BindingAdapter


/**  View 视图绑定 */
@BindingAdapter("bindViewClick")
fun bindViewClick(view: View,clickListener: View.OnClickListener){
    view.setOnClickListener(clickListener)
}