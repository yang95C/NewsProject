package com.yg.newsproject.baselibs.utils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;

public class AdapterRefreshUtils<T> {

    public int adapterRefresh(BaseQuickAdapter adapter, SmartRefreshLayout smartRefresh, int total, int page, List<T> datas){
        if (page == 1) {
            adapter.getData().clear();
            smartRefresh.finishRefresh();
        } else {
            smartRefresh.finishLoadMore();
        }
        adapter.getData().addAll(datas);
        adapter.notifyDataSetChanged();

        if (adapter.getData().size() >= total) {
            smartRefresh.finishLoadMoreWithNoMoreData();
        } else {
            page++;
        }

        return page;
    }
}
