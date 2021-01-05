package com.strong.ui

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.CheckBox
import com.strong.ui.base.BaseFragment

/**
 * created by YooJin.
 * date: 2020/12/30 14:57
 * desc:
 */
class TabFragment : BaseFragment() {

    /* 菜单集合 */
    private var menus: SparseArray<CheckBox> = SparseArray()

    override fun layoutId() = R.layout.fragment_tab

    override fun initData(bundle: Bundle?) {

    }

    fun click(view: View) {
        var position = -1
        when (view.id) {
            R.id.rl_home -> position = 0
            R.id.rl_menu -> position = 1
        }
        if (position >= 0) select(position)
    }

    /**
     * 选择菜单
     */
    private fun select(position: Int) {
        if (menus.get(position).isChecked)
            return //防止执行多次
        for (i in 0 until menus.size()) {
            val box = menus.get(i) ?: continue
            box.isChecked = (i == position)
        }
        //viewPager.setCurrentItem(position, false)
    }

}