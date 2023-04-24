package com.strong.ui.view.water

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * created by YooJin.
 * date: 2021/2/4 14:44
 * desc:波浪动画,SurfaceView无法alpha和视图动画
 */
class WaterBgSurfaceView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    init {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private lateinit var surfaceViewWater: SurfaceViewWaterUtils


    private fun init() {
        holder.addCallback(this)
        //透明处理
        holder.setFormat(PixelFormat.TRANSLUCENT)
        setZOrderOnTop(true)
        setZOrderMediaOverlay(true)
        surfaceViewWater = SurfaceViewWaterUtils(holder)
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            surfaceViewWater.init(width, height)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        //Log.e("===","surfaceCreated drawWater.isAlive ${drawWater.isAlive}")
        if (!surfaceViewWater.isAlive) {
            //Log.e("===","run")
            surfaceViewWater.runDraw()
        } else {
            //Log.e("===","notify")
            surfaceViewWater.resumeThread()
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        //Log.e("===","surfaceDestroyed")
        surfaceViewWater.stopDraw()
    }

}