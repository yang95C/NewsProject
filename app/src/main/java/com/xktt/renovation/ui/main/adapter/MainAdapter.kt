package com.xktt.renovation.ui.main.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xktt.renovation.R
import com.xktt.renovation.ui.main.bean.MainBean

open class MainAdapter (layoutId:Int, data:MutableList<MainBean>? = null): BaseQuickAdapter<MainBean, BaseViewHolder>(layoutId,data) {

    override fun convert(helper: BaseViewHolder, item: MainBean) {
        val tvName = helper.getView<TextView>(R.id.tv_name)
        val tvAge = helper.getView<TextView>(R.id.tv_age)
        tvName.text = "姓名：" + item.name
        tvAge.text = "年龄：" + item.age
    }

}