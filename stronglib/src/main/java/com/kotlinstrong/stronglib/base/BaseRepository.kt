package com.kotlinstrong.stronglib.base

import androidx.lifecycle.LiveData
import com.kotlinstrong.stronglib.factory.ApiResponse

open class BaseRepository {
    suspend fun <T : Any> apiCall(call: suspend () -> LiveData<ApiResponse<BaseResponse<T>>>): LiveData<ApiResponse<BaseResponse<T>>> {
        return call.invoke()
    }
}