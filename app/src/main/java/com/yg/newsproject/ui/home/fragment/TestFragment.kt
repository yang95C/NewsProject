package com.yg.newsproject.ui.home.fragment

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yg.newsproject.baselibs.base.BaseMvpFragment
import com.yg.newsproject.R
import com.yg.newsproject.baselibs.utils.ToastUtils
import com.yg.newsproject.baselibs.widget.LoadingDialog
import com.yg.newsproject.mvp.contract.TestContract
import com.yg.newsproject.mvp.presenter.TestPresenter
import com.yg.newsproject.ui.home.adapter.MainAdapter
import com.yg.newsproject.ui.home.bean.MainBean
import org.jetbrains.anko.support.v4.find

/**
 * @author chenxz
 * @date 2018/11/30
 * @desc
 */
class TestFragment : BaseMvpFragment<TestContract.View, TestContract.Presenter>(), TestContract.View {
    private val token = "eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAxMDYzMiIsInN1YiI6IjEwMDEwNjMyIiwicHdkIjoiMDRkN2EwNzJjMWUzNTczMTFjMTA5OGNiNTg0N2ViNmUiLCJleHAiOjE2MzA4MjUzOTYsImlhdCI6MTYyOTM1NDE2NywianRpIjoiZTI2NWM1MzQtYWY1YS00ZjhhLWFmMmYtZmE3MzhlNjg4ZDdiIn0.bv4swd32qjMpoPB_FJgNfTzAf8T8tX_FmTi_2Yk7jJE"
    private val page = 1
    private val pageSize = 10

    private val mRefresh by lazy { find<SwipeRefreshLayout>(R.id.mRefresh) }
    private val mRecycler by lazy { find<RecyclerView>(R.id.mRecycler) }
//    lateinit var adapter: MainMultiItemAdapter
    lateinit var adapter: MainAdapter
    var mData = ArrayList<MainBean>()
    lateinit var loadingDialog : LoadingDialog

    override fun createPresenter(): TestContract.Presenter = TestPresenter()

    override fun attachLayoutRes(): Int = R.layout.fragment_test

    override fun lazyLoad() {
        loadData()
        mRecycler.layoutManager = LinearLayoutManager(context)
        adapter = MainAdapter(R.layout.item_main_list)
        adapter.data = mData
        mRecycler.adapter = adapter
        mRefresh.setOnRefreshListener {
            loadData()
            adapter.notifyDataSetChanged()
            mRefresh.isRefreshing = false
        }
        adapter.addChildClickViewIds(R.id.tv_name,R.id.tv_age)
        adapter.setOnItemClickListener { adapter, view, position ->
            ToastUtils.showToast("点击了$position")
        }

        adapter.setOnItemChildClickListener { adapter, view, position ->
            var bean = adapter.getItem(position) as MainBean
            if (view.id == R.id.tv_name){
                ToastUtils.showToast("点击了" + bean.name)
            } else if (view.id == R.id.tv_age){
                ToastUtils.showToast("点击了" + bean.age)
            }
        }
    }

    private fun loadData() {
        //参数传递
        arguments?.getString("position")?.let { Log.d("fragment", it) }

        context?.let {
          loadingDialog = LoadingDialog.showDialog(it,"加载中...",false)!!
        }
        var b1 = MainBean(1)
        b1.name = "杨先生"
        b1.age = 23
        mData.add(b1)

        var b2 = MainBean(1)
        b2.name = "王先生"
        b2.age = 25
        mData.add(b2)

        var b3 = MainBean(1)
        b3.name = "李老师"
        b3.age = 30
        mData.add(b3)
        mPresenter?.getRecords(token,page,pageSize)
    }

    override fun getRecordsSuccess(any: Any) {
        ToastUtils.showToast("成功啦")
        loadingDialog.dismiss()
    }

}