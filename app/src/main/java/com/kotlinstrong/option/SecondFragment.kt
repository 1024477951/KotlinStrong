package com.kotlinstrong.option

import android.os.Bundle
import com.kotlinstrong.R
import com.kotlinstrong.base.BaseBindFragment

class SecondFragment : BaseBindFragment<OptionViewModel>() {

    override fun providerVMClass(): Class<OptionViewModel> = OptionViewModel::class.java

    override fun layoutId() = R.layout.fragment_first

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
    }

    override fun modelObserve() {
        mViewModel.apply {

        }
    }

}