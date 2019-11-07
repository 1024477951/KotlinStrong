package com.kotlinstrong.utils.aspect

import com.blankj.utilcode.util.LogUtils
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Before


@Aspect
open class AspectUtils {

    /**
     *  Before:在方法执行之前插入代码
     *  After:在方法执行之后插入代码
     *  AfterReturning:在方法执行后返回一个结果在执行，如果没有结果则不执行
     *  AfterThrowing:在方法执行中异常后执行
     *  Around:在方法执行前后和抛出异常时执行，前面的综合
     * */

    /** execution test方法里切入前后，call test方法外切入前后。
     * 匹配所有public的方法：execution(public * *(..))
     * 匹配包下所有类的方法：execution(* com.kotlinstrong.*(..)) （一个点表示不包含子包）
     * execution(* com.kotlin.. *(..)) （两个点表示包含子包）
     * 匹配实现特定接口的所有类的方法：execution(* com.kotlin.xxinterface+.*(..))
     * 匹配所有test开头的方法：execution(* test*(..))
     * 匹配所有方法：execution(* *.*(..))
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