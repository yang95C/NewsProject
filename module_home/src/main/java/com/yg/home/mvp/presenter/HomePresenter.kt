package com.yg.home.mvp.presenter

import com.yg.home.mvp.contract.HomeContract
import com.yg.home.mvp.model.HomeModel
import com.yg.newsproject.baselibs.ext.ss
import com.yg.newsproject.baselibs.mvp.BasePresenter

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class HomePresenter : BasePresenter<HomeContract.Model, HomeContract.View>(), HomeContract.Presenter {

    override fun createModel(): HomeContract.Model? = HomeModel()

    override fun findColumnList(mainId:String) {
        mModel?.findColumnList(mainId)?.ss(mModel,mView,onSuccess = {
            mView?.getColumnListSuccess(it?.data)
        },onError = {
            mView?.getColumnListError()
        })
    }

}