package com.kotlinstrong.utils.aspect

import com.blankj.utilcode.util.LogUtils
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Before


@Aspect
class AspectUtils {

    /**
        @Aspect：声明切面，标记类
        @Pointcut(切点表达式)：定义切点，标记方法
        @Before(切点表达式)：前置通知，切点之前执行
        @Around(切点表达式)：环绕通知，切点前后执行
        @After(切点表达式)：后置通知，切点之后执行
        @AfterReturning(切点表达式)：返回通知，切点方法返回结果之后执行
        @AfterThrowing(切点表达式)：异常通知，切点抛出异常时执行
     */

    /**
        execution(<@注解类型模式>? <修饰符模式>? <返回类型模式> <方法名模式>(<参数模式>) <异常模式>?)
        修饰符模式:public等
     */
    @Before("execution(* com.kotlinstrong.main.MainActivity.test*(..))")
    fun testAspectBefore(point: JoinPoint) {
        LogUtils.e("AspectUtils ${point.signature.name} ---testAspectBefore")
    }

    @AfterReturning("execution(* com.kotlinstrong.main.MainActivity.test*(..))", returning = "id")
    fun testAspectAfterReturning(point: JoinPoint, id: Int) {
        LogUtils.e("AspectUtils ${point.signature.name} ---testAspectAfterReturning $id")
    }
}