package com.xktt.renovation.mvp.presenter

import com.xktt.renovation.baselibs.mvp.BasePresenter
import com.xktt.renovation.mvp.contract.TestContract
import com.xktt.renovation.mvp.model.TestModel

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class TestPresenter : BasePresenter<TestContract.Model, TestContract.View>(), TestContract.Presenter {

    override fun createModel(): TestContract.Model? = TestModel()

}