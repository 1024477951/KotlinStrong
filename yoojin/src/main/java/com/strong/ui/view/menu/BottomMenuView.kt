package com.strong.ui.view.menu

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.strong.R

/**
 * created by YooJin.
 * date: 2021/1/5 14:24
 */
open class BottomMenuView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context!!, attrs, defStyleAttr){

    /* 菜单集合 */
    private var menus: SparseArray<CheckBox> = SparseArray()
    private var titles: MutableList<String>? = null

    constructor(context:Context,attrs: AttributeSet?):this(context,attrs,0)

    constructor(context:Context):this(context,null)

    @SuppressLint("InflateParams")
    private fun init(){
        for (i in 0 until titles?.size!!) {
            val bottomMenu = LayoutInflater.from(context).inflate(R.layout.layout_tab_bottom_menu,this,false)
            addTab(bottomMenu,i)
        }
    }

    /** 添加首页按钮缓存 */
    private fun addTab(view: View, position: Int) {
        addView(view)
        val tvMenu = view.findViewById<TextView>(R.id.tv_menu)
        if (position < titles?.size!!) {
            tvMenu.text = titles?.get(position)
        }
        view.setOnClickListener {
            select(position)
        }
        val checkBox = view.findViewById<CheckBox>(R.id.cb_menu)
        checkBox.isChecked = position == 0
        menus.put(position, checkBox)
    }

    /** 选择菜单 */
    fun select(position: Int) {
        if (menus.get(position).isChecked)
            return //防止重复执行
        for (i in 0 until menus.size()) {
            val box = menus.get(i)
            box.isChecked = (i == position)
        }
        callBack?.click(position)
    }

    fun setTitles(list: MutableList<String>){
        titles = list
        weightSum = titles!!.size.toFloat()
        init()
    }

    private var callBack: CallBack? = null
    interface CallBack{
        fun click(position: Int)
    }

    fun setCallBack(callBack: CallBack?){
        this.callBack = callBack
    }

}