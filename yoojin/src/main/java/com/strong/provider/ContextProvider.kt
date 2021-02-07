package com.strong.provider

import android.app.Application
import android.content.Context


class ContextProvider {

    private var mContext: Context

    constructor(context: Context){
        mContext = context
    }

    companion object {
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

    /**
     * 获取上下文
     */
    fun getContext(): Context? {
        return mContext
    }

    fun getApplication(): Application? {
        return mContext.applicationContext as Application
    }
}