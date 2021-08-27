package com.xktt.renovation.ui.my.activity

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.PermissionUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.xktt.renovation.R
import com.xktt.renovation.baselibs.config.UserManager
import com.xktt.renovation.baselibs.base.BaseMvpActivity
import com.xktt.renovation.baselibs.ext.setSingleClickListener
import com.xktt.renovation.baselibs.utils.GlideUtil
import com.xktt.renovation.baselibs.view.GlideEngine
import com.xktt.renovation.mvp.contract.PersonalDataContract
import com.xktt.renovation.mvp.presenter.PersonalDataPresenter
import com.xktt.renovation.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_personal_data.*
import kotlinx.android.synthetic.main.item_head_layout.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PersonalDataActivity : BaseMvpActivity<PersonalDataContract.View, PersonalDataContract.Presenter>(),PersonalDataContract.View, View.OnClickListener {

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, "正在加载")
    }


    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_personal_data
    }

    override fun initView() {
        super.initView()
        tv_head_title.setText("个人资料")
        img_head_back.setSingleClickListener { onBackPressed() }
        ll_head.setOnClickListener(this)
    }

    override fun start() {
//        UserManager.get().setUserToken("eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAxMDYzMiIsInN1YiI6IjEwMDEwNjMyIiwicHdkIjoiMDRkN2EwNzJjMWUzNTczMTFjMTA5OGNiNTg0N2ViNmUiLCJleHAiOjE2MzA4MjUzOTYsImlhdCI6MTYyOTM1NDE2NywianRpIjoiZTI2NWM1MzQtYWY1YS00ZjhhLWFmMmYtZmE3MzhlNjg4ZDdiIn0.bv4swd32qjMpoPB_FJgNfTzAf8T8tX_FmTi_2Yk7jJE")
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.ll_head ->{
                addHead()
            }
        }
    }

    private fun addHead() {
        if (!PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)){
            PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        } else{
            PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .theme(R.style.picture_white_style)
                .isEnableCrop(false) // 是否裁剪
                .maxSelectNum(1) // 最大图片选择数量
                .minSelectNum(1) // 最小选择数量
                .imageSpanCount(4) // 每行显示个数
                .isPreviewImage(false) // 是否预览图片
                .isAndroidQTransform(true) // 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) // 设置相册Activity方向，不设置默认使用系统
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(104)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 104){
            val selectList = PictureSelector.obtainMultipleResult(data)
            if (selectList.size > 0){
                var richPath = selectList[0].path
                GlideUtil.loadCircleImage(this,img_user,richPath,R.mipmap.default_mine_head,R.mipmap.default_mine_head,false)

                var img_path: String? = ""
                if (richPath.contains("content")) {
                    val proj = arrayOf(MediaStore.Images.Media.DATA)
                    val actualimagecursor = managedQuery(Uri.parse(richPath), proj, null, null, null)
                    val actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    actualimagecursor.moveToFirst()
                    img_path = actualimagecursor.getString(actual_image_column_index)
                } else {
                    img_path = richPath
                }
                val file = File(img_path)
                val body = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    file
                )
                val multipartBody = MultipartBody.Part.createFormData("multipartFile", file.getName(), body)
                mPresenter?.uploadImage(UserManager.get().getUserToken(),multipartBody,"1")
            }
        }
    }

    override fun uploadImageSuccess(any: Any) {
        UserManager.get().setUserHead(any.toString())
    }

    override fun createPresenter(): PersonalDataContract.Presenter {
        return PersonalDataPresenter()
    }

}