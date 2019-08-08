package com.kotlinstrong.base

import android.view.View
import android.view.ViewGroup
import com.kotlinstrong.R
import com.kotlinstrong.main.Tabs
import com.kotlinstrong.stronglib.base.BaseViewModel
/** 首页菜单 baseTab */
abstract class TabFragment<VM : BaseViewModel> : BaseBindFragment<VM>() {

    private var factory: Tabs? = null
    private var isVisibleUI: Boolean = false
    private var isEnd: Boolean = false

    override fun layoutId(): Int {
        return R.layout.layout_error
    }

    fun attachTabData(factory: Tabs) {
        this.factory = factory
    }

    @Suppress("DEPRECATION")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisibleUI = true
            onVisible()
        }else{
            isVisibleUI = false
        }
    }

    private fun onVisible() {
        if (!isVisibleUI || !isEnd || !loadRealLayout()) {
            return
        }
        loadRealLayout()
        isEnd = true
    }

    private fun loadRealLayout(): Boolean {
        var root:ViewGroup? = null
        if (view != null) {
            root = view as ViewGroup
            if (root != null) {
                root.removeAllViewsInLayout()
                View.inflate(root.context, this.factory!!.layoutId, root)
            }
        }
        return root != null
    }

}