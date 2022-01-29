package com.yg.main.mvp.presenter

import com.yg.newsproject.baselibs.mvp.BasePresenter
import com.yg.main.mvp.contract.CityListContract
import com.yg.main.mvp.model.CityListModel
import com.yg.newsproject.baselibs.ext.ss

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class CityListPresenter : BasePresenter<CityListContract.Model, CityListContract.View>(), CityListContract.Presenter {

    override fun createModel(): CityListContract.Model? = CityListModel()

    override fun getRecords(token: String, page: Int, pageSize: Int) {
        mModel?.getRecords(token,page,pageSize)?.ss(mModel,mView,onSuccess = {
            mView?.getRecordsSuccess(it.data)
        })
    }

}