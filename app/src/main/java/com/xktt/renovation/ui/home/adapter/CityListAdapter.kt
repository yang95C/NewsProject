package com.xktt.renovation.ui.home.adapter

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xktt.renovation.R
import com.xktt.renovation.ui.home.bean.CityBean
import com.xktt.renovation.ui.home.bean.CityListBean

open class CityListAdapter (layoutId:Int, data:MutableList<CityBean>? = null): BaseQuickAdapter<CityBean, BaseViewHolder>(layoutId,data) {

    override fun convert(helper: BaseViewHolder, item: CityBean) {
        val tv_index = helper.getView<TextView>(R.id.tv_index)
        val tv_city = helper.getView<TextView>(R.id.tv_city)
            tv_city.setText(item.name)
        if (TextUtils.isEmpty(item.index)){
            tv_index.visibility = View.GONE
        } else{
            tv_index.visibility = View.VISIBLE
            tv_index.setText(item.index)
        }
    }

}