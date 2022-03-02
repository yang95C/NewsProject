package com.yg.common.router

class RouterFragmentPath {

    /**
     * 首页主页
     */
    object Home {
        private const val HOME = "/home"
        const val PAGER_HOME = "$HOME/Home"
        const val PAGER_HOME_NEWS_LIST = "$HOME/NewsList"
    }

    /**
     * User组件
     */
    object User {
        private const val USER = "/user"

        // 个人中心
        const val PAGER_USER = "$USER/User"
    }

}