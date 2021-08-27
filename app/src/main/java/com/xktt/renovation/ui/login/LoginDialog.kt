package com.xktt.renovation.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.xktt.renovation.R
import com.xktt.renovation.baselibs.config.UserManager
import com.xktt.renovation.baselibs.ext.ss
import com.xktt.renovation.baselibs.utils.ToastUtils
import com.xktt.renovation.http.ApiRetrofit
import com.xktt.renovation.widgets.ImgCodeDialog
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

class LoginDialog : DialogFragment() , ImgCodeDialog.OnRandomVerificationCodeInputListener {
    lateinit var img_finish :ImageView
    lateinit var ed_phone :EditText
    lateinit var iv_clear :ImageView
    lateinit var ed_code :EditText
    lateinit var tv_get_otp :TextView
    lateinit var btnLogin:Button
    lateinit var cv:CheckBox

    fun newInstance(): LoginDialog? {
        return LoginDialog()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null && dialog.window != null) {
            dialog.setCancelable(false)
            val window = dialog.window
            val wlp = window!!.attributes
            wlp.gravity = Gravity.BOTTOM
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT
            wlp.height = WindowManager.LayoutParams.MATCH_PARENT
            window!!.attributes = wlp
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomDialog1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_login_dialog,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        img_finish = view.find(R.id.img_finish)
        ed_phone = view.find(R.id.ed_phone)
        iv_clear = view.find(R.id.iv_clear)
        ed_code = view.find(R.id.ed_code)
        tv_get_otp = view.find(R.id.tv_get_otp)
        btnLogin = view.find(R.id.btn_login_register)
        cv = view.find(R.id.cv_userAgreement)

        img_finish.setOnClickListener {
            dismiss()
        }

        ed_phone.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length > 0){
                    iv_clear.visibility = View.VISIBLE
                } else{
                    iv_clear.visibility = View.GONE
                }
                if (s.toString().length == 11) {
                    tv_get_otp.textColor = resources.getColor(R.color.app_blue)
                    tv_get_otp.isClickable = true
                } else {
                    tv_get_otp.textColor = resources.getColor(R.color.text_grey)
                    tv_get_otp.isClickable = true
                }
            }
        })

        ed_code.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            @SuppressLint("Range")
            override fun afterTextChanged(s: Editable) {
                if (s.toString().length > 0) {
                    btnLogin.isClickable = true
                    btnLogin.alpha = 1f
                } else {
                    btnLogin.isClickable = false
                    btnLogin.alpha = 0.5f
                }
            }
        })

        iv_clear.setOnClickListener {
            ed_phone.text.clear()
        }

        tv_get_otp.setOnClickListener {

            //send otp
            if (ed_phone.getText().toString().length == 11) {
                val vDialog = context?.let { it1 -> ImgCodeDialog(it1, ed_phone.text.toString(), this) }
                vDialog?.show()
            } else {
                ToastUtils.showToast("手机号格式错误")
            }
        }
        btnLogin.setOnClickListener {
            KeyboardUtils.hideSoftInput(btnLogin)
            if (cv.isChecked){
                val phone = ed_phone.text.toString()
                val code = ed_code.text.toString()
                if (TextUtils.isEmpty(phone)){
                    ToastUtils.showToast("请输入手机号")
                    return@setOnClickListener
                }
                if (!RegexUtils.isMobileSimple(phone)){
                    ToastUtils.showToast("请输入正确的手机号")
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(code)){
                    ToastUtils.showToast("请输入验证码")
                    return@setOnClickListener
                }
                loginCode()
            } else{
                ToastUtils.showToast("请同意用户协议")
            }
        }
    }

    //获取短信验证码
    override fun inputCode(code: String?) {
        code?.let {
            ApiRetrofit.service.getPhoneCode(ed_phone.text.toString(), it)?.ss(onSuccess = {
                var any = it.data
            })
        }
    }


    //验证码登录
    private fun loginCode() {
        var map = HashMap<String,String>()
        map.put("code",ed_code.text.toString())
        map.put("mobile",ed_phone.text.toString())
        ApiRetrofit.service.loginCode(map)?.ss(onSuccess = {
            val data = it.data
            if (data != null){
                UserManager.get().setNickName(data.nickName)
                UserManager.get().setUserToken(data.token)
                UserManager.get().setUserHead(data.headUrl)
                UserManager.get().setUserMobile(data.mobile)
                dismiss()
            }
        })
    }
}