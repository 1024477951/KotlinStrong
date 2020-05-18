package com.kotlinstrong.option

import android.os.Bundle
import com.kotlinstrong.R
import com.kotlinstrong.base.BaseBindActivity

class OptionsActivity : BaseBindActivity<OptionViewModel>() {

    override fun providerVMClass(): Class<OptionViewModel> = OptionViewModel::class.java

    override fun layoutId() = R.layout.activity_options

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
    }

    override fun modelObserve() {
        super.modelObserve()
        mViewModel.apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
