package com.xktt.renovation.baselibs.http.interceptor

import com.xktt.renovation.baselibs.http.constant.HttpConstant
import com.xktt.renovation.baselibs.utils.Preference
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author chenxz
 * @date 2018/9/26
 * @desc HeaderInterceptor: 设置请求头
 */
class HeaderInterceptor : Interceptor {

    /**
     * token
     */
    private var token: String by Preference(HttpConstant.TOKEN_KEY, "")

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("Content-type", "application/json; charset=utf-8")
         .addHeader("token", "eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAxMDYzMiIsInN1YiI6IjEwMDEwNjMyIiwicHdkIjoiMDRkN2EwNzJjMWUzNTczMTFjMTA5OGNiNTg0N2ViNmUiLCJleHAiOjE2MzA4MjUzOTYsImlhdCI6MTYyOTM1NDE2NywianRpIjoiZTI2NWM1MzQtYWY1YS00ZjhhLWFmMmYtZmE3MzhlNjg4ZDdiIn0.bv4swd32qjMpoPB_FJgNfTzAf8T8tX_FmTi_2Yk7jJE")
         .addHeader("cfrom", "android")
         .addHeader("channel", "qiuwugui_az")
         .addHeader("pkg", "com.cd.yqty")
         .addHeader("version", "2021081801")
//         .method(request.method(), request.body())

        val domain = request.url.host
        val url = request.url.toString()
        if (domain.isNotEmpty()) {
            val spDomain: String by Preference(domain, "")
            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
            if (cookie.isNotEmpty()) {
                // 将 Cookie 添加到请求头
                builder.addHeader(HttpConstant.COOKIE_NAME, cookie)
            }
        }

        return chain.proceed(builder.build())
    }

}