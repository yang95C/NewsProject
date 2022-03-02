package com.yg.home.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.yg.common.router.RouterFragmentPath
import com.yg.home.R
import com.yg.home.mvp.contract.NewsListContract
import com.yg.home.mvp.presenter.NewsListPresenter
import com.yg.home.ui.adapter.NewsListAdapter
import com.yg.lib_core.bean.NewsListBean
import com.yg.lib_core.bean.NewsListPageBean
import com.yg.lib_core.db.entity.ColumnBean
import com.yg.newsproject.baselibs.base.BaseMvpFragment
import com.yg.newsproject.baselibs.utils.AdapterRefreshUtils
import kotlinx.android.synthetic.main.fragment_news_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM = "param"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Route(path = RouterFragmentPath.Home.PAGER_HOME_NEWS_LIST)
class NewsListFragment : BaseMvpFragment<NewsListContract.View,NewsListContract.Presenter>(),
    NewsListContract.View {
    // TODO: Rename and change types of parameters
    private var param: ColumnBean? = null
    private var pageIndex = 1
    private lateinit var adapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getSerializable(ARG_PARAM) as ColumnBean?
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param: ColumnBean) =
            NewsListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM, param)
                }
            }
    }

    override fun createPresenter(): NewsListContract.Presenter {
        return NewsListPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_news_list
    }

    override fun lazyLoad() {
        smartRefresh.setRefreshHeader(ClassicsHeader(context))
        smartRefresh.setRefreshFooter(ClassicsFooter(context))
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = NewsListAdapter()
        recyclerView.adapter = adapter
        smartRefresh.setOnRefreshListener {
            pageIndex = 1
            onRefresh()
        }
        smartRefresh.setOnLoadMoreListener {
            onRefresh()
        }
        onRefresh()
    }

    private fun onRefresh(){
        mPresenter?.findNewsList(pageIndex,param?.id.toString(),"")
    }

    override fun findNewsListSuccess(data: NewsListPageBean?) {
        pageIndex = AdapterRefreshUtils<NewsListBean>().adapterRefresh(adapter,smartRefresh,data!!.records,pageIndex,data.rows)
    }

    override fun findNewsListError() {

    }

}