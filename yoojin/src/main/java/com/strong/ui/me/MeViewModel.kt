package com.strong.ui.me

import androidx.databinding.ObservableField
import com.blankj.utilcode.util.ToastUtils
import com.strong.baselib.base.BaseViewModel
import com.strong.ui.me.model.MeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MeViewModel : BaseViewModel() {

    private val repository by lazy { MeRepository() }

    val refreshMe: () -> Unit = { getUser() }

    val avatarField = ObservableField<String?>()
    val nickNameField = ObservableField("登录/注册")
    val mobileField = ObservableField("登录后可享受更多服务哦~")
    fun getUser() {
        launchOnUI {
            val result = withContext(Dispatchers.IO) {
                repository.getUser()
            }
            if (result.data != null) {
                with(result.data) {
                    avatarField.set(avatar)
                    nickNameField.set(nickname)
                    mobileField.set(getPwdMobile())
                }
            } else {
                ToastUtils.showShort("获取信息失败 msg ${result.msg}")
            }
        }
    }

}