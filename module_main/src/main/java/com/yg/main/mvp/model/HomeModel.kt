package com.yg.main.mvp.model

import com.yg.newsproject.baselibs.mvp.BaseModel
import com.yg.lib_core.bean.HttpResult
import com.yg.lib_core.bean.MainMenuBean
import com.yg.lib_core.http.ApiRetrofit
import com.yg.main.mvp.contract.HomeContract
import com.yg.newsproject.baselibs.constant.Constant
import io.reactivex.rxjava3.core.Observable

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class HomeModel : BaseModel(), HomeContract.Model {
    override fun findMainMenu(): Observable<HttpResult<MutableList<MainMenuBean>>> {
        return ApiRetrofit.service.findMainMenu(Constant.DEVICE_TYPE)
    }

}