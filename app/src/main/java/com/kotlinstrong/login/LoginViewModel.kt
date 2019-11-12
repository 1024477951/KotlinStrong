package com.kotlinstrong.login

import com.kotlinstrong.stronglib.base.BaseViewModel

class LoginViewModel : BaseViewModel() {

    private val tag: String = "LoginViewModel"

    private val repository by lazy { LoginRepository() }

}