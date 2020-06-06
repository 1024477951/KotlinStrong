package com.kotlinstrong.view.banner

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.kotlinstrong.stronglib.base.BaseAdapter
import com.kotlinstrong.stronglib.bean.BaseBindItem

open class AdsPager(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    private lateinit var mAdapter: BaseAdapter<BaseBindItem>
    private var isLoop: Boolean = false
    private lateinit var layoutMan: LoopLayoutManager
    private var loopTime: Long = 2000
    private var position: Int = 0

    constructor(context:Context):this(context,null)

    private var mHandler = Handler(Handler.Callback {
        false
    })
    private lateinit var runnable: Runnable

    init {
        init()
    }

    private fun init() {
        layoutMan = LoopLayoutManager()
        layoutManager = layoutMan
        mAdapter = BaseAdapter()
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
            mHandler.postDelayed(runnable,loopTime)
        }
        if (!isLoop) {//预防多次启动
            startLoop()
            mHandler.postDelayed(runnable,loopTime)
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

    open fun setList(items: MutableList<String>){
        val list: MutableList<BaseBindItem> = ArrayList()
        for (url in items){
            list.add(BannerBindItem(url))
        }
        mAdapter.setNewList(list)
    }

    /**
     * 滑动到指定位置
     */
    private fun smoothMoveToPosition(position: Int) {
        smoothScrollToPosition(position)
    }

}