package com.xktt.renovation.http

import com.xktt.renovation.baselibs.http.RetrofitFactory
import com.xktt.renovation.constant.Constant

/**
 * @author chenxz
 * @date 2018/11/21
 * @desc
 */
object MainRetrofit : RetrofitFactory<MainApi>() {

    override fun baseUrl(): String = Constant.BASE_URL

    override fun getService(): Class<MainApi> = MainApi::class.java

}