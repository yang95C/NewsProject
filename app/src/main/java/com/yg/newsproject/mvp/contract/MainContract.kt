package com.yg.newsproject.mvp.contract

import com.yg.newsproject.baselibs.mvp.IModel
import com.yg.newsproject.baselibs.mvp.IPresenter
import com.yg.newsproject.baselibs.mvp.IView
import com.yg.newsproject.bean.Banner
import com.yg.newsproject.bean.CollectionArticle
import com.yg.newsproject.bean.CollectionResponseBody
import com.yg.newsproject.bean.HttpResult
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