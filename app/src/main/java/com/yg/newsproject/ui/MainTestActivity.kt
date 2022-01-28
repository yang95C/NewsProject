package com.yg.newsproject.ui

import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.yg.newsproject.baselibs.base.BaseMvpTitleActivity
import com.yg.newsproject.R
import com.yg.newsproject.baselibs.ext.setSingleClickListener
import com.yg.newsproject.bean.Banner
import com.yg.newsproject.bean.CollectionArticle
import com.yg.newsproject.bean.CollectionResponseBody
import com.yg.newsproject.mvp.contract.MainContract
import com.yg.newsproject.mvp.presenter.MainPresenter
import com.yg.newsproject.utils.DialogUtil
import com.yg.newsproject.utils.PermissionHelper
import kotlinx.android.synthetic.main.activity_main_test.*

class MainTestActivity : BaseMvpTitleActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, "正在加载")
    }

    override fun attachChildLayoutRes(): Int = R.layout.activity_main_test

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun hasBackIcon(): Boolean = true

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun initView() {
        setStatusBarColor(resources.getColor(R.color.colorPrimary))
        setStatusBarIcon(false)
        super.initView()
        setBaseTitleColor(android.R.color.white)
        setBaseTitle("Main")
    }

    override fun initData() {
        btn_login.setSingleClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()
            if (TextUtils.isEmpty(username)) {
                showDefaultMsg("账号不能为空")
                return@setSingleClickListener
            }
            if (TextUtils.isEmpty(password)) {
                showDefaultMsg("密码不能为空")
                return@setSingleClickListener
            }
            mPresenter?.login(username, password)
        }
        btn_logout.setSingleClickListener {
            mPresenter?.logout()
        }
        btn_get_banner.setSingleClickListener {
            tv_result.text = ""
            imageView.visibility = View.VISIBLE
            mPresenter?.getBannerList()
        }
        btn_collect.setSingleClickListener {
            tv_result.text = ""
            imageView.visibility = View.GONE
            mPresenter?.getCollectList(0)
        }
        btn_permission.setSingleClickListener {
            PermissionHelper.requestCameraPermission(this) {
                showDefaultMsg("相机权限申请成功")
            }
//            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
//                    .subscribe {
//                        if (it) {
//                            showDefaultMsg("已允许")
//                        } else {
//                            showDefaultMsg("未允许")
//                        }
//                    }
        }

        btn_sub.setSingleClickListener {
            mPresenter?.getSubscribeList("eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAxMDYzMiIsInN1YiI6IjEwMDEwNjMyIiwicHdkIjoiMDRkN2EwNzJjMWUzNTczMTFjMTA5OGNiNTg0N2ViNmUiLCJleHAiOjE2MzA2NTM5OTgsImlhdCI6MTYyOTE4Mjc2OSwianRpIjoiOWQ3Mjk3YjItOTNmOC00MGI3LTgxN2YtZmE0NzM2ZTJjYTZlIn0.zcLOcFh-_Y37pjkTibCc87kzqpLp8gUAI64_mxyt1AY",1,10)
        }
    }

    override fun start() {
    }

    override fun showBanners(banners: MutableList<Banner>) {
        tv_result.text = banners.toString()
    }

    override fun loginSuccess() {
        showDefaultMsg("登录成功")
    }

    override fun showBannerList(bannerList: MutableList<Banner>) {
        if (bannerList.size > 0) {
            tv_result.text = bannerList[0].title
            Glide.with(this).load(bannerList[0].imagePath).into(imageView)
        }
    }

    override fun showCollectList(collectionResponseBody: CollectionResponseBody<CollectionArticle>) {
        if (collectionResponseBody.datas.isNotEmpty()) {
            tv_result.text = collectionResponseBody.datas[0].title
        }
    }

    override fun logoutSuccess() {
        showDefaultMsg("已退出登录")
    }

}
