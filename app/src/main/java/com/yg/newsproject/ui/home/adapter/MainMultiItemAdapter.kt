package com.yg.newsproject.ui.home.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yg.newsproject.R
import com.yg.newsproject.ui.home.bean.MainBean

/**
 * 多布局适配器
 */
open class MainMultiItemAdapter (data:MutableList<MainBean>? = null): BaseMultiItemQuickAdapter<MainBean, BaseViewHolder>(data) {

    init {
        //必须绑定type和layout的关系
        addItemType(1, R.layout.item_main_list)
    }

    override fun convert(helper: BaseViewHolder, item: MainBean) {
        helper ?: return
        item ?: return
        when (helper.itemViewType) {
            1 -> {
                val tvName = helper.getView<TextView>(R.id.tv_name)
                val tvAge = helper.getView<TextView>(R.id.tv_age)
                tvName.text = "姓名：" + item.name
                tvAge.text = "年龄：" + item.age
            }
            else -> print("其他布局")
        }
    }

}