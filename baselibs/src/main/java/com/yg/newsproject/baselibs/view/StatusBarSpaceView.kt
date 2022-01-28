package com.yg.newsproject.baselibs.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View

class StatusBarSpaceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mStatusBarHeight = 0
    init {
        if (visibility == VISIBLE) {
            visibility = INVISIBLE
        }

        if (!isInEditMode) {
            mStatusBarHeight = getStatusBarHeight(context)
        } else {
            mStatusBarHeight = 0
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

//        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        val newHeightMeasureSpec =
            MeasureSpec.makeMeasureSpec(mStatusBarHeight, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

    private fun getStatusBarHeight(context: Context) : Int{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                return context.resources.getDimensionPixelSize(resourceId)
            }
        }
        return 0
    }
}