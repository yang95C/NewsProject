package com.yg.user.ui.mvp.presenter

import com.yg.newsproject.baselibs.ext.ss
import com.yg.newsproject.baselibs.mvp.BasePresenter
import com.yg.user.ui.mvp.contract.MyContract
import com.yg.user.ui.mvp.model.MyModel

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class MyPresenter : BasePresenter<com.yg.user.ui.mvp.contract.MyContract.Model, com.yg.user.ui.mvp.contract.MyContract.View>(), com.yg.user.ui.mvp.contract.MyContract.Presenter {

    override fun createModel(): com.yg.user.ui.mvp.contract.MyContract.Model? =
        com.yg.user.ui.mvp.model.MyModel()

    override fun getRecords(token: String, page: Int, pageSize: Int) {
        mModel?.getRecords(token,page,pageSize)?.ss(mModel,mView,onSuccess = {
            mView?.getRecordsSuccess(it.data)
        })
    }

}