package com.strong.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.strong.R
import com.strong.databinding.FragmentSplashBinding
import com.strong.ui.base.BaseBindFragment


class SplashFragment : BaseBindFragment<FragmentSplashBinding, SplashViewModel>() {

    override fun layoutId() = R.layout.fragment_splash

    override fun providerVMClass() = SplashViewModel::class.java

    override fun initData(bundle: Bundle?) {
        binding.model = mViewModel
        //模拟加载图片
        var count = 0f
        val mHandler = Handler(Looper.getMainLooper())
        val mRunnable = object :Runnable {
            override fun run() {
                count+=20
                binding.pbTime.setProgress(count)
                if (count >= binding.pbTime.getMax()) {
                    mHandler.removeCallbacks(this)
                    activity!!.window.setBackgroundDrawableResource(R.color.white)
                    //移除启动页
                    activity!!.supportFragmentManager.beginTransaction().remove(this@SplashFragment).commitAllowingStateLoss()
                }else{
                    mHandler.postDelayed(this,500)
                }
            }
        }
        mHandler.postDelayed(mRunnable,500)
    }

    override fun modelObserve() {

    }

}