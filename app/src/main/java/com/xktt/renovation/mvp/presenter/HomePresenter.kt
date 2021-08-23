package com.xktt.renovation.mvp.presenter

import com.xktt.renovation.baselibs.ext.ss
import com.xktt.renovation.baselibs.mvp.BasePresenter
import com.xktt.renovation.mvp.contract.HomeContract
import com.xktt.renovation.mvp.contract.TestContract
import com.xktt.renovation.mvp.model.HomeModel
import com.xktt.renovation.mvp.model.TestModel

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