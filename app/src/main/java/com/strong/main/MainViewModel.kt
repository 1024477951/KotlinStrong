package com.kotlinstrong.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.kotlinstrong.R
import com.kotlinstrong.bean.AppMenuBean
import com.kotlinstrong.stronglib.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.bean.LoginBean
import com.kotlinstrong.stronglib.base.BaseResponse
import com.kotlinstrong.stronglib.factory.ApiResponse

class MainViewModel : BaseViewModel() {

    private val tag: String = "MainViewModel"

    private val repository by lazy { MainRepository() }

    val loginLiveData: MutableLiveData<LoginBean> = MutableLiveData()

    fun searchRepos(){
        launch {
            val result = withContext(Dispatchers.IO) { repository.searchRepos() }
            LogUtils.d(result)
        }
    }
    /** 模拟请求数据 */
    fun getAdsList(): MutableList<String>{
        val list: MutableList<String> = ArrayList<String>()
        list.add("https://images0.cnblogs.com/blog/583064/201410/101848223904099.jpg")
        list.add("https://images0.cnblogs.com/blog/583064/201410/101843420305548.jpg")
        list.add("https://images0.cnblogs.com/blog/583064/201410/101843562809501.jpg")
        list.add("https://images0.cnblogs.com/blog/583064/201410/101901331557216.jpg")
        return list
    }

    fun getAppMenuList(): MutableList<AppMenuBean>{
        val list: MutableList<AppMenuBean> = ArrayList<AppMenuBean>()

        list.add(AppMenuBean("Jni相关",AppMenuBean.TYPE_TITLE,0,null))
        list.add(AppMenuBean("文件加密",AppMenuBean.TYPE_MENU,R.mipmap.icon_app_encrypt,null))
        list.add(AppMenuBean("签名验证",AppMenuBean.TYPE_MENU,R.mipmap.icon_app_signature,null))
        list.add(AppMenuBean("文件切割",AppMenuBean.TYPE_MENU,R.mipmap.icon_app_cut,null))
        list.add(AppMenuBean("文件合并",AppMenuBean.TYPE_MENU,R.mipmap.icon_app_merge,null))

        list.add(AppMenuBean("模块2",AppMenuBean.TYPE_TITLE,0,null))
        list.add(AppMenuBean("加入白名单",AppMenuBean.TYPE_MENU,R.mipmap.icon_app_battery,null))
        list.add(AppMenuBean("menu2",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_leave,null))
        list.add(AppMenuBean("menu3",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_leave,null))
        list.add(AppMenuBean("menu4",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_leave,null))
        list.add(AppMenuBean("menu5",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_leave,null))

        list.add(AppMenuBean("模块3",AppMenuBean.TYPE_TITLE,0,null))
        list.add(AppMenuBean("menu1",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_word,null))
        list.add(AppMenuBean("menu2",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_word,null))
        list.add(AppMenuBean("menu3",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_word,null))
        list.add(AppMenuBean("menu4",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_word,null))
        list.add(AppMenuBean("menu5",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_word,null))
        list.add(AppMenuBean("menu6",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_word,null))

        list.add(AppMenuBean("模块4",AppMenuBean.TYPE_TITLE,0,null))
        list.add(AppMenuBean("menu1",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_pc,null))
        list.add(AppMenuBean("menu2",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_pc,null))
        list.add(AppMenuBean("menu3",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_pc,null))

        list.add(AppMenuBean("模块5",AppMenuBean.TYPE_TITLE,0,null))
        list.add(AppMenuBean("menu1",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_pc,null))
        list.add(AppMenuBean("menu2",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_pc,null))
        return list
    }

    fun login() {

    }
}