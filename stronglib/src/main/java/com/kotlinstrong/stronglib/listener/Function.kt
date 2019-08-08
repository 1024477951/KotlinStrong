package com.kotlinstrong.stronglib.listener

import android.view.View

interface Function<T> {
    abstract fun call(view: View, t: T)
}
