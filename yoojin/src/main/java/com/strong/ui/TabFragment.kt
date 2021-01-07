package com.strong.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.strong.R
import com.strong.ui.base.BaseFragment
import com.strong.ui.home.HomeFragment
import com.strong.ui.view.menu.BottomMenuView

class TabFragment : BaseFragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var menus: BottomMenuView
    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment by lazy { HomeFragment() }
    private val menuFragment by lazy { SecondFragment() }

    override fun layoutId() = R.layout.fragment_tab

    override fun initData(bundle: Bundle?) {
        viewPager = view?.findViewById(R.id.viewPager)!!
        menus = view?.findViewById(R.id.lin_bottom)!!
        val list: MutableList<String> = ArrayList()
        list.add("首页")
        list.add("菜单")
        menus.setTitles(list)
        menus.setCallBack(object : BottomMenuView.CallBack{
            override fun click(position: Int) {
                viewPager.setCurrentItem(position, false)
            }
        })
        initViewPager()
    }

    private fun initViewPager() {
        fragmentList.run {
            add(homeFragment)
            add(menuFragment)
        }
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                menus.select(position)
            }
        })
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = fragmentList[position]

            override fun getItemCount() = fragmentList.size
        }
    }

}