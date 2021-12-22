package com.strong.viewbinding

import androidx.databinding.BindingAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener


@BindingAdapter("onRefreshLoadMoreListener")
fun SmartRefreshLayout.onRefreshLoadMoreListener(action: () -> Unit) {
    setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
        override fun onRefresh(refreshLayout: RefreshLayout) {
            refreshLayout.finishRefresh()
            action()
        }

        override fun onLoadMore(refreshLayout: RefreshLayout) {
            refreshLayout.finishLoadMore()
            action()
        }
    })
}