package com.yg.home.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.yg.common.adapter.MainPageAdapter
import com.yg.common.router.RouterFragmentPath
import com.yg.home.R
import com.yg.home.mvp.contract.HomeContract
import com.yg.home.mvp.presenter.HomePresenter
import com.yg.lib_core.db.database.CommonDatabase
import com.yg.lib_core.db.entity.ColumnBean
import com.yg.newsproject.baselibs.base.BaseMvpFragment
import com.yg.newsproject.baselibs.utils.SettingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TYPE = "type"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@Route(path = RouterFragmentPath.Home.PAGER_HOME)
class HomeFragment : BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(),
    HomeContract.View {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var type: String? = null

    private var nameList = ArrayList<String>()
    private var fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            type = it.getString(TYPE)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun createPresenter(): HomeContract.Presenter {
        return HomePresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun lazyLoad() {
        viewPager.adapter = MainPageAdapter(fragments, nameList, childFragmentManager)
        homeTabLayout.setupWithViewPager(viewPager)
        homeTabLayout.setSelectedTabIndicatorColor(SettingUtil.getColor())
        homeTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val textView = tab?.view?.getChildAt(1) as TextView
                textView.apply {
                    setTextColor(SettingUtil.getColor())
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                    typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val textView = tab?.view?.getChildAt(1) as TextView
                textView.apply {
                    setTextColor(resources.getColor(R.color.text_black))
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        type?.let { mPresenter?.findColumnList(it) }
    }

    override fun getColumnListSuccess(data: MutableList<ColumnBean>?) = if (data != null) {
        for (i in data.indices) {
            val bean = data[i]
            nameList.add(bean.name)
            fragments.add(
                ARouter.getInstance().build(RouterFragmentPath.User.PAGER_USER)
                    .navigation() as Fragment
            )
        }
        if (data.size <= 1){
            homeTabLayout.visibility = View.GONE
        }
        viewPager.adapter?.notifyDataSetChanged()
        Thread {
            val dao = CommonDatabase.getInstance(requireContext()).columnDao()
            type?.let { dao.deleteAll(it) }
            dao.insertColumnList(data)
        }.start()
    } else {
        getDataColumnList()
    }

    override fun getColumnListError() {
        getDataColumnList()
    }

    private fun getDataColumnList() {
        val list = ArrayList<ColumnBean>()
        Observable.just(list).flatMap {
            val list = CommonDatabase.getInstance(requireContext()).columnDao().getColumnList(type.toString())
            return@flatMap Observable.just(list)
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                for (i in it.indices) {
                    val bean = it[i]
                    nameList.add(bean.name)
                    fragments.add(
                        ARouter.getInstance().build(RouterFragmentPath.User.PAGER_USER)
                            .navigation() as Fragment
                    )
                }
                viewPager.adapter?.notifyDataSetChanged()
                if (it.size <= 1){
                    homeTabLayout.visibility = View.GONE
                }
            }, {
                it.printStackTrace()
            })
    }
}