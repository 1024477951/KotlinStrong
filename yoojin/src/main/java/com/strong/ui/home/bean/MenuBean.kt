package com.strong.ui.home.bean

data class MenuBean(val title: String?,
                       val type: Int,
                       val resId: Int,
                       val adList:MutableList<String>?){

    companion object {
        const val TYPE_TITLE = 0
        const val TYPE_AD = 1
        const val TYPE_MENU = 2
    }
}

/**
 * 内部类 & 嵌套类
 * 内部类用关键字inner修饰
 * 嵌套类可以直接创建实例，内部类不能直接创建实例，需要通过外部类调用
 * 嵌套类不能引用包装类的成员；内部类会带有一个对外部包装类的对象的引用，可以访问外部类中的成员属性和成员函数
 * */