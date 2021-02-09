package com.strong.ui.view.water

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import android.view.SurfaceHolder
import com.strong.R
import com.strong.provider.KtxProvider

/**
 * created by YooJin.
 * date: 2021/2/4 15:01
 * desc:绘制工具类--用于测试波浪控件，好对比修改前的版本
 */
class WaterUtils(private val holder: SurfaceHolder) : Thread() {

    private var mWidth: Float = 0f
    private var mHeight: Float = 0f
    //波浪的高度
    private var mWaterHeight: Float = 0f
    //起伏的高度
    private var mWaterUp: Float = 0f
    /**
     * off = 偏移值
     */
    private var upY:Float = 0f
    private  var offy:Float = 0f
    /**
     * speed = 速度
     */
    private  var speedy:Float = 2f

    private var path: Path = Path()
    private var paint: Paint = Paint()
    private var isRun: Boolean = false

    init {
        // 去除画笔锯齿
        paint.isAntiAlias = true
        // 设置风格为实线
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
    }

    fun init(width: Int,height: Int){
        mWidth = width.toFloat()
        mHeight = height.toFloat()
        //波浪的高度
        mWaterHeight = mHeight / 2
        //起伏的高度
        mWaterUp = mWaterHeight * 2 / 3
    }

    fun runDraw(){
        isRun = true
        start()
    }

    fun stopDraw(){
        isRun = false
    }

    override fun run() {
        var canvas: Canvas?
        while (isRun){
            canvas = holder.lockCanvas()
            //清除画布
            canvas.drawColor(KtxProvider.mContext.getColor(R.color.text_color_titleBar_title),android.graphics.PorterDuff.Mode.CLEAR)
            paint.color = KtxProvider.mContext.getColor(R.color.bg_color_3159c7_83)

            path.reset()
            //起点
            path.moveTo(-mWidth, mWaterHeight)
            path.quadTo(-mWidth / 4, mWaterHeight - mWaterUp, -mWidth / 2, mWaterHeight)
            path.quadTo(-mWidth * 3 / 4, mWaterHeight + mWaterUp, 0f, mWaterHeight)
            path.quadTo(mWidth / 4, mWaterHeight - mWaterUp, mWidth / 2, mWaterHeight)
            path.quadTo(mWidth * 3 / 4, mWaterHeight + mWaterUp, mWidth, mWaterHeight)
            //闭合操作
            path.lineTo(mWidth, mHeight)
            path.lineTo(0f, mHeight)
            path.close()
            canvas.drawPath(path, paint)
            // 解除锁定，并提交修改内容
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun water1(canvas: Canvas,starPoint:FloatArray,quad1Point:FloatArray,quad2Point:FloatArray){
        path.reset()
        path.moveTo(starPoint[0], starPoint[1])
        //绘制二级贝塞尔,一个二级曲线代表一个扇形
        path.quadTo(quad1Point[0], quad1Point[1], quad1Point[2], quad1Point[3])
        //反向扇形
        path.quadTo(quad2Point[0], quad2Point[1], quad2Point[2], quad2Point[3])
        //闭合区域
        path.lineTo(mWidth, mHeight)
        path.lineTo(0f, mHeight)
        path.close()
        //绘制二级贝塞尔弧形
        canvas.drawPath(path, paint)
    }

    private fun water2(canvas: Canvas,starPoint:FloatArray,quad3Point:FloatArray){
        path.reset()
        path.moveTo(starPoint[0], starPoint[1])
        path.quadTo(quad3Point[0], quad3Point[1], quad3Point[2], quad3Point[3])
        path.lineTo(mWidth, mHeight)
        path.lineTo(0f, mHeight)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun water3(canvas: Canvas,starPoint:FloatArray,quad1Point:FloatArray,quad2Point:FloatArray){
        path.reset()
        path.moveTo(starPoint[0], starPoint[1])
        //绘制二级贝塞尔,一个二级曲线代表一个扇形
        path.quadTo(quad1Point[0], quad2Point[1], quad1Point[2], quad1Point[3])
//            path.moveTo(mWidth / 2, mWaterHeight)
        //反向扇形
        path.quadTo(quad2Point[0], quad1Point[1], quad2Point[2], quad2Point[3])

        //闭合区域
        path.lineTo(mWidth, mHeight)
        path.lineTo(0f, mHeight)
        path.close()
        //绘制二级贝塞尔弧形
        canvas.drawPath(path, paint)
    }

}