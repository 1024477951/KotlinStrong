package com.strong.ui.splash

import android.content.Context
import android.content.Intent
import com.strong.R
import com.strong.baselib.setSingleClick
import com.strong.baselib.sp.SpConstant
import com.strong.baselib.sp.SpUtil
import com.strong.databinding.ActivityLoginGuideBinding
import com.strong.ui.MainActivity
import com.strong.baselib.base.BaseVMActivity
import com.strong.baselib.base.BaseViewModel

/** 登录欢迎引导页 */
class LoginGreetGuideActivity :
    BaseVMActivity<BaseViewModel, ActivityLoginGuideBinding>() {

    override fun providerVMClass() = BaseViewModel::class.java

    override fun initView() {
        mBinding.textTranslationGuide
            .addTextGuide(getString(R.string.login_guide_text_start))
            .addTextGuide(getString(R.string.login_guide_text_transition))
            .addTextGuide(
                getString(R.string.login_guide_text_end_content),
                getString(R.string.login_guide_text_end_bright),
                R.color.c_5121ff
            )
            .initGuide()

        mBinding.root.setSingleClick {
            goMainPage()
        }
    }

    override fun initData() {
        SpUtil.encode(SpConstant.isShowedFirstGuide, true)
    }

    override fun scribeObserve() {

    }

    private fun goMainPage() {
        if (mBinding.textTranslationGuide.isFinishAnimation) {
            MainActivity.launch(this, false)
            finish()
        }
    }

    override fun onBackPressed() {
        if (!moveTaskToBack(false)) {
            moveTaskToBack(true)
        }
    }

    override fun onDestroy() {
        mBinding.textTranslationGuide.clear()
        super.onDestroy()
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, LoginGreetGuideActivity::class.java)
            context.startActivity(intent)
        }
    }
}