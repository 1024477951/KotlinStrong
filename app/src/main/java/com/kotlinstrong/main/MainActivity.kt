package com.kotlinstrong.main

import android.os.Bundle
import android.util.SparseArray
import android.view.KeyEvent
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.kotlinstrong.R
import com.kotlinstrong.base.BaseBindAvtivity
import com.kotlinstrong.stronglib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseBindAvtivity<MainViewModel>(), ViewPager.OnPageChangeListener {

    private var menus: SparseArray<CheckBox>? = SparseArray()
    private var adapter: BounceAdapter? = null

    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun layoutId() = R.layout.activity_main

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
        adapter = BounceAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = TabsHelper.getCount()
        viewPager.addOnPageChangeListener(this)

        addTab(home, 0)
        addTab(contact, 1)
        addTab(dynamic, 2)
        addTab(me, 3)

        testAspect()
        testAfterReturning()
    }

    fun testAspect(){}
    fun testAfterReturning():Int{ return 1 }

    override fun modelObserve() {
        super.modelObserve()
        mViewModel.apply {
            loginLiveData.observe(this@MainActivity, Observer {
                SPUtils.getInstance().put("token",it.HC_ACCESS_TOKEN)
                LogUtils.d("==>","success")
            })
        }
    }

    fun click(view: View) {
        var position = -1
        when (view.id) {
            R.id.home -> position = 0
            R.id.contact -> position = 1
            R.id.dynamic -> position = 2
            R.id.me -> position = 3
        }
        if (position >= 0) select(position)
    }

    /**
     * 添加首页按钮缓存
     */
    private fun addTab(view: View, position: Int) {
        val checkBox = view.findViewWithTag<CheckBox>("check")
        checkBox.isChecked = false
        menus!!.put(position, checkBox)
    }


    /**
     * 选择菜单
     */
    private fun select(position: Int) {
        if (menus!!.get(position).isChecked)
            return //防止执行多次
        for (i in 0 until menus!!.size()) {
            val box = menus!!.get(i) ?: continue
            box.isChecked = (i == position)
        }
        viewPager.setCurrentItem(position, false)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        select(position)
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        menus!!.clear()
        menus = null
    }

}
