package com.kotlinstrong.http

import com.kotlinstrong.stronglib.base.BaseRetrofitClient
import okhttp3.Interceptor

/** 网络访问类 */
object RetrofitClient : BaseRetrofitClient() {
    /**  网络访问拦截器 */
    private val interceptor by lazy {
        RequestInterceptor()
    }
    /**  添加扩展 */
    override fun addInterceptor(): Interceptor {
        return interceptor
    }
    /** 只获取一次实例，复用共享 */
    private val retrofit by lazy { getRetrofit(UrlConfig.BASE_API) }

    fun<T> getService(clazz: Class<T>): T{
        return retrofit.create(clazz)
    }
}