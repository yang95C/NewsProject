package com.xktt.renovation.ui.my.fragment

import android.content.Intent
import androidx.fragment.app.Fragment
import com.xktt.renovation.R
import com.xktt.renovation.baselibs.base.BaseMvpFragment
import com.xktt.renovation.mvp.contract.MyContract
import com.xktt.renovation.mvp.presenter.MyPresenter
import com.xktt.renovation.ui.my.activity.SettingActivity
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
class MyFragment : BaseMvpFragment<MyContract.View,MyContract.Presenter>(),MyContract.View {

    override fun createPresenter(): MyContract.Presenter {
        return MyPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_my
    }

    override fun lazyLoad() {
        tv_set.setOnClickListener {
            val intent = Intent(context,SettingActivity().javaClass)
            startActivity(intent)
        }
    }

    override fun getRecordsSuccess(any: Any) {

    }
}