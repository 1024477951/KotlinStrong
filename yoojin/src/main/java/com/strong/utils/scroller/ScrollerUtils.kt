package com.strong.utils.scroller

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * created by YooJin.
 * date: 2021/2/4 17:36
 * desc:列表滑动监听
 */
class ScrollerUtils {

    companion object {

        fun scroller(
            recyclerView: RecyclerView,
            toolbar: View,
            scrollerCallBack: ScrollerCallBack
        ) {

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                private var totalDy: Int = 0

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    totalDy += dy
                    if (totalDy < 0) {
                        totalDy = 0
                    }
                    val childAt = recyclerView.getChildAt(0)
                    val rect = Rect()
                    childAt.getLocalVisibleRect(rect)
                    val height = childAt.height
                    val alpha = abs(1f * totalDy / height)
                    if (totalDy < height / 3){
                        scrollerCallBack.alpha(alpha)
                    }else{
                        scrollerCallBack.alpha(1f)
                    }
                    //Log.e("===", "height ${height / 3} totalDy $totalDy alpha $alpha")
                }

            })
        }

    }

}