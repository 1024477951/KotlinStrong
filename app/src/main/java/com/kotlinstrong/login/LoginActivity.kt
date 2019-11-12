package com.kotlinstrong.login

import com.kotlinstrong.R
import com.kotlinstrong.base.BaseBindAvtivity

class LoginActivity : BaseBindAvtivity<LoginViewModel>() {

    override fun layoutId() = R.layout.activity_login

    override fun providerVMClass(): Class<LoginViewModel> = LoginViewModel::class.java
}