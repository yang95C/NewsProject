package com.xktt.renovation.ui.main.fragment

import android.view.View
import com.xktt.renovation.baselibs.base.BaseMvpFragment
import com.xktt.renovation.R
import com.xktt.renovation.mvp.contract.TestContract
import com.xktt.renovation.mvp.presenter.TestPresenter

/**
 * @author chenxz
 * @date 2018/11/30
 * @desc
 */
class TestFragment : BaseMvpFragment<TestContract.View, TestContract.Presenter>(), TestContract.View {

    override fun createPresenter(): TestContract.Presenter = TestPresenter()

    override fun attachLayoutRes(): Int = R.layout.fragment_test

    override fun lazyLoad() {
    }

    override fun initView(view: View) {
        super.initView(view)
    }
}