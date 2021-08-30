package com.xktt.renovation.ui.my.activity

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.xktt.renovation.R
import com.xktt.renovation.baselibs.base.BaseActivity
import com.xktt.renovation.baselibs.config.UserManager
import com.xktt.renovation.baselibs.ext.ss
import com.xktt.renovation.baselibs.utils.ToastUtils
import com.xktt.renovation.http.ApiRetrofit
import kotlinx.android.synthetic.main.activity_setting_pass.*
import kotlinx.android.synthetic.main.item_head_layout.*
import java.util.regex.Pattern

class SettingPassActivity : BaseActivity() {
    private var isPass = false
    private var isConfirm = false
    override fun attachLayoutRes(): Int {
        return R.layout.activity_setting_pass
    }

    override fun initView() {
        tv_head_title.text = "修改密码"
        img_head_back.setOnClickListener { finish() }
        et_pass.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())){
                    img_del.visibility = View.GONE
                    isPass = false
                } else {
                    img_del.visibility = View.VISIBLE
                    isPass = true
                }
                if (isPass && isConfirm){
                    img_ok.setImageResource(R.mipmap.icon_pass_ok)
                } else{
                    img_ok.setImageResource(R.mipmap.icon_pass_ok1)
                }
            }
        })
        img_del.setOnClickListener {
            isPass = false
            et_pass.text.clear()
        }

        et_confirm.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())){
                    img_del1.visibility = View.GONE
                    isConfirm = false
                } else {
                    img_del1.visibility = View.VISIBLE
                    isConfirm = true
                }
                if (isPass && isConfirm){
                    img_ok.setImageResource(R.mipmap.icon_pass_ok)
                } else{
                    img_ok.setImageResource(R.mipmap.icon_pass_ok1)
                }
            }
        })
        img_del1.setOnClickListener {
            isConfirm = false
            et_confirm.text.clear()
        }

        img_ok.setOnClickListener {
            val pass = et_pass.text.toString()
            val confirm = et_confirm.text.toString()
            if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirm)){
                ToastUtils.showToast("请输入新密码")
                return@setOnClickListener
            }
            if (pass == confirm){
                if (isPassword(pass) && pass.length > 7){
                    updataPass()
                } else {
                    ToastUtils.showToast("密码必须包含字母、数字、特色字符中任意两种，8位以上")
                }
            } else {
                ToastUtils.showToast("确认密码有误请重新输入")
            }
        }
    }

    private fun updataPass() {
        var map = HashMap<String,String>()
        map.put("password",et_pass.text.toString())
        map.put("confirmPassword",et_confirm.text.toString())
        ApiRetrofit.service.updataPass(map)?.ss(onSuccess = {
            if (it.retcode == "0000"){
                ToastUtils.showToast("密码修改成功")
                finish()
            } else {
                ToastUtils.showToast(it.retmsg)
            }
        })
    }

    override fun start() {

    }

    fun isPassword(password: String?): Boolean {
        //密码为6~20位数字,英文,符号至少两种组合的字符
        val regex = "^(?![0-9]+$)(?![a-zA-Z]+$)(?!([^(0-9a-zA-Z)]|[\\(\\)])+$)([^(0-9a-zA-Z)]|[\\(\\)]|[a-zA-Z]|[0-9]){6,20}$"
        val p = Pattern.compile(regex)
        val m = p.matcher(password)
        val isMatch = m.matches()
        Log.d("TAG", "isPassword: 是否密码正则匹配$isMatch")
        return isMatch
    }

}