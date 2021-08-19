package com.xktt.renovation.mvp.model

import com.xktt.renovation.baselibs.mvp.BaseModel
import com.xktt.renovation.bean.Banner
import com.xktt.renovation.bean.CollectionArticle
import com.xktt.renovation.bean.CollectionResponseBody
import com.xktt.renovation.bean.HttpResult
import com.xktt.renovation.http.MainRetrofit
import com.xktt.renovation.mvp.contract.MainContract
import io.reactivex.rxjava3.core.Observable

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
class MainModel : BaseModel(), MainContract.Model {

    override fun getBanners(): Observable<HttpResult<MutableList<Banner>>> {
        return MainRetrofit.service.getHomeBanner()
    }

    override fun login(username: String, password: String): Observable<HttpResult<Any>> {
        return MainRetrofit.service.login(username, password)
    }

    override fun getBannerList(): Observable<HttpResult<MutableList<Banner>>> {
        return MainRetrofit.service.getBannerList()
    }

    override fun getCollectList(page: Int): Observable<HttpResult<CollectionResponseBody<CollectionArticle>>> {
        return MainRetrofit.service.getCollectList(page)
    }

    override fun logout(): Observable<HttpResult<Any>> {
        return MainRetrofit.service.logout()
    }

    override fun getSubscribeList(
        token: String,
        page: Int,
        pageSize: Int
    ): Observable<HttpResult<Any>> {
        return MainRetrofit.service.getSubscribeList(token, page, pageSize)
    }

}