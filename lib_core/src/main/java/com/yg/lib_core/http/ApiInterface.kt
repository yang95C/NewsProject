package com.yg.lib_core.http

import com.yg.lib_core.bean.*
import com.yg.lib_core.db.entity.ColumnBean
import com.yg.lib_core.db.entity.MainMenuBean
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
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


    /**************************************************************** 接口 ******************************************************************************************/


    /**
     * 上传图片
     */
    @Multipart
    @POST("/sprapi/file/fileUpload")
    fun updateImage(@Header("token") token:String, @Part multipartFile:MultipartBody.Part, @Query("fileType") fileType:String):Observable<HttpResult<Any>>


    /**
     * 获取图形验证码
     */
    @GET("/zxapi/login/getGraphicalCode")
    fun graphicalCode(@Query("mobile") mobile:String): Observable<ResponseBody>

    /**
     * 获取手机验证码
     */
    @GET("/zxapi/login/getCode")
    fun getPhoneCode(@Query("mobile") mobile:String, @Query("vcode") vcode :String): Observable<HttpResult<Any>>

    /**
     * 验证码登录
     */
    @POST("/zxapi/login/yzmLogin")
    fun loginCode(@Body maps : Map<String, String>): Observable<HttpResult<UserInfoBean>>
    /**
     * 密码登录
     */
    @POST("/zxapi/login/passwordLogin")
    fun loginPass(@Body maps : Map<String, String>): Observable<HttpResult<UserInfoBean>>

    /**
     * 修改密码
     */
    @POST("/zxapi/user/setPassword")
    fun updataPass(@Body maps : Map<String, String>): Observable<HttpResult<Any>>




    /***********************************************  新闻项目接口  ***********************************************************/
    /**
     * 获取启动页广告图
     */
    @GET("/a/appAdvetiseInfo/getAdveInfoByConId")
    fun getStartPageAd(@Query("positionId") positionId:String,@Query("deviceType") deviceType:String): Observable<HttpResult<MutableList<StartPageBean>>>

    /**
     * 获取主菜单
     */
    @GET("/a/columnType/findNewAllList")
    fun findMainMenu(@Query("deviceType") deviceType:String): Observable<HttpResult<MutableList<MainMenuBean>>>

    /**
     * 获取栏目集合
     */
    @GET("/a/Comuln/sortListByUser")
    fun findColumnList(@Query("deviceType") deviceType:String,@Query("type") type:String,@Query("userId") userId:String): Observable<HttpResult<MutableList<ColumnBean>>>
}