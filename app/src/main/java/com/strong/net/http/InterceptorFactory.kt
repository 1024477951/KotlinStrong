package com.strong.net.http

import com.strong.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object InterceptorFactory {
    //日志拦截器
    fun logInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        return logging
    }
}