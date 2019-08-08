package com.kotlinstrong.stronglib.base
//out相当于Java中? extends T
data class BaseResponse<out T>(val code: Int, val message: String, val data: T)