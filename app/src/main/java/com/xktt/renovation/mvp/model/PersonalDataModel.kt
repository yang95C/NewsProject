package com.xktt.renovation.mvp.model

import com.xktt.renovation.baselibs.mvp.BaseModel
import com.xktt.renovation.bean.HttpResult
import com.xktt.renovation.http.ApiRetrofit
import com.xktt.renovation.mvp.contract.PersonalDataContract
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class PersonalDataModel : BaseModel(), PersonalDataContract.Model {
    override fun uploadImage(token: String, multipartFile: MultipartBody.Part, fileType: String): Observable<HttpResult<Any>> {
       return ApiRetrofit.service.updateImage(token,multipartFile,fileType)
    }

}