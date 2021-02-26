package com.strong.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ActivityUtils
import com.strong.R
import com.strong.databinding.ActivityMainBinding
import com.strong.ui.base.BaseBindActivity
import com.strong.ui.home.HomeFragment
import com.strong.ui.sort.SortFragment
import com.strong.ui.splash.SplashFragment
import com.strong.ui.view.menu.BottomMenuView

class MainActivity : BaseBindActivity<ActivityMainBinding, MainViewModel>() {

    companion object{
        fun startMainActivity(){
            ActivityUtils.startActivity(MainActivity::class.java)
        }
    }
    //启动页
    private val splashFragment by lazy { SplashFragment() }

    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment by lazy { HomeFragment() }
    private val sortFragment by lazy { SortFragment() }

    override fun layoutId() = R.layout.activity_main

    override fun providerVMClass() = MainViewModel::class.java

    override fun initData(bundle: Bundle?) {
        //添加启动页
        supportFragmentManager.beginTransaction().replace(R.id.fl_splash,splashFragment).commitAllowingStateLoss()

        initViewPager()
    }

    private fun initViewPager() {
        val list: MutableList<String> = ArrayList()
        list.add("首页")
        list.add("Tabs")
        binding.linBottom.setTitles(list)
        binding.linBottom.setCallBack(object : BottomMenuView.CallBack{
            override fun click(position: Int) {
                binding.viewPager.setCurrentItem(position, false)
            }
        })
        fragmentList.run {
            add(homeFragment)
            add(sortFragment)
        }
        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                binding.linBottom.select(position)
            }
        })
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = fragmentList[position]

            override fun getItemCount() = fragmentList.size
        }
    }

}