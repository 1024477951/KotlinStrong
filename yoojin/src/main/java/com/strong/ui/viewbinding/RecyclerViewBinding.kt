package com.strong.ui.viewbinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.strong.ui.adapter.BaseAdapter

/**  RecyclerView 视图绑定 */
@BindingAdapter("adapter")
fun RecyclerView.adapter(adapter: BaseAdapter) {
    setAdapter(adapter)
}