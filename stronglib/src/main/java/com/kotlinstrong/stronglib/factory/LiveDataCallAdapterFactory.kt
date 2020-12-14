package com.kotlinstrong.stronglib.factory

import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/** 参考：https://github.com/android/architecture-components-samples */
class LiveDataCallAdapterFactory : Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        /** ParameterizedType（参数化类型）即泛型；例如：List< T>、Map< K,V>等带有参数化的对象 */
        val selfType = getRawType(returnType)
        if (selfType != LiveData::class.java) {
            LogUtils.e("Response must be LiveData.class. error: type is $selfType")
            throw IllegalStateException("return type must be LiveData.class. error: type is $selfType")
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(
            bodyType
        )
    }

}