package com.xktt.renovation.mvp.model

import com.xktt.renovation.baselibs.mvp.BaseModel
import com.xktt.renovation.bean.Banner
import com.xktt.renovation.bean.CollectionArticle
import com.xktt.renovation.bean.CollectionResponseBody
import com.xktt.renovation.bean.HttpResult
import com.xktt.renovation.http.ApiRetrofit
import com.xktt.renovation.mvp.contract.MainContract
import io.reactivex.rxjava3.core.Observable

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
class MainModel : BaseModel(), MainContract.Model {

    override fun getBanners(): Observable<HttpResult<MutableList<Banner>>> {
        return ApiRetrofit.service.getHomeBanner()
    }

    override fun login(username: String, password: String): Observable<HttpResult<Any>> {
        return ApiRetrofit.service.login(username, password)
    }

    override fun getBannerList(): Observable<HttpResult<MutableList<Banner>>> {
        return ApiRetrofit.service.getBannerList()
    }

    override fun getCollectList(page: Int): Observable<HttpResult<CollectionResponseBody<CollectionArticle>>> {
        return ApiRetrofit.service.getCollectList(page)
    }

    override fun logout(): Observable<HttpResult<Any>> {
        return ApiRetrofit.service.logout()
    }

    override fun getSubscribeList(
        token: String,
        page: Int,
        pageSize: Int
    ): Observable<HttpResult<Any>> {
        return ApiRetrofit.service.getSubscribeList(token, page, pageSize)
    }

}