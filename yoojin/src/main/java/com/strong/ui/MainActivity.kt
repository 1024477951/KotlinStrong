package com.strong.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.gyf.immersionbar.ktx.immersionBar
import com.strong.R
import com.strong.databinding.ActivityMainBinding
import com.strong.ui.base.BaseBindActivity
import com.strong.ui.home.HomeFragment
import com.strong.ui.me.MeFragment
import com.strong.ui.sort.SortFragment
import com.strong.ui.splash.SplashFragment
import com.strong.ui.view.menu.BottomMenuView

class MainActivity : BaseBindActivity<ActivityMainBinding, MainViewModel>() {

    //启动页
    private val splashFragment by lazy { SplashFragment() }

    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment by lazy { HomeFragment() }
    private val sortFragment by lazy { SortFragment() }
    private val meFragment by lazy { MeFragment() }

    private val isShowSplash: Boolean by lazy {
        intent.getBooleanExtra(EXTRA_IS_SHOW_SPLASH, false)
    }

    override fun layoutId() = R.layout.activity_main

    override fun providerVMClass() = MainViewModel::class.java

    override fun initData(bundle: Bundle?) {
        if (isShowSplash) {
            //添加启动页
            supportFragmentManager.beginTransaction().replace(R.id.fl_splash, splashFragment)
                .commitAllowingStateLoss()
        }
        initTabs()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initTabs() {
        val titles: MutableList<String> = ArrayList()
        titles.run {
            add("首页")
            add("Tabs")
            add("我的")
        }
        val drawables: MutableList<Drawable?> = ArrayList()
        drawables.run {
            add(getDrawable(R.drawable.selected_main_bottom_home))
            add(getDrawable(R.drawable.selected_main_bottom_sort))
            add(getDrawable(R.drawable.selected_main_bottom_me))
        }
        binding.linBottom.setTitles(titles, drawables)
        binding.linBottom.setCallBack(object : BottomMenuView.CallBack {
            override fun click(position: Int) {
                binding.viewPager.setCurrentItem(position, false)
            }
        })
        fragmentList.run {
            add(homeFragment)
            add(sortFragment)
            add(meFragment)
        }
        with(binding.viewPager) {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    binding.linBottom.select(position)
                }
            })
            isUserInputEnabled = false
            offscreenPageLimit = 1
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun createFragment(position: Int) = fragmentList[position]

                override fun getItemCount() = fragmentList.size
            }
        }
    }

    override fun initImmersionBar() {
        immersionBar {
            transparentBar()
        }
    }

    companion object {
        private const val EXTRA_IS_SHOW_SPLASH = "extra_is_show_splash"
        fun launch(context: Context, isShowSplash: Boolean = true) {
            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_IS_SHOW_SPLASH, isShowSplash)
            }
            context.startActivity(intent)
        }
    }

}