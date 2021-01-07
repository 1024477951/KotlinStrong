package com.strong.net
//out相当于Java中? extends T
data class BaseResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)