package com.kotlinstrong.view.banner

import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils

open class LoopSnapHelper(private val callback: CallBack) : PagerSnapHelper() {
    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int
    ): Int {
        val pos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        val nextPos = pos % layoutManager.itemCount
//        LogUtils.e("LoopSnapHelper","pos $pos nextPos $nextPos")
        callback?.changePosition(nextPos)
        return nextPos
    }

    interface CallBack{
        /** 手势滑动改变后的position */
        fun changePosition(position: Int)
    }

}