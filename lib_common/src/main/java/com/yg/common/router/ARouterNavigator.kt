package com.yg.common.router

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter

class ARouterNavigator {

    fun navigateHomeActivity(context: Context?){
        ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN).navigation(context)
    }

}