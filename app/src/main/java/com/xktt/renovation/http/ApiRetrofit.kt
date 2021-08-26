package com.xktt.renovation.http

import com.xktt.renovation.baselibs.http.RetrofitFactory
import com.xktt.renovation.constant.Constant

/**
 * @author chenxz
 * @date 2018/11/21
 * @desc
 */
object ApiRetrofit : RetrofitFactory<ApiInterface>() {

    override fun baseUrl(): String = Constant.BASE_URL

    override fun getService(): Class<ApiInterface> = ApiInterface::class.java

}