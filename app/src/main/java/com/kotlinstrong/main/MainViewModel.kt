package com.kotlinstrong.main

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.JsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.R
import com.kotlinstrong.bean.AppMenuBean
import com.kotlinstrong.stronglib.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.bean.LoginBean
import com.kotlinstrong.utils.aspect.MyAnnotationTime

class MainViewModel : BaseViewModel() {

    private val tag: String = "MainViewModel"

    private val repository by lazy { MainRepository() }

    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()

    val loginLiveData: MutableLiveData<LoginBean> = MutableLiveData()

    @MyAnnotationTime
    fun getArticleList(page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getArticleList(page) }
            executeResponse(
                result,
                { mArticleList.postValue(result.data) },
                { mArticleList.postValue(result.data) }
            )
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
        list.add(AppMenuBean(null,AppMenuBean.TYPE_AD,0,getAdsList()))

        list.add(AppMenuBean("模块1",AppMenuBean.TYPE_TITLE,0,null))
        list.add(AppMenuBean("文件加密",AppMenuBean.TYPE_MENU,R.mipmap.icon_app_encrypt,null))
        list.add(AppMenuBean("menu2",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_right,null))
        list.add(AppMenuBean("menu3",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_right,null))
        list.add(AppMenuBean("menu4",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_right,null))

        list.add(AppMenuBean("模块2",AppMenuBean.TYPE_TITLE,0,null))
        list.add(AppMenuBean("menu1",AppMenuBean.TYPE_MENU,R.mipmap.app_icon_leave,null))
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
        launch {
            val result = withContext(Dispatchers.IO) { repository.login() }
            executeResponse(result, { loginLiveData.postValue(result.data) }, { LogUtils.e(tag, GsonUtils.toJson(result)) })
        }
    }
}