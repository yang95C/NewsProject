package com.xktt.renovation.ui.main.activity

import com.xktt.renovation.R
import com.xktt.renovation.baselibs.base.BaseActivity
import com.xktt.renovation.ui.main.fragment.MianFragment
import com.xktt.renovation.utils.ActivityUtils

class MainActivity : BaseActivity() {

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun initView() {
        ActivityUtils.addFragment(supportFragmentManager, MianFragment::class.java,R.id.frameLayout)
    }

    override fun start() {

    }
}