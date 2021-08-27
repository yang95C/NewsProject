package com.xktt.renovation.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.xktt.renovation.R
import com.xktt.renovation.baselibs.ext.getResponseBody
import com.xktt.renovation.http.ApiRetrofit
import com.xktt.renovation.utils.DialogUtil

class ImgCodeDialog(context: Context,
                    var phone: String,
                    private val listener: OnRandomVerificationCodeInputListener
) : Dialog(context, R.style.CustomProgressDialog), View.OnClickListener {
    private lateinit var ivCode: ImageView
    private lateinit var edCode: EditText
    private lateinit var btnSubmit: Button
    private lateinit var ivCose: ImageView
    private lateinit var mDialog: QMUITipDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_send_code)
        val window = window
        //设置弹出位置
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mDialog = DialogUtil.getWaitDialog(context, "加载中")
        initView()
        initData()
    }

    private fun initView() {
        ivCode = findViewById(R.id.iv_code)
        edCode = findViewById(R.id.ed_code)
        btnSubmit = findViewById(R.id.btn_submit)
        ivCose = findViewById(R.id.iv_close)

        ivCode.setOnClickListener {

        }
        ivCose.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
        ivCode.setOnClickListener(this)
    }

    private fun initData() {
        if (mDialog != null) {
            mDialog!!.show()
        }
        graphicCode()
    }

    private fun graphicCode() {
        ApiRetrofit.service.graphicalCode(phone)?.getResponseBody(onSuccess = {
            var responseBody = it
            var bytes = responseBody.bytes()
            val bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
            ivCode.setImageBitmap(bitmap)
            if (mDialog != null) {
                mDialog!!.dismiss()
            }
        })
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_submit) {
            if (edCode!!.text.toString().trim { it <= ' ' }.length > 0) {
                listener.inputCode(edCode!!.text.toString())
                dismiss()
            }
        } else if (v.id == R.id.iv_code) {
            graphicCode()
        } else if (v.id == R.id.iv_close) {
            dismiss()
        }
    }

    interface OnRandomVerificationCodeInputListener {
        fun inputCode(code: String?)
    }
}