package com.strong.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.strong.R
import com.strong.databinding.FragmentHomeBinding
import com.strong.ui.adapter.BaseAdapter
import com.strong.ui.base.BaseBindFragment
import com.strong.ui.home.bean.MenuBean
import com.strong.ui.home.click.FunctionClick
import com.strong.ui.home.item.HomeBannerBindItem
import com.strong.ui.home.item.MenuContentBindItem
import com.strong.ui.home.item.MenuTitleBindItem
import com.strong.utils.encrypt.EncryptUtils
import com.strong.utils.aspect.MyAnnotationOnclick
import com.strong.utils.scroller.ScrollerCallBack
import com.strong.utils.scroller.ScrollerUtils
import com.strong.utils.selector.PictureSelectorUtils
import com.strong.utils.system.BatteryUtils
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseBindFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun layoutId() = R.layout.fragment_home

    override fun providerVMClass() = HomeViewModel::class.java

    private var mAdapter = BaseAdapter()

    override fun initData(bundle: Bundle?) {
        binding.model = mViewModel
        initList()
        //切面测试
        testAspect()
        testAfterReturning()
    }

    fun testAspect() {}
    fun testAfterReturning(): Int {
        return 666
    }

    private fun initList() {
        mViewModel.getBanners()
        ScrollerUtils.scroller(binding.recyclerView, object : ScrollerCallBack {

            override fun alpha(alpha: Float) {
                binding.tvTitle.alpha = alpha
                binding.toolbar.alpha = 1f - alpha
                //Log.e("===", "(1f - alpha) ${1f - alpha} alpha $alpha")
            }

        })
        binding.recyclerView.adapter = mAdapter
        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                //根据类型返回不同长度的显示
                return when (mAdapter.list?.get(position)) {
                    is HomeBannerBindItem -> 3
                    is MenuTitleBindItem -> 3
                    else -> 1
                }
            }
        }
        //调用这段代码之前需要先setAdapter才能生效
        binding.recyclerView.layoutManager = gridLayoutManager
    }

    override fun modelObserve() {
        mViewModel.bannerLiveData.observe(this, {
            if (it != null) {
                mAdapter.setItem(HomeBannerBindItem(it))
            }
            mViewModel.getMenuList()
        })
        mViewModel.menuLiveData.observe(this, {
            if (it != null) {
                val list = ArrayList<Any>()
                for (data in it) {
                    when (data.type) {
                        MenuBean.TYPE_TITLE -> list.add(MenuTitleBindItem(data))
                        else -> list.add(MenuContentBindItem(data, click))
                    }
                }
                mAdapter.addItems(list)
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            0 ->
                if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {

                } else {
                    ToastUtils.showShort("没有权限")
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when {
                    BatteryUtils.isIgnoringBatteryOptimizations() -> {
                        ToastUtils.showShort("加入成功,为你自动跳转系统设置,进一步优化!")
                        Handler(Looper.getMainLooper()).postDelayed({
                            BatteryUtils.setAppIgnore()
                        }, 1000)
                    }
                    else -> {
                        ToastUtils.showShort("加入失败")
                    }
                }
            }
        } else if (requestCode == Activity.DEFAULT_KEYS_SHORTCUT) {
            if (resultCode == Activity.RESULT_OK) {
                ToastUtils.showShort("Write permissions are granted", Toast.LENGTH_SHORT)
            } else {
                ToastUtils.showShort("Write permissions are denied", Toast.LENGTH_SHORT)
            }
        }
    }

    private val click = object : FunctionClick {
        @MyAnnotationOnclick
        override fun click(resId: Int) {
            when (resId) {
                R.mipmap.icon_home_menu_encrypt ->
                    if (checkFilePermission()) {
                        EncryptUtils.test()
                        ToastUtils.showShort("前往${EncryptUtils.path}目录查看结果")
                    }
                R.mipmap.icon_home_menu_signature -> ToastUtils.showShort("验证结果为：${EncryptUtils.checkSignature()}")
                R.mipmap.icon_home_menu_cut ->
                    if (checkFilePermission()) {
                        EncryptUtils.fileSplit()
                        ToastUtils.showShort("前往${EncryptUtils.path}目录查看结果")
                    }
                R.mipmap.icon_home_menu_merge ->
                    if (checkFilePermission()) {
                        EncryptUtils.fileMerge()
                        ToastUtils.showShort("前往${EncryptUtils.path}目录查看结果")
                    }
                R.mipmap.icon_home_menu_battery ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!BatteryUtils.isIgnoringBatteryOptimizations()) {
                            BatteryUtils.requestIgnoreBatteryOptimizations(this@HomeFragment)
                        } else {
                            ToastUtils.showShort("已经在名单中")
                        }
                    }
                R.mipmap.icon_home_menu_batch ->
                    PictureSelectorUtils.openPicture(
                        this@HomeFragment,
                        object : OnResultCallbackListener<LocalMedia> {
                            override fun onResult(result: MutableList<LocalMedia>?) {
                                if (result != null) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                        val urisToModify = Uri.parse(result[0].realPath)
                                        val editPendingIntent = MediaStore.createWriteRequest(
                                            requireActivity().contentResolver,
                                            arrayListOf(urisToModify)
                                        )
                                        startIntentSenderForResult(
                                            editPendingIntent.intentSender,
                                            Activity.DEFAULT_KEYS_SHORTCUT,
                                            null,
                                            0,
                                            0,
                                            0,
                                            requireArguments()
                                        )
                                    } else {
                                        ToastUtils.showShort("Write permissions are granted")
                                    }
                                }
                            }

                            override fun onCancel() {

                            }

                        }, null
                    )
            }
        }
    }

    fun checkFilePermission(): Boolean {
        if (!Environment.isExternalStorageManager()) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            return false
        }
        return true
    }

}