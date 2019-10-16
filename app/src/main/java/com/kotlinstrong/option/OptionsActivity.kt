package com.kotlinstrong.option

import android.os.Bundle
import android.util.SparseArray
import android.view.KeyEvent
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.SPUtils
import com.kotlinstrong.R
import com.kotlinstrong.base.BaseBindAvtivity
import kotlinx.android.synthetic.main.activity_main.*

class OptionsActivity : BaseBindAvtivity<OptionViewModel>() {

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
