package com.strong.ui.view.water

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.util.Log
import android.view.TextureView

/**
 * created by YooJin.
 * date: 2021/2/4 14:44
 * desc:波浪动画
 */
class WaterBgTextureView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    TextureView(context!!, attrs, defStyleAttr), TextureView.SurfaceTextureListener {

    init {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0)

    constructor(context: Context):this(context, null)

    private lateinit var drawWater: TextureViewWaterUtils


    private fun init() {
        surfaceTextureListener = this
        drawWater = TextureViewWaterUtils(this)
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed){
            drawWater.init(width,height)
        }
    }

    override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
        if (!drawWater.isAlive) {
            //Log.e("===","run")
            drawWater.runDraw()
        }else{
            //Log.e("===","notify")
            drawWater.resumeThread()
        }
    }

    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
        //Log.e("===","onSurfaceTextureSizeChanged")
    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
        //Log.e("===","onSurfaceTextureDestroyed")
        drawWater.stopDraw()
        return true
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {

    }

}