package com.yg.newsproject.mvp.presenter

import com.yg.newsproject.baselibs.ext.ss
import com.yg.newsproject.baselibs.mvp.BasePresenter
import com.yg.newsproject.mvp.contract.HomeContract
import com.yg.newsproject.mvp.model.HomeModel

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class HomePresenter : BasePresenter<HomeContract.Model, HomeContract.View>(), HomeContract.Presenter {

    override fun createModel(): HomeContract.Model? = HomeModel()

    override fun getRecords(token: String, page: Int, pageSize: Int) {
        mModel?.getRecords(token,page,pageSize)?.ss(mModel,mView,onSuccess = {
            mView?.getRecordsSuccess(it.data)
        })
    }

}