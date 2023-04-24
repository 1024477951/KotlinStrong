package com.strong.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.strong.R
import com.strong.databinding.FragmentSplashBinding
import com.strong.ui.base.BaseBindFragment


class SplashFragment : BaseBindFragment<FragmentSplashBinding, SplashViewModel>() {

    override fun layoutId() = R.layout.fragment_splash

    override fun providerVMClass() = SplashViewModel::class.java

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun initData(bundle: Bundle?) {
        binding.model = mViewModel
        mViewModel.getSplashList()

        var count = 0f
        mHandler = Handler(Looper.getMainLooper())
        mRunnable = Runnable {
            count += 1
            binding.pbTime.setProgress(count)
            if (count >= binding.pbTime.getMax()) {
                next()
            } else {
                mHandler.postDelayed(mRunnable, 2000)
            }
        }
        binding.btnNext.setOnClickListener { next() }
    }

    override fun modelObserve() {
        mViewModel.splashLiveData.observe(this) {
            binding.btnNext.visibility = View.VISIBLE
            binding.pbTime.setMax(it + 0f)
            mHandler.postDelayed(mRunnable, 2000)
        }
    }

    /** 关闭启动页 */
    private fun next() {
        mHandler.removeCallbacks(mRunnable)
        requireActivity().window.setBackgroundDrawableResource(R.color.white)
        //移除启动页
        requireActivity().supportFragmentManager.beginTransaction().remove(this@SplashFragment)
            .commitAllowingStateLoss()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
    }

}