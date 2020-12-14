package com.kotlinstrong.main

import com.kotlinstrong.base.TabFragment
import com.kotlinstrong.home.AppMenuFragment

/** 配置首页菜单 tab */
enum class Tabs(val tabIndex: Int, val clazz: Class<out TabFragment<MainViewModel>>) {

    Home(0, AppMenuFragment::class.java),
    Contact(1, AppMenuFragment::class.java),
    AppMenu(2, AppMenuFragment::class.java),
    Me(3, AppMenuFragment::class.java);

    fun fromTabIndex(tabIndex: Int): Tabs? {
        for (value in values()) {
            if (value.tabIndex == tabIndex) {
                return value
            }
        }
        return null
    }

}