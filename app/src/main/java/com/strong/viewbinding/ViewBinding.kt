package com.strong.viewbinding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter


/**  View 视图绑定 */
@BindingAdapter("bindViewClick")
fun View.bindViewClick(clickListener: View.OnClickListener){
    setOnClickListener(clickListener)
}

/**
 * 通用属性
 */
@BindingAdapter(
    value = ["isCheck", "checkResId", "unCheckResId", "checkColor", "unCheckColor"],
    requireAll = false
)
fun isCheck(
    view: View?,
    isCheck: Boolean,
    checkResId: Int,
    unCheckResId: Int,
    checkColor: Int,
    unCheckColor: Int
) {
    if (view is ImageView) {
        if (isCheck) {
            view.setImageResource(checkResId)
        } else {
            view.setImageResource(unCheckResId)
        }
    } else if (view is TextView) {
        if (isCheck) {
            if (checkColor > 0) {
                view.setTextColor(view.resources.getColor(checkColor))
            }
        } else {
            if (unCheckColor > 0) {
                view.setTextColor(view.resources.getColor(unCheckColor))
            }
        }
    }
}