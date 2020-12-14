package com.kotlinstrong.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.R
import com.kotlinstrong.base.TabFragment
import com.kotlinstrong.bean.AppMenuBean
import com.kotlinstrong.home.item.MenuContentBindItem
import com.kotlinstrong.home.item.MenuTitleBindItem
import com.kotlinstrong.main.MainViewModel
import com.kotlinstrong.stronglib.base.BaseAdapter
import com.kotlinstrong.stronglib.bean.BaseBindItem
import com.kotlinstrong.stronglib.binding.BaseBindViewHolder
import com.kotlinstrong.stronglib.cutil.EncryptUtils
import com.kotlinstrong.stronglib.listener.Function
import com.kotlinstrong.stronglib.util.system.BatteryUtils
import com.kotlinstrong.utils.aspect.MyAnnotationOnclick
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_app_menu.*

class AppMenuFragment : TabFragment<MainViewModel>() , OnRefreshLoadMoreListener {

    private val requestCode = 0x1024

    override fun layoutId() = R.layout.fragment_app_menu

    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java

    private var mAdapter: BaseAdapter<BaseBindItem> = BaseAdapter()

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)

        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshLoadMoreListener(this)

        val dataList = mViewModel.getAppMenuList()

        val list = ArrayList<BaseBindItem>()
        for (data in dataList){
            when(data.type){
                AppMenuBean.TYPE_TITLE -> list.add(MenuTitleBindItem(data))
                else -> list.add(MenuContentBindItem(data))
            }
        }
        mAdapter.setNewList(list)

        val gridLayoutManager = GridLayoutManager(context,3)
        gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val bean = mAdapter.getItem(position)
                var size: Int//根据类型返回不同长度的显示
                size = when {
                    bean is MenuTitleBindItem -> 3
                    else -> 1
                }
                return size
            }
        }
        //调用这段代码之前需要先setAdapter才能生效
        recyclerView.layoutManager = gridLayoutManager
    }

    override fun modelObserve() {
        mViewModel.apply {

        }
    }

    fun checkFilePermission(): Boolean{
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
                return false
            }
        }
        return true
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        refreshLayout.finishLoadMore()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        refreshLayout.finishRefresh()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            requestCode -> if (grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                EncryptUtils.test()
            }else{
                ToastUtils.showShort("没有权限")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_FIRST_USER){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when{
                    BatteryUtils.isIgnoringBatteryOptimizations() -> {
                        ToastUtils.showShort("加入成功,为你自动跳转系统设置,进一步优化!")
                        Handler().postDelayed({
                            BatteryUtils.setAppIgnore()
                        },1000)
                    } else -> {
                        ToastUtils.showShort("加入失败")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private val click = object : Function {
        @MyAnnotationOnclick
        override fun click(view: View, position: Int) {
            val bean : AppMenuBean = mAdapter.getItem(position) as AppMenuBean
            when(bean.resId){
                R.mipmap.icon_app_encrypt ->
                    if (checkFilePermission()){
                        EncryptUtils.test()
                        ToastUtils.showShort("前往${EncryptUtils.path}目录查看结果")
                    }
                R.mipmap.icon_app_signature -> ToastUtils.showShort("验证结果为：${EncryptUtils.checkSignature()}")
                R.mipmap.icon_app_cut ->
                    if (checkFilePermission()){
                        EncryptUtils.fileSplit()
                        ToastUtils.showShort("前往${EncryptUtils.path}目录查看结果")
                    }
                R.mipmap.icon_app_merge ->
                    if (checkFilePermission()){
                        EncryptUtils.fileMerge()
                        ToastUtils.showShort("前往${EncryptUtils.path}目录查看结果")
                    }
                R.mipmap.icon_app_battery ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!BatteryUtils.isIgnoringBatteryOptimizations()) {
                            BatteryUtils.requestIgnoreBatteryOptimizations(this@AppMenuFragment)
                        }else{
                            ToastUtils.showShort("已经在名单中")
                        }
                    }
            }
        }
    }

}