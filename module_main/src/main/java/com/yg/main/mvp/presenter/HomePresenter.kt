package com.yg.main.mvp.presenter

import com.yg.newsproject.baselibs.mvp.BasePresenter
import com.yg.main.mvp.contract.HomeContract
import com.yg.main.mvp.model.HomeModel
import com.yg.newsproject.baselibs.ext.ss

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class HomePresenter : BasePresenter<HomeContract.Model, HomeContract.View>(), HomeContract.Presenter {

    override fun createModel(): HomeContract.Model? = HomeModel()

    override fun findMainMenu() {
        mModel?.findMainMenu()?.ss(mModel,mView,onSuccess = {
            mView?.getMainMenuSuccess(it?.data)
        })
    }

}