package com.strong.net
//out相当于Java中? extends T
data class BaseResponse<out T>(val code: Int, val msg: String?, val data: T?)