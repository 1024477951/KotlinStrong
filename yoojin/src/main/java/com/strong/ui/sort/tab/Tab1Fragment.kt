package com.strong.ui.sort.tab

import android.os.Bundle
import com.strong.R
import com.strong.databinding.FragmentTab1Binding
import com.strong.ui.base.BaseBindFragment
import com.strong.ui.sort.SortViewModel


class Tab1Fragment : BaseBindFragment<FragmentTab1Binding, SortViewModel>() {

    override fun layoutId() = R.layout.fragment_tab1

    override fun providerVMClass() = SortViewModel::class.java

    override fun initData(bundle: Bundle?) {
        binding.model = mViewModel

    }

    override fun modelObserve() {

    }


}