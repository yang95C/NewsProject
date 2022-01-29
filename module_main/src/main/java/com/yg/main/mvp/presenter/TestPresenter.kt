package com.yg.main.mvp.presenter

import com.yg.newsproject.baselibs.mvp.BasePresenter
import com.yg.main.mvp.contract.TestContract
import com.yg.main.mvp.model.TestModel
import com.yg.newsproject.baselibs.ext.ss

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class TestPresenter : BasePresenter<TestContract.Model, TestContract.View>(), TestContract.Presenter {

    override fun createModel(): TestContract.Model? = TestModel()

    override fun getRecords(token: String, page: Int, pageSize: Int) {
        mModel?.getRecords(token,page,pageSize)?.ss(mModel,mView,onSuccess = {
            mView?.getRecordsSuccess(it.data)
        })
    }

}