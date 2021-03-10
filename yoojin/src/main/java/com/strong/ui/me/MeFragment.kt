package com.strong.ui.me


import android.os.*
import com.blankj.utilcode.util.ToastUtils
import com.strong.R
import com.strong.databinding.FragmentMeBinding
import com.strong.ui.base.BaseBindFragment
import com.strong.ui.me.click.FunctionClick


class MeFragment : BaseBindFragment<FragmentMeBinding, MeViewModel>() {

    override fun layoutId() = R.layout.fragment_me

    override fun providerVMClass() = MeViewModel::class.java

    override fun initData(bundle: Bundle?) {
        binding.model = mViewModel
        binding.click = click
        initList()
    }

    private fun initList() {
        mViewModel.getUser()
    }

    override fun modelObserve() {

    }

    private val click = object : FunctionClick {

        override fun onLogin() {
            ToastUtils.showShort("onLogin")
        }

    }

}