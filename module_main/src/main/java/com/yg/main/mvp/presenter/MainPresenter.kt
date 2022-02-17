package com.yg.main.mvp.presenter

import com.yg.main.mvp.contract.MainContract
import com.yg.newsproject.baselibs.mvp.BasePresenter
import com.yg.main.mvp.model.MainModel
import com.yg.newsproject.baselibs.ext.ss

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {

    override fun createModel(): MainContract.Model? = MainModel()

    override fun findMainMenu() {
        mModel?.findMainMenu()?.ss(mModel,mView,onSuccess = {
            mView?.getMainMenuSuccess(it?.data)
        })
    }

}