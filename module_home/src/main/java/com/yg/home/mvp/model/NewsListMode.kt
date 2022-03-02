package com.yg.home.mvp.model


import com.yg.home.mvp.contract.NewsListContract
import com.yg.lib_core.bean.HttpResult
import com.yg.lib_core.bean.NewsListPageBean
import com.yg.lib_core.http.ApiRetrofit
import com.yg.newsproject.baselibs.config.UserManager
import com.yg.newsproject.baselibs.constant.Constant
import com.yg.newsproject.baselibs.mvp.BaseModel
import io.reactivex.rxjava3.core.Observable

class NewsListMode : BaseModel(),NewsListContract.Mode {
    override fun findNewsList(
        page: Int,
        columnId: String,
        tagId: String
    ): Observable<HttpResult<NewsListPageBean>> {
        return ApiRetrofit.service.findNewsList(columnId,tagId,page,UserManager.get().getUserId(),Constant.DEVICE_TYPE)
    }
}