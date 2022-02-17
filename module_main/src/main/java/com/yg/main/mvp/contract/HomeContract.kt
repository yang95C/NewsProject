package com.yg.main.mvp.contract

import com.yg.newsproject.baselibs.mvp.IModel
import com.yg.newsproject.baselibs.mvp.IPresenter
import com.yg.newsproject.baselibs.mvp.IView
import com.yg.lib_core.bean.HttpResult
import com.yg.lib_core.bean.MainMenuBean
import io.reactivex.rxjava3.core.Observable

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
interface HomeContract {

    interface View : IView {
        fun getMainMenuSuccess(data: MutableList<MainMenuBean>?)
    }

    interface Presenter : IPresenter<View> {
        fun findMainMenu()
    }

    interface Model : IModel {
        fun findMainMenu(): Observable<HttpResult<MutableList<MainMenuBean>>>
    }

}