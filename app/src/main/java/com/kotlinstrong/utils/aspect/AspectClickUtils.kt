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

/** 切面工具类 */
@Aspect
class AspectClickUtils {

    /** 防抖 start ------------------------------------ **/
    /*
     * 定义切点，标记切点为所有被@AopOnclick注解的方法
     * @+注解全路径
     */
    @Pointcut("execution(@com.kotlinstrong.utils.aspect.MyAnnotationOnclick * *(..))")
    fun methodAnnotated(){}
    /*
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
        //判断注释是否在method上
        if (!method.isAnnotationPresent(MyAnnotationOnclick::class.java)) {
            return
        }
        val aopOnclick = method.getAnnotation(MyAnnotationOnclick::class.java)
        // 判断是否快速点击
        if (!ClickUtils.isFastDoubleClick(aopOnclick.value)) {
            // 执行原方法
            joinPoint.proceed()
        }
    }
    /** 防抖 end ------------------------------------ **/


    /** 登录 start ------------------------------------ **/
    @Pointcut("execution(@com.kotlinstrong.utils.aspect.MyAnnotationLogin * *(..))")
    fun methodLogin(){}

    @Around("methodLogin()")
    @Throws(Throwable::class)
    fun aroundLoginPoint(joinPoint: ProceedingJoinPoint) {
//        LogUtils.e("====aroundLoginPoint")
        ToastUtils.showShort("login")
        joinPoint.proceed()
        ActivityUtils.startActivity(LoginActivity::class.java)
    }
    /** 登录 end ------------------------------------ **/

    /**
       @Pointcut("execution(" +//执行语句
        "@com.kotlinstrong.utils.aspect.MyAnnotationOnclick" +//注解筛选
        "*" + //类路径,*为任意路径
        "*" + //方法名,*为任意方法名
        "(..)" +//方法参数,'..'为任意个任意类型参数
        ")" +
        " && " +//并集
        )

        @Aspect：声明切面，标记类
        @Pointcut(切点表达式)：定义切点，标记方法，告诉代码注入工具，在何处注入一段特定代码的表达式
        @Before(切点表达式)：前置通知，切点之前执行
        @Around(切点表达式)：环绕通知，切点前后执行
        @After(切点表达式)：后置通知，切点之后执行
        @AfterReturning(切点表达式)：返回通知，切点方法返回结果之后执行
        @AfterThrowing(切点表达式)：异常通知，切点抛出异常时执行
    * */
}