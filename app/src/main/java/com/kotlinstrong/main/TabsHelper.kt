package com.kotlinstrong.main

import android.util.SparseArray
import androidx.fragment.app.FragmentManager
import com.kotlinstrong.base.TabFragment
import com.kotlinstrong.stronglib.base.BaseFragment
import com.kotlinstrong.stronglib.base.BaseViewModel
import java.lang.ref.SoftReference
/** 初始化，并且缓存tab */
object TabsHelper {
    /**
     * 稀疏数组,是Android内部特有的api,标准的jdk是没有这个类的.在Android内部用来替代HashMap<Integer></Integer>,E>这种形式,使用SparseArray更加节省内存空间的使用,
     * SparseArray也是以key和value对数据进行保存,并且key不需要封装成对象类型,最大的优势就是内存消耗小
     */
    private val arr = SparseArray<SoftReference<TabFragment<MainViewModel>>>()

    fun getFragment(position: Int): TabFragment<MainViewModel>? {
        return arr.get(position).get()
    }

    fun init(fm: FragmentManager) {
        for (tab in Tabs.values()) {
            try {
                var fragment: TabFragment<MainViewModel>? = null

                val fs = fm.fragments
                if (fs != null) {
                    for (f in fs) {
                        if (f.javaClass == tab.clazz) {
                            fragment = f as TabFragment<MainViewModel>
                            break
                        }
                    }
                }

                if (fragment == null) {
                    fragment = tab.clazz.newInstance()
                }
                fragment!!.attachTabData(tab)

                arr.put(tab.tabIndex, SoftReference(fragment))
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
    }

    fun getCount(): Int {
        return Tabs.values().size
    }
}
