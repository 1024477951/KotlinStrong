package com.kotlinstrong.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

open class ScrollerViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    constructor(context:Context):this(context,null)

    private val isScroller = false

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        // 触摸事件不触发
        return if (this.isScroller) {
            super.onTouchEvent(ev)
        } else false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        // 不处理触摸拦截事件
        return if (this.isScroller) {
            super.onInterceptTouchEvent(ev)
        } else false
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if (this.isScroller) {
            super.dispatchTouchEvent(ev)
        } else super.dispatchTouchEvent(ev)
    }
}