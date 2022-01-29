package com.yg.main.mvp.contract

import com.yg.newsproject.baselibs.mvp.IModel
import com.yg.newsproject.baselibs.mvp.IPresenter
import com.yg.newsproject.baselibs.mvp.IView
import com.yg.lib_core.bean.HttpResult
import io.reactivex.rxjava3.core.Observable

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
interface CityListContract {

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