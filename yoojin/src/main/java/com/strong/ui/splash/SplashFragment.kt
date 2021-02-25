package com.strong.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.navigation.Navigation
import com.strong.R
import com.strong.databinding.FragmentSplashBinding
import com.strong.ui.base.BaseBindFragment


class SplashFragment : BaseBindFragment<FragmentSplashBinding, SplashViewModel>() {

    override fun layoutId() = R.layout.fragment_splash

    override fun providerVMClass() = SplashViewModel::class.java

    override fun initData(bundle: Bundle?) {
        binding.model = mViewModel
        var count = 0f
        val mHandler = Handler()
        val mRunnable = object :Runnable {
            override fun run() {
                count+=10
                binding.pbTime.setProgress(count)
                if (count >= binding.pbTime.getMax()) {
                    mHandler.removeCallbacks(this)
                    activity!!.window.setBackgroundDrawableResource(R.color.white)
                    activity!!.let { Navigation.findNavController(it, R.id.nav_main) }
                        .navigate(R.id.fm_tab)
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