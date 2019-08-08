package com.kotlinstrong

import com.kotlinstrong.api.ArticleApi
import com.kotlinstrong.http.UrlConfig
import com.kotlinstrong.stronglib.base.BaseApp
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit

val appModule = module {

//    viewModel { PaoViewModel(get<PaoRepo>()) }

    // 每次使用到的时候都会生成新的实例
    factory {  }

    val httpModule = module {

        val cacheFile = File(BaseApp.getContext().cacheDir, "app_caheData")
        //设置缓存大小
        val cache = Cache(cacheFile, (1024 * 1024 * 14).toLong())//google建议放到这里

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        var mClient = OkHttpClient.Builder()
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .cache(cache)//添加缓存
            //.addNetworkInterceptor(new CacheInterceptor())
            //应用拦截器.addInterceptor(mInterceptor)
            //网络拦截器.addNetworkInterceptor(mInterceptor)
            .addInterceptor(httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            })
            //.addInterceptor(AddHeaderInterceptor())
            //.addInterceptor(LogInterceptor())
            .proxy(Proxy.NO_PROXY)//避免被抓包
            .build()

        single<Retrofit> {
            Retrofit.Builder()
                .client(mClient)
                .baseUrl(UrlConfig.BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        }
        //单例 single<interface> { get<Retrofit>().create(object.class) }
        single<ArticleApi> { get<Retrofit>().create(ArticleApi::class.java) }
    }
}