package com.kotlinstrong.main

import com.kotlinstrong.R
import com.kotlinstrong.base.TabFragment
import com.kotlinstrong.home.HomeFragment
import com.kotlinstrong.stronglib.base.BaseFragment
import com.kotlinstrong.stronglib.base.BaseViewModel
/** 配置首页菜单 tab */
enum class Tabs(val tabIndex: Int, val clazz: Class<out TabFragment<MainViewModel>>, val layoutId: Int) {

    Home(0, HomeFragment::class.java, R.layout.fragment_home),
    Contact(1, HomeFragment::class.java, R.layout.fragment_home),
    Dynamic(2, HomeFragment::class.java, R.layout.fragment_home),
    Me(3, HomeFragment::class.java, R.layout.fragment_home);

    fun fromTabIndex(tabIndex: Int): Tabs? {
        for (value in values()) {
            if (value.tabIndex == tabIndex) {
                return value
            }
        }
        return null
    }

}