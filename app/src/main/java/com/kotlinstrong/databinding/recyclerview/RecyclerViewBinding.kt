package com.kotlinstrong.databinding.recyclerview

import androidx.databinding.BindingAdapter
import com.kotlinstrong.view.banner.AdsPager

/**  RecyclerView 视图绑定 */


@BindingAdapter("ads_list")
fun setAdsList(recyclerView: AdsPager, list: MutableList<String>?){
    list?.let {
        recyclerView.setList(list)
    }
}