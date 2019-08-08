package com.kotlinstrong.stronglib.base

import com.kotlinstrong.stronglib.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitClient {

    companion object {
        private const val TIME_OUT = 5
    }

    private val client: OkHttpClient
        get() {
            val cacheFile = File(BaseApp.getContext().cacheDir, "app_caheData")
            //设置缓存大小
            val cache = Cache(cacheFile, (1024 * 1024 * 14).toLong())//google建议放到这里

            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }
            builder
                .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .cache(cache)//添加缓存
                //.addNetworkInterceptor(new CacheInterceptor())
                //应用拦截器.addInterceptor(mInterceptor)
                //网络拦截器.addNetworkInterceptor(mInterceptor)
                .addInterceptor(logging)
                //.addInterceptor(AddHeaderInterceptor())
                //.addInterceptor(LogInterceptor())
                .proxy(Proxy.NO_PROXY)//避免被抓包
                .build()
            handleBuilder(builder)

            return builder.build()
        }

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build().create(serviceClass)
    }
}
