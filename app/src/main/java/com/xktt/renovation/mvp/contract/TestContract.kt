package com.xktt.renovation.mvp.contract

import com.xktt.renovation.baselibs.mvp.IModel
import com.xktt.renovation.baselibs.mvp.IPresenter
import com.xktt.renovation.baselibs.mvp.IView

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
interface TestContract {

    interface View : IView {

    }

    interface Presenter : IPresenter<View> {

    }

    interface Model : IModel {

    }

}