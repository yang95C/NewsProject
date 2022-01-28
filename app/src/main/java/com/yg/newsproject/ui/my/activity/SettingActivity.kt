package com.yg.newsproject.ui.my.activity

import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.yg.newsproject.R
import com.yg.newsproject.baselibs.base.BaseActivity
import com.yg.newsproject.baselibs.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.item_head_layout.*

class SettingActivity : BaseActivity(), View.OnClickListener {
    override fun attachLayoutRes(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        tv_head_title.setText("设置")
        img_head_back.setOnClickListener { finish() }
        tv_data.setOnClickListener(this)
        tv_info.setOnClickListener(this)
        tv_safe.setOnClickListener(this)
        tv_about.setOnClickListener(this)
        tv_about.setOnClickListener(this)
    }

    override fun start() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_data -> {
                val intent = Intent(this,PersonalDataActivity().javaClass)
                ActivityUtils.startActivity(intent)
            }
            R.id.tv_info -> {
                ToastUtils.showToast("装修信息")
            }
            R.id.tv_safe -> {
                ToastUtils.showToast("账户与安全")
            }
            R.id.tv_about -> {
                ToastUtils.showToast("关于我们")
            }
        }
    }

}