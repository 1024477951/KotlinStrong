package com.strong.ui.sort.tab

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.blankj.utilcode.util.ToastUtils
import com.strong.R
import com.strong.databinding.FragmentTab1Binding
import com.strong.ui.base.BaseBindFragment
import com.strong.ui.sort.SortViewModel


class Tab1Fragment : BaseBindFragment<FragmentTab1Binding, SortViewModel>() {

    override fun layoutId() = R.layout.fragment_tab1

    override fun providerVMClass() = SortViewModel::class.java

    override fun initData(bundle: Bundle?) {
        binding.model = mViewModel
        setFragmentResultListener(
            "tab1"
        ) { key: String, bundle: Bundle ->
            Log.e("Tab1Fragment","${bundle.getString("data")}")
            ToastUtils.showShort("接收到的key $key value ${bundle.getString("data")}")
            binding.tvTab.text = bundle.getString("data")
        }
    }

    override fun modelObserve() {

    }


}