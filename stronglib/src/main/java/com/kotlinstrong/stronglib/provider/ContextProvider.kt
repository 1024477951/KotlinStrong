package com.kotlinstrong.stronglib.provider

import android.app.Application
import android.content.Context


class ContextProvider {

    private var mContext: Context? = null

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
                        val context: Context = ApplicationContextProvider.mContext ?: throw IllegalStateException("context == null")
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
        return mContext!!.applicationContext as Application
    }
}