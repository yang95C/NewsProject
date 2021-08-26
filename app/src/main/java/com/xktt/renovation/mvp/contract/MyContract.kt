package com.xktt.renovation.mvp.contract

import com.xktt.renovation.baselibs.mvp.IModel
import com.xktt.renovation.baselibs.mvp.IPresenter
import com.xktt.renovation.baselibs.mvp.IView
import com.xktt.renovation.bean.HttpResult
import io.reactivex.rxjava3.core.Observable

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
interface MyContract {

    interface View : IView {
        fun getRecordsSuccess(any: Any)
    }

    interface Presenter : IPresenter<View> {
        fun getRecords(token: String, page: Int, pageSize: Int)
    }

    interface Model : IModel {
        fun getRecords(token:String,page:Int,pageSize:Int): Observable<HttpResult<Any>>
    }

}