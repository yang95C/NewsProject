package com.xktt.renovation.http

import com.xktt.renovation.bean.Banner
import com.xktt.renovation.bean.CollectionArticle
import com.xktt.renovation.bean.CollectionResponseBody
import com.xktt.renovation.bean.HttpResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface MainApi {

    @GET("banner/json")
    fun getHomeBanner(): Observable<HttpResult<MutableList<Banner>>>

    /**
     * 订阅
     */
    @GET("/sprapi/userfocus/getActiveList")
    fun getSubscribeList(@Header("token")  token :String, @Query("page") page: Int, @Query("limit") limit : Int)
            : Observable<HttpResult<Any>>

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String)
            : Observable<HttpResult<Any>>

    /**
     * 退出登录
     */
    @GET("user/logout/json")
    fun logout(): Observable<HttpResult<Any>>

    /**
     * 轮播列表数据
     */
    @GET("banner/json")
    fun getBannerList(): Observable<HttpResult<MutableList<Banner>>>

    /**
     * 收藏列表数据
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int)
            : Observable<HttpResult<CollectionResponseBody<CollectionArticle>>>

}