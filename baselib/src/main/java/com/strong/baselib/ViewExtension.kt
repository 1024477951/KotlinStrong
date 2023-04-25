package com.strong.baselib

import android.view.View
import com.blankj.utilcode.util.ClickUtils

/** 对所有设置 setDebouncingClick 的视图应用防抖点击 */
fun View.setDebouncingClick(action: (View) -> Unit) {
    ClickUtils.applyGlobalDebouncing(this, action)
}

/** 对单视图应用防抖点击 */
fun View.setSingleClick(action: (View) -> Unit) {
    ClickUtils.applySingleDebouncing(this, action)
}