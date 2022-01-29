package com.yg.user.ui.fragment

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.yg.common.router.RouterFragmentPath

import com.yg.newsproject.baselibs.base.BaseMvpFragment

import com.yg.lib_core.bean.UserInfoBean
import com.yg.user.R
import com.yg.user.ui.mvp.contract.MyContract
import com.yg.user.ui.mvp.presenter.MyPresenter
import kotlinx.android.synthetic.main.fragment_my.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Route(path = RouterFragmentPath.User.PAGER_USER)
class MyFragment : BaseMvpFragment<MyContract.View,MyContract.Presenter>(),MyContract.View {

    override fun createPresenter(): MyContract.Presenter {
        return MyPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_my
    }

    override fun lazyLoad() {
        tv_set.setOnClickListener {
//            val intent = Intent(context,SettingActivity().javaClass)
//            startActivity(intent)
        }
        ll_diary.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
//               val loginDialog = LoginDialog().newInstance()
//                loginDialog?.show(childFragmentManager,"login")
//                loginDialog?.setLoginListener(object :LoginDialog.OnLoginSuccessListener{
//                    override fun OnLoginSuccess(bean: UserInfoBean) {
//
//                    }
//                })
            }
        })
    }

    override fun getRecordsSuccess(any: Any) {

    }
}