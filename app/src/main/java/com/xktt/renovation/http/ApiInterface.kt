package com.xktt.renovation.http

import com.xktt.renovation.bean.Banner
import com.xktt.renovation.bean.CollectionArticle
import com.xktt.renovation.bean.CollectionResponseBody
import com.xktt.renovation.bean.HttpResult
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiInterface {

    @GET("banner/json")
    fun getHomeBanner(): Observable<HttpResult<MutableList<Banner>>>

    /**
     * 账户明细
     */
    @GET("/v2/records.php")
    fun getRecords(@Header("token") token :String, @Query("page") page: Int, @Query("pagesize") pagesize : Int, @Query("cfrom") cfrom : String
                   , @Query("channel") channel : String, @Query("pkg") pkg:String, @Query("version") version:String)
            : Observable<HttpResult<Any>>

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

    /**
     * 上传图片
     */
    @Multipart
    @POST("/sprapi/file/fileUpload")
    fun updateImage(@Header("token") token:String, @Part multipartFile:MultipartBody.Part, @Query("fileType") fileType:String):Observable<HttpResult<Any>>

}