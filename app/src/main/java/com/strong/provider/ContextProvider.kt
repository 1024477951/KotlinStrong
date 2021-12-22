package com.strong.provider

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


class ContextProvider(val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: ContextProvider? = null
        /**
         * 获取实例
         */
        fun get(): ContextProvider? {
            if (instance == null) {
                synchronized(ContextProvider::class.java) {
                    if (instance == null) {
                        val context: Context = KtxProvider.mContext
                        instance = ContextProvider(context)
                    }
                }
            }
            return instance
        }
    }

    fun getApplication(): Application {
        return context.applicationContext as Application
    }
}