package com.kotlinstrong.stronglib.listener

import android.view.View

interface Function<T> {
    fun call(view: View, t: T)
}
