package com.yg.newsproject.mvp.presenter

import com.yg.newsproject.baselibs.ext.ss
import com.yg.newsproject.baselibs.mvp.BasePresenter
import com.yg.newsproject.mvp.contract.MyContract
import com.yg.newsproject.mvp.model.MyModel

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class MyPresenter : BasePresenter<MyContract.Model, MyContract.View>(), MyContract.Presenter {

    override fun createModel(): MyContract.Model? = MyModel()

    override fun getRecords(token: String, page: Int, pageSize: Int) {
        mModel?.getRecords(token,page,pageSize)?.ss(mModel,mView,onSuccess = {
            mView?.getRecordsSuccess(it.data)
        })
    }

}