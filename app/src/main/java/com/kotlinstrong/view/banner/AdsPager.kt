package com.kotlinstrong.view.banner

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.kotlinstrong.BR
import com.kotlinstrong.R
import java.lang.ref.WeakReference

open class AdsPager(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    private var mAdapter: AdsPagerAdapter<String>? = null
    private var isLoop: Boolean = true
    private var layoutMan: LinearLayoutManager? = null
    private var adsTask: AdsTask? = null
    private var loopTime: Long = 2000

    constructor(context:Context):this(context,null)

    init {
        init()
    }

    private fun init() {
        layoutMan = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManager = layoutMan
        mAdapter = AdsPagerAdapter<String>(context, BR.data, R.layout.item_head_ads,isLoop)
        adapter = mAdapter
        PagerSnapHelper().attachToRecyclerView(this)
        adsTask = AdsTask(this)
        startLoop()
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        when(e!!.action){
            MotionEvent.ACTION_DOWN -> stopLoop()
            MotionEvent.ACTION_MOVE -> stopLoop()
            MotionEvent.ACTION_UP-> startLoop()
        }
        return super.onTouchEvent(e)
    }

    open fun startLoop(){
        isLoop = true
        postDelayed(adsTask,loopTime)
    }

    open fun stopLoop(){
        isLoop = false
        if (adsTask != null)
            removeCallbacks(adsTask)
    }

    open fun setList(list: MutableList<String>){
        mAdapter!!.setNewList(list)
    }

    internal class AdsTask : Runnable{

        private var weakRef: WeakReference<AdsPager>

        constructor(page: AdsPager){
            weakRef = WeakReference<AdsPager>(page)
        }

        override fun run() {
            val page = weakRef.get()
            if (page!!.isLoop){
                var position = page.layoutMan!!.findFirstVisibleItemPosition() + 1
                if (position >= page.adapter!!.itemCount){
                    position = 0
                }
                page.smoothScrollToPosition(position)
                page.postDelayed(page.adsTask,page.loopTime)
            }
        }

    }

}