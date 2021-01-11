package com.strong.utils.aspect

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature

/** 切面工具类 */
@Aspect
class AspectUtils {

    @Before("execution(* com.strong.ui.home.HomeFragment.test*(..))")
    fun testAspectBefore(point: JoinPoint) {
        LogUtils.d("AspectUtils ${point.signature.name} ---testAspectBefore")
    }

    @AfterReturning("execution(* com.strong.ui.home.HomeFragment.test*(..))", returning = "id")
    fun testAspectAfterReturning(point: JoinPoint, id: Int) {
        LogUtils.d("AspectUtils ${point.signature.name} ---testAspectAfterReturning $id")
    }

    /** 耗时统计 start ------------------------------------ **/
    @Pointcut("execution(@com.strong.utils.aspect.MyAnnotationTime * *(..))")
    fun methodTime(){}

    @Around("methodTime()")
    @Throws(Throwable::class)
    fun aroundTimePoint(joinPoint: ProceedingJoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        if (!method.isAnnotationPresent(MyAnnotationTime::class.java)) {
            return
        }
        val time = System.currentTimeMillis()
        joinPoint.proceed()
        val millis = System.currentTimeMillis() - time
        LogUtils.d("AspectUtils ${joinPoint.signature.name} 耗时 millis $millis")
    }
    /** 耗时统计 end ------------------------------------ **/

    /** 防抖 start ------------------------------------ **/
    /*
     * 定义切点，标记切点为所有被@AopOnclick注解的方法
     * @+注解全路径
     */
    @Pointcut("execution(@com.strong.utils.aspect.MyAnnotationOnclick * *(..))")
    fun methodAnnotated(){}
    /*
     * 定义一个切面方法，包裹切点方法
     * ProceedingJoinPoint：继承自JoinPoint，为了支持Around注解，其他的几种切面只需要用到JoinPoint
     */
    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        LogUtils.d("AspectUtils ${joinPoint.signature.name} put aroundJoinPoint")
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
    @Pointcut("execution(@com.strong.utils.aspect.MyAnnotationLogin * *(..))")
    fun methodLogin(){}

    @Around("methodLogin()")
    @Throws(Throwable::class)
    fun aroundLoginPoint(joinPoint: ProceedingJoinPoint) {
        ToastUtils.showShort("login")
        //此处判断是否登录，如果没有不执行方法，跳转到登录，如果已经登录只执行原方法
        joinPoint.proceed()

    }
    /** 登录 end ------------------------------------ **/

    /**
    @Pointcut("execution(" +//执行语句
    "@com.strong.utils.aspect.MyAnnotationOnclick" +//注解筛选
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