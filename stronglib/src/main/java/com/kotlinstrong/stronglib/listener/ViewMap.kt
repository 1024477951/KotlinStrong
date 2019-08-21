package com.kotlinstrong.stronglib.listener

import androidx.annotation.LayoutRes

interface ViewMap<T> {
    @LayoutRes
    fun layoutId(t: T): Int
}