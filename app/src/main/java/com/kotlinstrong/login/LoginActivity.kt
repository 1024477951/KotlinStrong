package com.kotlinstrong.login

import com.kotlinstrong.R
import com.kotlinstrong.base.BaseBindActivity

class LoginActivity : BaseBindActivity<LoginViewModel>() {

    override fun layoutId() = R.layout.activity_login

    override fun providerVMClass(): Class<LoginViewModel> = LoginViewModel::class.java
}