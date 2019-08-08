package com.kotlinstrong.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.kotlinstrong.api.ArticleApi
import com.kotlinstrong.stronglib.base.BaseApp
import com.kotlinstrong.stronglib.base.BaseRetrofitClient
import okhttp3.OkHttpClient


object RetrofitClient : BaseRetrofitClient() {

    val service by lazy { getService(ArticleApi::class.java, UrlConfig.BASE_API) }

    private val cookieJar by lazy { PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(BaseApp.getContext())) }

    override fun handleBuilder(builder: OkHttpClient.Builder) {
        builder.cookieJar(cookieJar)

    }
}