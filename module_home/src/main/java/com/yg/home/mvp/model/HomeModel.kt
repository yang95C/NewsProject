package com.yg.home.mvp.model

import com.yg.newsproject.baselibs.mvp.BaseModel
import com.yg.lib_core.bean.HttpResult
import com.yg.lib_core.http.ApiRetrofit
import com.yg.home.mvp.contract.HomeContract
import com.yg.lib_core.db.entity.ColumnBean
import com.yg.newsproject.baselibs.config.UserManager
import com.yg.newsproject.baselibs.constant.Constant
import io.reactivex.rxjava3.core.Observable

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class HomeModel : BaseModel(), HomeContract.Model {
    override fun findColumnList(mainId:String): Observable<HttpResult<MutableList<ColumnBean>>> {
        return ApiRetrofit.service.findColumnList(Constant.DEVICE_TYPE,mainId,UserManager.get().getUserId())
    }

}