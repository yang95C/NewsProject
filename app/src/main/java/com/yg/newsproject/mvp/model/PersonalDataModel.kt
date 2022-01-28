package com.yg.newsproject.mvp.model

import com.yg.newsproject.baselibs.mvp.BaseModel
import com.yg.newsproject.bean.HttpResult
import com.yg.newsproject.http.ApiRetrofit
import com.yg.newsproject.mvp.contract.PersonalDataContract
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