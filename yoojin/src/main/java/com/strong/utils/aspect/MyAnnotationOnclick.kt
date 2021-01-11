package com.strong.utils.aspect

import kotlin.annotation.Target
import kotlin.annotation.Retention

/* 防抖点击 */
/**
 * SOURCE：运行时 不存储在编译后的 Class 文件。
 * BINARY：加载时 存储在编译后的 Class 文件，但是反射不可见。
 * RUNTIME：编译时 存储在编译后的 Class 文件，反射可见。
 */
@Retention(AnnotationRetention.RUNTIME)
/**
 * CLASS：类，接口或对象，注解类也包括在内。
 * ANNOTATION_CLASS：只有注解类。
 * TYPE_PARAMETER：Generic type parameter (unsupported yet)通用类型参数（还不支持）。
 * PROPERTY：属性。
 * FIELD：字段，包括属性的支持字段。
 * LOCAL_VARIABLE：局部变量。
 * VALUE_PARAMETER：函数或构造函数的值参数。
 * CONSTRUCTOR：仅构造函数（主函数或者第二函数）。
 * FUNCTION：方法（不包括构造函数）。
 * PROPERTY_GETTER：只有属性的 getter。
 * PROPERTY_SETTER：只有属性的 setter。
 * TYPE：类型使用。
 * EXPRESSION：任何表达式。
 * FILE：文件。
 * TYPEALIAS：@SinceKotlin("1.1") 类型别名，Kotlin1.1已可用。
 */
@Target(AnnotationTarget.FUNCTION)
annotation class MyAnnotationOnclick(
    /** 点击间隔时间 */
    val value: Long = 1000
)