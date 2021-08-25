package com.strong.ui.home

import androidx.lifecycle.MutableLiveData
import com.strong.R
import com.strong.ui.base.BaseViewModel
import com.strong.ui.home.bean.BannerBean
import com.strong.ui.home.bean.MenuBean
import com.strong.ui.home.model.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    private val repository by lazy { HomeRepository() }

    val refreshHome: ()-> Unit = {
        getBanners()
    }

    val bannerLiveData: MutableLiveData<MutableList<BannerBean.BannerData>> = MutableLiveData()
    fun getBanners() {
        launchOnUI {
            //withContext是串行的，如果要并行需要使用async
            val result = withContext(Dispatchers.IO) {
                repository.getBanner()
            }
            result.data?.let {
                bannerLiveData.postValue(it.list)
            }
            getMenuList()
        }
    }

    val menuLiveData: MutableLiveData<ArrayList<MenuBean>> = MutableLiveData()
    fun getMenuList(){
        val list = ArrayList<MenuBean>()

        list.add(MenuBean("Jni相关",MenuBean.TYPE_TITLE,0))
        list.add(MenuBean("文件加密",MenuBean.TYPE_MENU, R.mipmap.icon_home_menu_encrypt))
        list.add(MenuBean("签名验证",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_signature))
        list.add(MenuBean("文件切割",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_cut))
        list.add(MenuBean("文件合并",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_merge))

        list.add(MenuBean("系统相关",MenuBean.TYPE_TITLE,0))
        list.add(MenuBean("加入白名单",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_battery))
        list.add(MenuBean("Batch operations",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_batch))
        list.add(MenuBean("字体大小",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_font))
        list.add(MenuBean("menu4",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_leave))
        list.add(MenuBean("menu5",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_leave))

        list.add(MenuBean("模块3",MenuBean.TYPE_TITLE,0))
        list.add(MenuBean("menu1",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word))
        list.add(MenuBean("menu2",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word))
        list.add(MenuBean("menu3",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word))
        list.add(MenuBean("menu4",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word))
        list.add(MenuBean("menu5",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word))
        list.add(MenuBean("menu6",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_word))

        list.add(MenuBean("模块4",MenuBean.TYPE_TITLE,0))
        list.add(MenuBean("menu1",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_pc))
        list.add(MenuBean("menu2",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_pc))
        list.add(MenuBean("menu3",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_pc))

        list.add(MenuBean("模块5",MenuBean.TYPE_TITLE,0))
        list.add(MenuBean("menu1",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_pc))
        list.add(MenuBean("menu2",MenuBean.TYPE_MENU,R.mipmap.icon_home_menu_pc))
        menuLiveData.postValue(list)
    }

}