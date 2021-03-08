package com.strong.net.http

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.strong.net.factory.LiveDataCallAdapterFactory
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitClient {

    companion object {
        //20秒超时
        private const val TIME_OUT = 20
    }

    private val cookieJar by lazy { PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(Utils.getApp()))}

    private val client by lazy {
        val cacheFile = File(Utils.getApp().cacheDir, "app_cache")
        //设置缓存大小
        val cache = Cache(cacheFile, (1024 * 1024 * 14).toLong())//google建议放到这里 缓存14M

        val client = OkHttpClient.Builder()
        client.addInterceptor(addInterceptor())
        client
            .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .cache(cache)//添加缓存
            .cookieJar(cookieJar)
            .addInterceptor(InterceptorFactory.logInterceptor())
            .proxy(Proxy.NO_PROXY)//避免被抓包
            .build()
    }
    /** 添加自定义拦截器 */
    abstract fun addInterceptor(): Interceptor

    protected fun getRetrofit(baseUrl: String): Retrofit{
        LogUtils.d("==>","getRetrofit -- 只会获取一次")
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }
}
