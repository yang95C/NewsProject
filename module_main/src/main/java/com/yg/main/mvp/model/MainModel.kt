package com.yg.main.mvp.model

import com.yg.newsproject.baselibs.mvp.BaseModel
import com.yg.lib_core.bean.Banner
import com.yg.lib_core.bean.CollectionArticle
import com.yg.lib_core.bean.CollectionResponseBody
import com.yg.lib_core.bean.HttpResult
import com.yg.lib_core.http.ApiRetrofit
import com.yg.main.mvp.contract.MainContract
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