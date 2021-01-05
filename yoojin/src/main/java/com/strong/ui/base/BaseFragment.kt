package com.strong.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected var contentView: View? = null

    /**
     *  加载布局
     */
    abstract fun layoutId(): Int
    /**
     * 初始化数据
     */
    abstract fun initData(bundle: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (contentView == null) {
            contentView = inflater.inflate(layoutId(), container, false)
        } else {
            if (contentView!!.parent is ViewGroup) {
                val parent = contentView!!.parent as ViewGroup
                parent.removeView(contentView)
            }
        }
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(arguments)
    }

    override fun onDestroyView() {
        if (contentView!!.parent is ViewGroup) {
            val parent = contentView!!.parent as ViewGroup
            parent.removeView(contentView)
        }
        super.onDestroyView()
    }
}