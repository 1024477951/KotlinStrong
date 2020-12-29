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

    fun attachTabData(factory: Tabs) {
        this.factory = factory
    }

//    @Suppress("DEPRECATION")
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (userVisibleHint) {
//            isVisibleUI = true
//            onVisible()
//        }else{
//            isVisibleUI = false
//        }
//    }

//    private fun onVisible() {
//        if (!isVisibleUI || isEnd) {
//            return
//        }
//        loadRealLayout()
//    }

//    private fun loadRealLayout(): Boolean {
//        var root:ViewGroup? = null
//        if (view != null) {
//            root = view as ViewGroup
//            root.removeAllViewsInLayout()
//            View.inflate(root.context, this.factory!!.layoutId, root)
//            isEnd = true//只加载一次
//        }
//        return root != null
//    }

}