package com.kotlinstrong.utils.aspect

import com.blankj.utilcode.util.LogUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/** 切面点击工具类 */
@Aspect
class AspectClickUtils {
    /**
     * 定义切点，标记切点为所有被@AopOnclick注解的方法
     */
    @Pointcut("execution(@com.kotlinstrong.utils.aspect.AnnotationOnclick * *(..))")
    fun methodAnnotated(){}

//    @Pointcut("execution(@com.kotlinstrong.utils.aspect.AnnotationLogin * *(..))")
//    fun methodLogin(){}

    /**
     * 定义一个切面方法，包裹切点方法
     * ProceedingJoinPoint：继承自JoinPoint，为了支持Around注解，其他的几种切面只需要用到JoinPoint
     */
    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        // 取出方法的注解,返回连接点处的签名
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        if (!method.isAnnotationPresent(AnnotationOnclick::class.java)) {
            return
        }
        val aopOnclick = method.getAnnotation(AnnotationOnclick::class.java)
        // 判断是否快速点击
        if (!ClickUtils.isFastDoubleClick(aopOnclick.value)) {
            // 不是快速点击，执行原方法
            joinPoint.proceed()
        }
    }

//    @Around("methodLogin()")
//    @Throws(Throwable::class)
//    fun aroundLoginPoint(joinPoint: ProceedingJoinPoint) {
//
//    }
}