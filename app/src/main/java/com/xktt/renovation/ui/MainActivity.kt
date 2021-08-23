package com.xktt.renovation.ui

import com.xktt.renovation.R
import com.xktt.renovation.baselibs.base.BaseActivity
import com.xktt.renovation.utils.ActivityUtils

class MainActivity : BaseActivity() {

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun initView() {
        ActivityUtils.addFragment(supportFragmentManager, MainFragment::class.java,R.id.frameLayout)
    }

    override fun start() {

    }
}