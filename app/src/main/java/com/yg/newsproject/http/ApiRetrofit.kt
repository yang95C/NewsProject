package com.yg.newsproject.http

import com.yg.newsproject.baselibs.http.RetrofitFactory
import com.yg.newsproject.constant.Constant

/**
 * @author chenxz
 * @date 2018/11/21
 * @desc
 */
object ApiRetrofit : RetrofitFactory<ApiInterface>() {

    override fun baseUrl(): String = Constant.BASE_URL

    override fun getService(): Class<ApiInterface> = ApiInterface::class.java

}