package com.xktt.renovation.mvp.presenter

import com.xktt.renovation.baselibs.ext.ss
import com.xktt.renovation.baselibs.mvp.BasePresenter
import com.xktt.renovation.mvp.contract.MyContract
import com.xktt.renovation.mvp.contract.TestContract
import com.xktt.renovation.mvp.model.MyModel
import com.xktt.renovation.mvp.model.TestModel

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