package com.strong.ui.sort.tab

import android.os.Bundle
import com.strong.R
import com.strong.databinding.FragmentTab2Binding
import com.strong.ui.base.BaseBindFragment
import com.strong.ui.sort.SortViewModel


class Tab2Fragment : BaseBindFragment<FragmentTab2Binding, SortViewModel>() {

    override fun layoutId() = R.layout.fragment_tab2

    override fun providerVMClass() = SortViewModel::class.java

    override fun initData(bundle: Bundle?) {
        binding.model = mViewModel

    }

    override fun modelObserve() {

    }

}