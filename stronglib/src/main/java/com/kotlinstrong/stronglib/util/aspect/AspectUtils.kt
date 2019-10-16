package com.kotlinstrong.stronglib.util.aspect

import com.blankj.utilcode.util.LogUtils
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Before


@Aspect
open class AspectUtils {

    @Before("execution(* com.kotlinstrong.main.MainActivity.init*(..))")
    fun testAspectBefore(point: JoinPoint) {
        LogUtils.e("AspectUtils", point.signature.name + "-before ")
    }
}