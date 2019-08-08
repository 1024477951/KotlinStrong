package com.kotlinstrong.stronglib.listener;


import androidx.annotation.LayoutRes;

/**
 * item绑定.
 */
public interface ViewMap<T> {
    @LayoutRes
    int layoutId(T t);
}
