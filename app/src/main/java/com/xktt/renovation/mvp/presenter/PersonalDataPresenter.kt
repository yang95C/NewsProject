package com.xktt.renovation.mvp.presenter

import com.xktt.renovation.baselibs.ext.ss
import com.xktt.renovation.baselibs.mvp.BasePresenter
import com.xktt.renovation.mvp.contract.PersonalDataContract
import com.xktt.renovation.mvp.model.PersonalDataModel
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