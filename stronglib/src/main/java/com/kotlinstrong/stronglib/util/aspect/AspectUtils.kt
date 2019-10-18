package com.kotlinstrong.stronglib.util.aspect

import com.blankj.utilcode.util.LogUtils
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Before


@Aspect
open class AspectUtils {

    /** AspectJ
     *  Before:在方法执行之前插入代码
     *  After:在方法执行之后插入代码
     *  AfterReturning:在方法执行后返回一个结果在执行，如果没有结果则不执行
     *  AfterThrowing:在方法执行中异常后执行
     *  Around:在方法执行前后和抛出异常时执行，前面的综合
     * */

    @Before("execution(* com.kotlinstrong.main.MainActivity.test*(..))")
    fun testAspectBefore(point: JoinPoint) {
        LogUtils.e("AspectUtils $point.signature.name -Before")
    }

    @AfterReturning("execution(* com.kotlinstrong.main.MainActivity.test*(..))",returning = "id")
    fun testAspectAfterReturning(point: JoinPoint,id:Int) {
        LogUtils.e("AspectUtils $point.signature.name -AfterReturning $id")
    }
}