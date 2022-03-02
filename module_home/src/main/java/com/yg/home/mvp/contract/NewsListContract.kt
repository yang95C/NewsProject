package com.yg.home.mvp.contract


import com.yg.lib_core.bean.HttpResult
import com.yg.lib_core.bean.NewsListPageBean
import com.yg.newsproject.baselibs.mvp.IModel
import com.yg.newsproject.baselibs.mvp.IPresenter
import com.yg.newsproject.baselibs.mvp.IView
import io.reactivex.rxjava3.core.Observable

interface NewsListContract {
    interface View : IView {
        fun findNewsListSuccess(data: NewsListPageBean?)
        fun findNewsListError()
    }

    interface Presenter : IPresenter<View> {
        fun findNewsList(page: Int,columnId: String,tagId: String)
    }

    interface Mode : IModel {
        fun findNewsList(page: Int,columnId: String,tagId: String): Observable<HttpResult<NewsListPageBean>>
    }
}