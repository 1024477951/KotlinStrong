package com.kotlinstrong.stronglib.listener

import android.view.View

interface LongFunction {
    fun longClick(view: View, position: Int): Boolean
}
