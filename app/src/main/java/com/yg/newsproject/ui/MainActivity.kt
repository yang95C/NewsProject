package com.yg.newsproject.ui

import com.yg.newsproject.R
import com.yg.newsproject.baselibs.base.BaseActivity
import com.yg.newsproject.utils.ActivityUtils

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