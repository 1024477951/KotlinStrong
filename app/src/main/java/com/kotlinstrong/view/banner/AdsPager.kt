package com.kotlinstrong.view.banner

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.BR
import com.kotlinstrong.R
import com.kotlinstrong.stronglib.listener.Function
import java.lang.ref.WeakReference

open class AdsPager(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    private var mAdapter: AdsPagerAdapter<String>? = null
    private var isLoop: Boolean = false
    private var layoutMan: LoopLayoutManager? = null
    private var loopTime: Long = 2000
    private var position: Int = 0

    constructor(context:Context):this(context,null)

    private var mHandler = Handler(Handler.Callback {
        false
    })
    private var runnable: Runnable? = null

    init {
        init()
    }

    private fun init() {
        layoutMan = LoopLayoutManager()
        layoutManager = layoutMan
        mAdapter = AdsPagerAdapter(context, BR.data, R.layout.item_head_ads)
        mAdapter!!.addEvent(BR.click,object : Function<String> {
            override fun call(view: View, t: String) {
                ToastUtils.showShort(t)
            }
        })
        adapter = mAdapter
        LoopSnapHelper(object : LoopSnapHelper.CallBack {
            override fun changePosition(p: Int) {
                position = p
            }
        }).attachToRecyclerView(this)
        runnable = Runnable {
            if (isLoop) {
                position %= adapter!!.itemCount
                smoothMoveToPosition(position)
                ++position
            }
            mHandler!!.postDelayed(runnable,loopTime)
        }
        if (!isLoop) {//预防多次启动
            startLoop()
            mHandler!!.postDelayed(runnable,loopTime)
        }
//        LogUtils.e("AdsPager","init startTime $loopTime")
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
    }

    open fun stopLoop(){
        if (isLoop) {
            isLoop = false
        }
    }

    open fun onDestory(){
        stopLoop()
        mHandler.removeCallbacks(runnable)
    }

    open fun setList(list: MutableList<String>){
        mAdapter!!.setNewList(list)
    }

    /**
     * 滑动到指定位置
     */
    private fun smoothMoveToPosition(position: Int) {
        smoothScrollToPosition(position)
    }

}