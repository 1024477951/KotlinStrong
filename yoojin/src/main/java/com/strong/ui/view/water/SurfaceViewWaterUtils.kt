package com.strong.ui.view.water

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import android.view.SurfaceHolder
import android.view.animation.LinearInterpolator
import com.strong.R
import com.strong.provider.KtxProvider
import okhttp3.internal.notify
import okhttp3.internal.wait


/**
 * created by YooJin.
 * date: 2021/2/4 15:01
 * desc:绘制工具类
 */
class SurfaceViewWaterUtils(private val holder: SurfaceHolder) : Thread() {

    private var mWidth: Float = 0f
    private var mHeight: Float = 0f

    //波浪的高度
    private var mWaterHeight: Float = 0f

    //起伏的高度
    private var mWaterUp: Float = 0f

    /**
     * off = 偏移值
     */
    private var offx: Float = 0f

    private var path: Path = Path()
    private var paint: Paint = Paint()
    private var isRun: Boolean = false

    private var circlePaint: Paint = Paint()

    private lateinit var mValueAnimator: ValueAnimator

    init {
        // 去除画笔锯齿
        paint.isAntiAlias = true
        // 设置风格为实线
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 2f

        circlePaint.style = Paint.Style.FILL
    }

    fun init(width: Int, height: Int) {
        mWidth = width.toFloat()
        mHeight = height.toFloat()
        //线性差值器
        mValueAnimator = ValueAnimator.ofFloat(0f, mWidth)
        mValueAnimator.duration = 1700
        mValueAnimator.interpolator = LinearInterpolator()
        mValueAnimator.repeatCount = ValueAnimator.INFINITE
        mValueAnimator.addUpdateListener { animation ->
            offx = animation.animatedValue as Float
        }
        //波浪的高度
        mWaterHeight = mHeight / 2
        //起伏的高度
        mWaterUp = mWaterHeight / 2
    }

    /** 启动线程 */
    fun runDraw() {
        isRun = true
        start()
        mValueAnimator.start()
    }

    /** 恢复线程 */
    fun resumeThread() {
        synchronized(this) {
            isRun = true
            notify()
            mValueAnimator.start()
        }
    }

    /** 暂停线程 */
    fun stopDraw() {
        isRun = false
        mValueAnimator.cancel()
    }

    override fun run() {
        synchronized(this) {
            while (true) {
                if (!isRun) {
                    wait()
                }
                val canvas = holder.lockCanvas()
                if (canvas != null) {
                    //清除画布
                    canvas.drawColor(
                        Color.TRANSPARENT,
                        android.graphics.PorterDuff.Mode.CLEAR
                    )
                    paint.color = KtxProvider.mContext.getColor(R.color.bg_color_3159c7_83)
                    circlePaint.color = KtxProvider.mContext.getColor(R.color.black)
                    water(canvas)
                    //循环起伏
//            offx += 3
//            if (offx >= mWidth) {
//                offx = 0f
//            }
                    // 解除锁定，并提交修改内容
                    holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

    /** 绘制波浪 */
    private fun water(canvas: Canvas) {
        path.reset()
        //起点
        path.moveTo(-mWidth + offx, mWaterHeight)
        //波浪的数量
        for (i in 0 until 2) {
            path.quadTo(
                -mWidth * 3 / 4 + i * mWidth + offx,
                mWaterHeight - mWaterUp,
                -mWidth / 2 + i * mWidth + offx,
                mWaterHeight
            )
            //canvas.drawCircle(-mWidth * 3 / 4 + i * mWidth + offx, mWaterHeight - mWaterUp,5f,circlePaint)
            //canvas.drawCircle(-mWidth / 2 + i * mWidth + offx, mWaterHeight,5f,circlePaint)
            path.quadTo(
                -mWidth / 4 + i * mWidth + offx,
                mWaterHeight + mWaterUp,
                i * mWidth + offx,
                mWaterHeight
            )
            //canvas.drawCircle(-mWidth / 4 + i * mWidth + offx, mWaterHeight + mWaterUp,5f,circlePaint)
            //canvas.drawCircle(i * mWidth + offx, mWaterHeight,5f,circlePaint)
//            Log.e(
//                "===\n", "i = $i\n" +
//                        "x1 ${-mWidth * 3 / 4 + i * mWidth} y1 ${mWaterHeight - mWaterUp} x2 ${-mWidth / 2 + i * mWidth} y2 $mWaterHeight \n" +
//                        "x1 ${-mWidth / 4 + i * mWidth} y1 ${mWaterHeight + mWaterUp} x2 ${i * mWidth} y2 $mWaterHeight"
//            )
        }
        //闭合操作
        path.lineTo(mWidth, mHeight)
        path.lineTo(0f, mHeight)
        path.close()
        canvas.drawPath(path, paint)
    }

}