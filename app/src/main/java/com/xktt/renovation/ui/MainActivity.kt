package com.xktt.renovation.ui

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.xktt.renovation.R
import com.xktt.renovation.baselibs.base.BaseActivity
import com.xktt.renovation.baselibs.utils.StatusBarUtil
import com.xktt.renovation.utils.ActivityUtils

class MainActivity : BaseActivity() {

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun initView() {
//        StatusBarUtil.setTransparent(this)
//        setStatusBarColor(resources.getColor(R.color.app_color_blue))
        ActivityUtils.addFragment(supportFragmentManager, MainFragment::class.java,R.id.frameLayout)
    }

    override fun start() {

    }
}