package com.kotlinstrong.main

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kotlinstrong.base.TabFragment

@Suppress("DEPRECATION")
class BounceAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    init {
        TabsHelper.init(fm)
    }

    override fun getItem(position: Int): TabFragment<MainViewModel> {
        return TabsHelper.getFragment(position)!!
    }

    override fun getCount(): Int {
        return TabsHelper.getCount()
    }
}