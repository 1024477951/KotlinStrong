package com.kotlinstrong.utils.aspect

import com.blankj.utilcode.util.LogUtils
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Before

/** 切面测试类 */
@Aspect
class AspectUtils {

    @Before("execution(* com.kotlinstrong.main.MainActivity.test*(..))")
    fun testAspectBefore(point: JoinPoint) {
        LogUtils.e("AspectUtils ${point.signature.name} ---testAspectBefore")
    }

    @AfterReturning("execution(* com.kotlinstrong.main.MainActivity.test*(..))", returning = "id")
    fun testAspectAfterReturning(point: JoinPoint, id: Int) {
        LogUtils.e("AspectUtils ${point.signature.name} ---testAspectAfterReturning $id")
    }
}