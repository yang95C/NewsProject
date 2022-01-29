package com.yg.newsproject.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yg.newsproject.R
import com.yg.newsproject.baselibs.base.BaseMvpFragment
import com.yg.main.mvp.contract.HomeContract
import com.yg.main.mvp.presenter.HomePresenter
import org.jetbrains.anko.support.v4.find

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(),HomeContract.View {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val smartRefresh by lazy { find<SmartRefreshLayout>(R.id.smartRefresh) }
    private val recyclerView by lazy { find<RecyclerView>(R.id.recyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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
        smartRefresh.setHeaderHeight(60f)
        smartRefresh.setRefreshHeader(ClassicsHeader(context))
        recyclerView.layoutManager = GridLayoutManager(context,2)
    }

    override fun getRecordsSuccess(any: Any) {

    }
}