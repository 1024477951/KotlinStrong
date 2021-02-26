package com.strong.ui.sort

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.strong.R
import com.strong.databinding.FragmentSortBinding
import com.strong.ui.base.BaseImmersionBindFragment
import com.strong.ui.sort.tab.Tab1Fragment
import com.strong.ui.sort.tab.Tab2Fragment


class SortFragment : BaseImmersionBindFragment<FragmentSortBinding, SortViewModel>() {

    override fun layoutId() = R.layout.fragment_sort

    override fun providerVMClass() = SortViewModel::class.java

    override fun statusView() = binding.status

    private val titleList = arrayOf("tab1", "tab2", "tab3", "tab4", "tab5")
    private val fragmentList = arrayListOf<Fragment>()
    private val tab1Fragment by lazy { Tab1Fragment() }
    private val tab2Fragment by lazy { Tab2Fragment() }
    private val tab3Fragment by lazy { Tab1Fragment() }
    private val tab4Fragment by lazy { Tab2Fragment() }
    private val tab5Fragment by lazy { Tab1Fragment() }

    override fun initData(bundle: Bundle?) {
        binding.model = mViewModel
        fragmentList.run {
            add(tab1Fragment)
            add(tab2Fragment)
            add(tab3Fragment)
            add(tab4Fragment)
            add(tab5Fragment)
        }
        initViewPager()
        binding.fabTab.setOnClickListener {
            childFragmentManager.setFragmentResult(
                "tab1",
                bundleOf("data" to "你就叫tab1")
            )
            childFragmentManager.setFragmentResult(
                "tab2",
                bundleOf("data" to "你就叫tab2")
            )
        }
    }

    private fun initViewPager() {
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = fragmentList[position]

            override fun getItemCount() = titleList.size
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    override fun modelObserve() {

    }

}