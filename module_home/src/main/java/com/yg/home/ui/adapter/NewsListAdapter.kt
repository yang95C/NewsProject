package com.yg.home.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yg.home.R
import com.yg.lib_core.bean.NewsListBean
import com.yg.newsproject.baselibs.utils.GlideUtil

class NewsListAdapter(data: MutableList<NewsListBean>? = null): BaseMultiItemQuickAdapter<NewsListBean,BaseViewHolder>(data) {
    init {
        addItemType(0, R.layout.item_news_list_layout_1)
        addItemType(1, R.layout.item_news_list_layout_1)
    }

    override fun convert(helper: BaseViewHolder, item: NewsListBean) {
        helper?: return
        item?:return
        when(helper.itemViewType){
            0 -> {
                itemData1(helper,item)
            }
             1 -> {
                itemData1(helper,item)
             }
        }
    }

    private fun itemData1(helper: BaseViewHolder, item: NewsListBean) {
        val tvTitle = helper.getView<TextView>(R.id.tv_title)
        val tvImgNum = helper.getView<TextView>(R.id.tv_img_num)
        val textTag = helper.getView<TextView>(R.id.text_tag)
        val textHot = helper.getView<TextView>(R.id.text_hot)
        val tvUser = helper.getView<TextView>(R.id.tv_user)
        val imgCover = helper.getView<ImageView>(R.id.img_cover)
        val llImgBg = helper.getView<LinearLayout>(R.id.ll_img_bg)
        tvTitle.text = item.title
        tvUser.text = item.dataSource
        if (TextUtils.isEmpty(item.titleFilePath)){
            imgCover.visibility = View.GONE
        } else {
            imgCover.visibility = View.VISIBLE
            GlideUtil.loadImage(context,imgCover,item.titleFilePath.toString())
        }
        if (item.isTop == 1){
            textTag.visibility = View.VISIBLE
        } else {
            textTag.visibility = View.GONE
        }
        if (item.isHot == 1){
            textHot.visibility = View.VISIBLE
        } else {
            textHot.visibility = View.GONE
        }
        if (item.imgNum != null && item.imgNum!! > 0){
            llImgBg.visibility = View.VISIBLE
            tvImgNum.text = "${item.imgNum}图"
        } else {
            llImgBg.visibility = View.GONE
        }
    }
}