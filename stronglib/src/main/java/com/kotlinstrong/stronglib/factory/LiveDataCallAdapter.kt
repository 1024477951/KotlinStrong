package com.kotlinstrong.stronglib.factory

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<T>(private val responseType: Type) :
    CallAdapter<T, LiveData<ApiResponse<T>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<T>): LiveData<ApiResponse<T>> {
        return object : LiveData<ApiResponse<T>>() {
            /** 确保多线程的情况下安全的运行,不会被其它线程打断，一直等到该方法执行完成，才由JVM从等待队列中选择其它线程进入 */
            private var started = AtomicBoolean(false)
            //处于active状态的观察者(observe)个数从0变为1，回调LiveData的onActive()方法
            override fun onActive() {
                super.onActive()
                //把当前对象值与expect相比较,如果相等，把对象值设置为update，并返回为true
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<T> {
                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            postValue(
                                ApiResponse.create<T>(
                                    response
                                )
                            )
                        }

                        override fun onFailure(call: Call<T>, throwable: Throwable) {
                            postValue(
                                ApiResponse.create<T>(
                                    throwable
                                )
                            )
                        }
                    })
                }
            }
        }
    }
}