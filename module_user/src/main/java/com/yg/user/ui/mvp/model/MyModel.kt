package com.yg.user.ui.mvp.model

import com.yg.newsproject.baselibs.mvp.BaseModel
import com.yg.lib_core.bean.HttpResult
import com.yg.lib_core.http.ApiRetrofit
import com.yg.user.ui.mvp.contract.MyContract
import io.reactivex.rxjava3.core.Observable

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class MyModel : BaseModel(), com.yg.user.ui.mvp.contract.MyContract.Model {
    override fun getRecords(token: String, page: Int, pageSize: Int): Observable<HttpResult<Any>> {
       return ApiRetrofit.service.getRecords(token,page,pageSize,"android","fotmkt_az","com.cd.yqty","2021081801")
    }

}