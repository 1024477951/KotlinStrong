package com.kotlinstrong.main

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kotlinstrong.base.TabFragment

@Suppress("DEPRECATION")
class BounceAdapter(private val fm: FragmentManager) : FragmentPagerAdapter(fm) {

    init {
        TabsHelper.init(fm)
    }

    override fun getItem(position: Int): TabFragment<MainViewModel> {
        return TabsHelper.getFragment(position)!!
    }

    override fun getCount(): Int {
        return TabsHelper.getCount()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        fm.beginTransaction().show(fragment).commitAllowingStateLoss()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = TabsHelper.getFragment(position)!!
        fm.beginTransaction().hide(fragment).commitAllowingStateLoss()
    }
}