package com.yg.main.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.yg.common.router.RouterActivityPath
import com.yg.main.R
import com.yg.newsproject.baselibs.base.BaseActivity
import com.yg.newsproject.baselibs.utils.ActivityUtils

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
class MainActivity : BaseActivity() {

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun initView() {

        ActivityUtils.addFragment(supportFragmentManager, MainFragment::class.java,R.id.frameLayout)
    }

    override fun start() {

    }
}