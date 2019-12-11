package com.kotlinstrong.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.BR
import com.kotlinstrong.R
import com.kotlinstrong.base.TabFragment
import com.kotlinstrong.bean.AppMenuBean
import com.kotlinstrong.bean.Article
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.main.MainViewModel
import com.kotlinstrong.option.OptionsActivity
import com.kotlinstrong.stronglib.base.BaseAdapter
import com.kotlinstrong.stronglib.cutil.EncryptUtils
import com.kotlinstrong.stronglib.listener.Function
import com.kotlinstrong.stronglib.listener.LongFunction
import com.kotlinstrong.stronglib.listener.ViewMap
import com.kotlinstrong.utils.aspect.MyAnnotationOnclick
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_head_ads.*

class AppMenuFragment : TabFragment<MainViewModel>() , OnRefreshLoadMoreListener {

    private val requestCode = 0x1024

    override fun layoutId() = R.layout.fragment_app_menu

    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java

    private var mAdapter: BaseAdapter<AppMenuBean>? = null

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
        mAdapter = BaseAdapter(context, BR.data, object : ViewMap<AppMenuBean> {
            override fun layoutId(t: AppMenuBean): Int {
                return when {
                    t.type == AppMenuBean.TYPE_AD -> R.layout.layout_app_menu_ads
                    t.type == AppMenuBean.TYPE_TITLE -> R.layout.item_app_menu_text
                    else -> R.layout.item_app_menu_child
                }
            }
        })
        mAdapter!!.addEvent(BR.click, object : Function<AppMenuBean> {
            @MyAnnotationOnclick
            override fun call(view: View, t: AppMenuBean) {
                when(t.resId){
                    R.mipmap.icon_app_encrypt -> testEncrypt()
                }
            }
        })
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshLoadMoreListener(this)

        var list = mViewModel.getAppMenuList()
        mAdapter!!.setNewList(list)

        val gridLayoutManager = GridLayoutManager(context,3)
        gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val bean = mAdapter!!.getItem(position)
                var size: Int//根据类型返回不同长度的显示
                size = when {
                    bean.type == AppMenuBean.TYPE_AD || bean.type == AppMenuBean.TYPE_TITLE -> 3
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
    /** 加密测试 */
    fun testEncrypt(){
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE ) !== PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
                return
            }
        }
        EncryptUtils.test()
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

    override fun onDestroy() {
        super.onDestroy()
        pager_ads.onDestory()
    }

}