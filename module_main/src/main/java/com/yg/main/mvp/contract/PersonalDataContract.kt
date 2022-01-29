package com.yg.main.mvp.contract

import com.yg.newsproject.baselibs.mvp.IModel
import com.yg.newsproject.baselibs.mvp.IPresenter
import com.yg.newsproject.baselibs.mvp.IView
import com.yg.lib_core.bean.HttpResult
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
interface PersonalDataContract {

    interface View : IView {
        fun uploadImageSuccess(any: Any)
    }

    interface Presenter : IPresenter<View> {
        fun uploadImage(token: String, multipartFile: MultipartBody.Part, fileType: String)
    }

    interface Model : IModel {
        fun uploadImage(token:String,multipartFile: MultipartBody.Part,fileType: String): Observable<HttpResult<Any>>
    }

}