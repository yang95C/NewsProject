package com.xktt.renovation.mvp.contract

import com.xktt.renovation.baselibs.mvp.IModel
import com.xktt.renovation.baselibs.mvp.IPresenter
import com.xktt.renovation.baselibs.mvp.IView
import com.xktt.renovation.bean.Banner
import com.xktt.renovation.bean.CollectionArticle
import com.xktt.renovation.bean.CollectionResponseBody
import com.xktt.renovation.bean.HttpResult
import io.reactivex.rxjava3.core.Observable

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
interface MainContract {

    interface View : IView {
        fun showBanners(banners: MutableList<Banner>)

        fun loginSuccess()

        fun showBannerList(bannerList: MutableList<Banner>)

        fun showCollectList(collectionResponseBody: CollectionResponseBody<CollectionArticle>)

        fun logoutSuccess()
    }

    interface Presenter : IPresenter<View> {
        fun getBanner()
        fun getBanner2()
        fun getBanner3()

        fun login(username: String, password: String)
        fun getBannerList()
        fun getCollectList(page: Int)
        fun logout()

        fun getSubscribeList(token:String,page:Int,pageSize:Int)
    }

    interface Model : IModel {
        fun getBanners(): Observable<HttpResult<MutableList<Banner>>>

        fun login(username: String, password: String): Observable<HttpResult<Any>>
        fun getBannerList(): Observable<HttpResult<MutableList<Banner>>>
        fun getCollectList(page: Int): Observable<HttpResult<CollectionResponseBody<CollectionArticle>>>
        fun logout(): Observable<HttpResult<Any>>
        fun getSubscribeList(token:String,page:Int,pageSize:Int): Observable<HttpResult<Any>>

    }

}