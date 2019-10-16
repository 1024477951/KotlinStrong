package com.kotlinstrong.stronglib.util.http

import com.kotlinstrong.stronglib.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object InterceptorFactory {
    //日志拦截器
    fun LogInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        return logging
    }
}