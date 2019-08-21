package com.kotlinstrong.stronglib.listener

import android.view.View

interface LongFunction<T> {
    fun call(view: View, t: T)
}
