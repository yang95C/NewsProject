package com.yg.home.mvp.contract

import com.yg.newsproject.baselibs.mvp.IModel
import com.yg.newsproject.baselibs.mvp.IPresenter
import com.yg.newsproject.baselibs.mvp.IView
import com.yg.lib_core.bean.HttpResult
import com.yg.lib_core.db.entity.ColumnBean
import io.reactivex.rxjava3.core.Observable

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
interface HomeContract {

    interface View : IView {
        fun getColumnListSuccess(data: MutableList<ColumnBean>?)
        fun getColumnListError()
    }

    interface Presenter : IPresenter<View> {
        fun findColumnList(mainId:String)
    }

    interface Model : IModel {
        fun findColumnList(mainId:String): Observable<HttpResult<MutableList<ColumnBean>>>
    }

}