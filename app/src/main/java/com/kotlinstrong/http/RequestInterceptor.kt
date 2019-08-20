package com.kotlinstrong.http

import android.util.Log
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.SPUtils
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder()
        val url = request.url.toString()
        requestBuilder
            .addHeader("Device", "Android:"+DeviceUtils.getAndroidID())
        request = requestBuilder.url(url).build()
        Log.d("RequestInterceptor","Device "+"Android:"+DeviceUtils.getAndroidID())
        //从request中获取headers，通过给定的键url_name
        val headerValues = request.headers("url_head")
        if(headerValues != null && headerValues.isNotEmpty()) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            requestBuilder.removeHeader("url_head")
            //匹配获得新的BaseUrl
            val headerValue = headerValues[0]
            val newBaseUrl: HttpUrl?= if ("article" == headerValue) {
                UrlConfig.ARTICLE_API.toHttpUrlOrNull()
            } else {
                UrlConfig.BASE_API.toHttpUrlOrNull()
            }

            //从request中获取原有的HttpUrl实例oldHttpUrl
            val oldHttpUrl = request.url
            //重建新的HttpUrl，修改需要修改的url部分
            val newFullUrl = oldHttpUrl
                .newBuilder()
                .scheme(newBaseUrl!!.scheme)//设置网络协议
                .host(newBaseUrl.host)//更换主机名
                .port(newBaseUrl.port)//更换端口
                .build()
            Log.d("RequestInterceptor","url "+ newFullUrl.toUri())
            //重建这个request，通过builder.url(newFullUrl).build()；
            //然后返回一个response至此结束修改
            return chain.proceed(requestBuilder.url(newFullUrl).build())
        }else{
            Log.d("RequestInterceptor","url "+ request.url.toString())
            return chain.proceed(request)
        }
    }

}