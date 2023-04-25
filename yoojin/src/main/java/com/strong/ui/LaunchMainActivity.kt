package com.strong.ui

import com.gyf.immersionbar.ktx.immersionBar
import com.strong.baselib.sp.SpConstant
import com.strong.baselib.sp.SpUtil
import com.strong.databinding.ActivityLaunchBinding
import com.strong.baselib.base.BaseVMActivity
import com.strong.baselib.base.BaseViewModel
import com.strong.ui.splash.LoginGreetGuideActivity

/**
 * @author liuzhen
 * @date 2023/4/25
 */
class LaunchMainActivity : BaseVMActivity<BaseViewModel, ActivityLaunchBinding>() {

    override fun initImmersionBar() {
        immersionBar{
            transparentBar()
        }
    }

    override fun initView() {

    }

    override fun initData() {
        val isShowedFirstGuide = SpUtil.decodeBoolean(SpConstant.isShowedFirstGuide, false) ?: false
        if (isShowedFirstGuide) {
            //添加启动页
            MainActivity.launch(this)
        } else {
            //首次欢迎引导
            LoginGreetGuideActivity.launch(this)
        }
        finish()
    }

    override fun providerVMClass() = BaseViewModel::class.java
}