package com.kotlinstrong.base

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import com.kotlinstrong.R
import com.kotlinstrong.stronglib.base.BaseViewModel

abstract class LazyFragment<VM : BaseViewModel> : BaseBindFragment<VM>() , LifecycleObserver {

    private var is_visible: Boolean = false
    private var isLoad: Boolean = false

    override fun layoutId(): Int {
        return R.layout.layout_error
    }

    @Suppress("DEPRECATION")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            is_visible = true
            onVisible()
        }else{
            is_visible = false
        }
    }

    private fun onVisible() {
        if (!is_visible || !isLoad || !loadRealLayout()) {
            return
        }
        loadRealLayout()
        isLoad = true
    }

    private fun loadRealLayout(): Boolean {
        var root:ViewGroup? = null
        if (view != null) {
            root = view as ViewGroup
            if (root != null) {
                root.removeAllViewsInLayout()
                View.inflate(root.context, layoutId(), root)
            }
        }
        return root != null
    }
}