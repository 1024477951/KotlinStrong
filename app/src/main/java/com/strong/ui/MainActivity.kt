package com.strong.ui

import com.strong.baselib.base.BaseVMActivity
import com.strong.baselib.base.BaseViewModel
import com.strong.baselib.setSingleClick
import com.strong.databinding.ActivityMainBinding
//import io.flutter.embedding.android.FlutterActivity

class MainActivity : BaseVMActivity<BaseViewModel, ActivityMainBinding>() {

    override fun initView() {
        mBinding.ivCircle.setSingleClick {
//            startActivity(
//                FlutterActivity
//                    .withNewEngine()
//                    .initialRoute("/ListPage")
//                    .build(this)
//            )
        }
    }

    override fun initData() {

    }

    override fun providerVMClass() = BaseViewModel::class.java

}