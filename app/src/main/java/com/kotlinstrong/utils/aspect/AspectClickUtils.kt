package com.kotlinstrong.utils.aspect

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.login.LoginActivity
import com.kotlinstrong.option.OptionsActivity
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/** 切面点击工具类 */
@Aspect
class AspectClickUtils {
    /**
     * 定义切点，标记切点为所有被@AopOnclick注解的方法
     * @+注解全路径
     */
    @Pointcut("execution(@com.kotlinstrong.utils.aspect.MyAnnotationOnclick * *(..))")
    fun methodAnnotated(){}

    @Pointcut("execution(@com.kotlinstrong.utils.aspect.MyAnnotationLogin * *(..))")
    fun methodLogin(){}

    /**
     * 定义一个切面方法，包裹切点方法
     * ProceedingJoinPoint：继承自JoinPoint，为了支持Around注解，其他的几种切面只需要用到JoinPoint
     */
    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
//        LogUtils.e("====aroundJoinPoint")
        // 取出方法的注解,返回连接点处的签名
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        if (!method.isAnnotationPresent(MyAnnotationOnclick::class.java)) {
            return
        }
        val aopOnclick = method.getAnnotation(MyAnnotationOnclick::class.java)
        // 判断是否快速点击
        if (!ClickUtils.isFastDoubleClick(aopOnclick.value)) {
            // 不是快速点击，执行原方法
            joinPoint.proceed()
        }
    }

    @Around("methodLogin()")
    @Throws(Throwable::class)
    fun aroundLoginPoint(joinPoint: ProceedingJoinPoint) {
        LogUtils.e("====aroundLoginPoint")
        ToastUtils.showShort("login")
        joinPoint.proceed()
        ActivityUtils.startActivity(LoginActivity::class.java)
    }
}