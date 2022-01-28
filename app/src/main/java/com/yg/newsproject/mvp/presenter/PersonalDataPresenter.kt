package com.yg.newsproject.mvp.presenter

import com.yg.newsproject.baselibs.ext.ss
import com.yg.newsproject.baselibs.mvp.BasePresenter
import com.yg.newsproject.mvp.contract.PersonalDataContract
import com.yg.newsproject.mvp.model.PersonalDataModel
import okhttp3.MultipartBody

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class PersonalDataPresenter : BasePresenter<PersonalDataContract.Model, PersonalDataContract.View>(), PersonalDataContract.Presenter {

    override fun createModel(): PersonalDataContract.Model? = PersonalDataModel()

    override fun uploadImage(token: String, multipartFile: MultipartBody.Part, fileType: String) {
        mModel?.uploadImage(token,multipartFile,fileType)?.ss(mModel,mView,onSuccess = {
            mView?.uploadImageSuccess(it.data)
        })
    }

}