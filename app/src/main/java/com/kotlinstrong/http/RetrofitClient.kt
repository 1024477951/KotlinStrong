package com.kotlinstrong.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.kotlinstrong.stronglib.base.BaseApp
import com.kotlinstrong.stronglib.base.BaseRetrofitClient
import okhttp3.OkHttpClient

/** 网络访问类 */
object RetrofitClient : BaseRetrofitClient() {

//    val service by lazy { getService(ArticleApi::class.java, UrlConfig.BASE_API) }

    fun<S> getService(clazz: Class<out S>): S{
        return getService(clazz, UrlConfig.BASE_API)
    }

    private val cookieJar by lazy { PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(BaseApp.getContext())) }
    /** 扩展配置信息 */
    override fun handleBuilder(builder: OkHttpClient.Builder) {
        builder.cookieJar(cookieJar)
    }
}