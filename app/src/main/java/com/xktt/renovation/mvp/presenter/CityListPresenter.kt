package com.xktt.renovation.mvp.presenter

import com.xktt.renovation.baselibs.ext.ss
import com.xktt.renovation.baselibs.mvp.BasePresenter
import com.xktt.renovation.mvp.contract.CityListContract
import com.xktt.renovation.mvp.model.CityListModel

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