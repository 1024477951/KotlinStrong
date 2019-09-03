package com.kotlinstrong.databinding.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlinstrong.BR
import com.kotlinstrong.R
import com.kotlinstrong.view.banner.AdsPager
import com.kotlinstrong.view.banner.AdsPagerAdapter

/**  RecyclerView 视图绑定 */


@BindingAdapter("ads_list")
fun setAdsList(recyclerView: AdsPager, list: MutableList<String>?){
    list?.let {
        recyclerView.setList(list)
    }
}