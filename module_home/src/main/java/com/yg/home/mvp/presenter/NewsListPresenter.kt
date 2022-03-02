package com.yg.home.mvp.presenter

import com.yg.home.mvp.contract.NewsListContract
import com.yg.home.mvp.model.NewsListMode
import com.yg.newsproject.baselibs.ext.ss
import com.yg.newsproject.baselibs.mvp.BasePresenter

class NewsListPresenter : BasePresenter<NewsListContract.Mode,NewsListContract.View>(), NewsListContract.Presenter {
    override fun createModel(): NewsListContract.Mode? {
        return NewsListMode()
    }

    override fun findNewsList(page: Int, columnId: String, tagId: String) {
        mModel?.findNewsList(page, columnId, tagId)?.ss(onSuccess = {
             mView?.findNewsListSuccess(it?.data)
        },onError = {
            mView?.findNewsListError()
        })
    }
}