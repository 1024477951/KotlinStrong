package com.strong.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.strong.R
import com.strong.ui.base.BaseViewModel
import com.strong.ui.home.bean.ArticleList
import com.strong.ui.home.bean.MenuBean
import com.strong.ui.home.model.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    private val repository by lazy { HomeRepository() }

    val testLiveData: MutableLiveData<ArticleList> = MutableLiveData()

    fun getTestList() {
        launchOnUI {
            val result = withContext(Dispatchers.IO) {
                repository.getTestList()
            }
            testLiveData.postValue(result.data)
            Log.e("result",GsonUtils.toJson(result.data))
        }
    }

    fun getMenuList(): MutableList<MenuBean>{
        val list: MutableList<MenuBean> = ArrayList()

        list.add(MenuBean("Jni相关",MenuBean.TYPE_TITLE,0,null))
        list.add(MenuBean("文件加密",MenuBean.TYPE_MENU, R.mipmap.icon_home_menu_encrypt,null))
        list.add(MenuBean("签名验证",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_signature,null))
        list.add(MenuBean("文件切割",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_cut,null))
        list.add(MenuBean("文件合并",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_merge,null))

        list.add(MenuBean("模块2",MenuBean.TYPE_TITLE,0,null))
        list.add(MenuBean("加入白名单",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_battery,null))
        list.add(MenuBean("menu2",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_leave,null))
        list.add(MenuBean("menu3",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_leave,null))
        list.add(MenuBean("menu4",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_leave,null))
        list.add(MenuBean("menu5",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_leave,null))

        list.add(MenuBean("模块3",MenuBean.TYPE_TITLE,0,null))
        list.add(MenuBean("menu1",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word,null))
        list.add(MenuBean("menu2",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word,null))
        list.add(MenuBean("menu3",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word,null))
        list.add(MenuBean("menu4",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word,null))
        list.add(MenuBean("menu5",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word,null))
        list.add(MenuBean("menu6",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word,null))

        list.add(MenuBean("模块4",MenuBean.TYPE_TITLE,0,null))
        list.add(MenuBean("menu1",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_pc,null))
        list.add(MenuBean("menu2",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_pc,null))
        list.add(MenuBean("menu3",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_pc,null))

        list.add(MenuBean("模块5",MenuBean.TYPE_TITLE,0,null))
        list.add(MenuBean("menu1",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_pc,null))
        list.add(MenuBean("menu2",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_pc,null))
        return list
    }

}