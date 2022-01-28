package com.yg.newsproject.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.yg.newsproject.R
import com.yg.newsproject.baselibs.config.UserManager
import com.yg.newsproject.baselibs.ext.ss
import com.yg.newsproject.baselibs.utils.ToastUtils
import com.yg.newsproject.http.ApiRetrofit
import com.yg.newsproject.widgets.ImgCodeDialog
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

class LoginDialog : DialogFragment() , ImgCodeDialog.OnRandomVerificationCodeInputListener {
    lateinit var img_finish :ImageView
    lateinit var ed_phone :EditText
    lateinit var iv_clear :ImageView
    lateinit var ed_code :EditText
    lateinit var ed_pass :EditText
    lateinit var tv_get_otp :TextView
    lateinit var btnLogin:Button
    lateinit var cv:CheckBox
    lateinit var tv_pass:TextView
    lateinit var iv_clear1:ImageView
    lateinit var cl_code:ConstraintLayout
    lateinit var cl_pass:ConstraintLayout
    lateinit var listener:OnLoginSuccessListener


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

        cl_code = view.find(R.id.cl_code)
        ed_pass = view.find(R.id.ed_pass)
        tv_pass = view.find(R.id.tv_pass)
        iv_clear1 = view.find(R.id.iv_clear1)
        cl_pass = view.find(R.id.cl_pass)

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


        ed_pass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            @SuppressLint("Range")
            override fun afterTextChanged(s: Editable) {
                if (TextUtils.isEmpty(s.toString())){
                    iv_clear1.visibility = View.GONE
                } else {
                    iv_clear1.visibility = View.VISIBLE
                }
                if (s.toString().length > 7) {
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

        iv_clear1.setOnClickListener {
            ed_pass.text.clear()
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
                val pass = ed_pass.text.toString()
                if (TextUtils.isEmpty(phone)){
                    ToastUtils.showToast("请输入手机号")
                    return@setOnClickListener
                }
                if (!RegexUtils.isMobileSimple(phone)){
                    ToastUtils.showToast("请输入正确的手机号")
                    return@setOnClickListener
                }
                if (cl_code.visibility == View.VISIBLE){
                    if (TextUtils.isEmpty(code)){
                        ToastUtils.showToast("请输入验证码")
                        return@setOnClickListener
                    }
                    loginCode()
                } else {
                    if (TextUtils.isEmpty(pass)){
                        ToastUtils.showToast("请输入密码")
                        return@setOnClickListener
                    }
                    if (pass.length < 8){
                        ToastUtils.showToast("密码不正确")
                        return@setOnClickListener
                    }
                    loginPass()
                }
            } else{
                ToastUtils.showToast("请同意用户协议")
            }
        }

        tv_pass.setOnClickListener {
            if (cl_code.visibility == View.VISIBLE){
                cl_code.visibility = View.GONE
                cl_pass.visibility = View.VISIBLE
                tv_pass.text = "验证码登录"
                getTimeCount()?.cancel()
            } else {
                cl_code.visibility = View.VISIBLE
                cl_pass.visibility = View.GONE
                tv_pass.text = "账号密码登录"
            }
        }
    }

    //获取短信验证码
    override fun inputCode(code: String?) {
        code?.let {
            ApiRetrofit.service.getPhoneCode(ed_phone.text.toString(), it)?.ss(onSuccess = {
                if (it.retcode == "0000"){
                    startTimer()
                } else {
                    ToastUtils.showToast(it.retmsg)
                }
            })
        }
    }

    private fun startTimer(){
        getTimeCount()?.start()
        tv_get_otp.isClickable = false
    }

    private fun getTimeCount(): TimeCount? {
        return if (mTimeCount == null) TimeCount(60 * 1000, 500).also {
            mTimeCount = it
        } else mTimeCount
    }

    //验证码登录
    private fun loginCode() {
        var map = HashMap<String,String>()
        map.put("code",ed_code.text.toString())
        map.put("mobile",ed_phone.text.toString())
        ApiRetrofit.service.loginCode(map)?.ss(onSuccess = {
            val data = it.data
            if (data != null && it.retcode == "0000"){
                UserManager.get().setNickName(if (TextUtils.isEmpty(data.nickName)) "" else data.nickName)
                UserManager.get().setUserToken(if (TextUtils.isEmpty(data.token)) "" else data.token)
                UserManager.get().setUserHead(if (TextUtils.isEmpty(data.headUrl)) "" else data.headUrl)
                UserManager.get().setUserMobile(if (TextUtils.isEmpty(data.mobile)) "" else data.mobile)
                if (listener != null){
                    listener.OnLoginSuccess(data)
                }
                dismiss()
            } else {
                ToastUtils.showToast(it.retmsg)
            }
        })
    }

    //密码登录
    private fun loginPass() {
        var map = HashMap<String,String>()
        map.put("password",ed_pass.text.toString())
        map.put("mobile",ed_phone.text.toString())
        ApiRetrofit.service.loginPass(map)?.ss(onSuccess = {
            val data = it.data
            if (data != null && it.retcode == "0000"){
                UserManager.get().setNickName(if (TextUtils.isEmpty(data.nickName)) "" else data.nickName)
                UserManager.get().setUserToken(if (TextUtils.isEmpty(data.token)) "" else data.token)
                UserManager.get().setUserHead(if (TextUtils.isEmpty(data.headUrl)) "" else data.headUrl)
                UserManager.get().setUserMobile(if (TextUtils.isEmpty(data.mobile)) "" else data.mobile)
                if (listener != null){
                    listener.OnLoginSuccess(data)
                }
                dismiss()
            } else {
                ToastUtils.showToast(it.retmsg)
            }
        })
    }

    private var mTimeCount: TimeCount? = null


    inner class TimeCount(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
//            text.set(makeWaitingText(millisUntilFinished));
            tv_get_otp.text = makeWaitingText(millisUntilFinished)
            tv_get_otp.isClickable = true
            tv_get_otp.isEnabled = true
        }

        override fun onFinish() {
            onStopTimer()
        }

        private fun onStopTimer() {
            tv_get_otp.isClickable = true
            tv_get_otp.text = "获取验证码"
            tv_get_otp.isEnabled = true
        }

        private fun makeWaitingText(time: Long): String? {
            return generateSendCodeText(time)
        }
    }

    fun generateSendCodeText(time: Long): String? {
        return ((time - 250) / 1000).toString() + "s"
    }

    interface OnLoginSuccessListener{
        fun OnLoginSuccess(bean: UserInfoBean)
    }

    fun setLoginListener(l: OnLoginSuccessListener){
        listener = l
    }
}