package com.yg.home.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yg.home.R
import com.yg.lib_core.bean.MultiItemEntityType
import com.yg.lib_core.bean.NewsListBean
import com.yg.newsproject.baselibs.utils.GlideUtil

class NewsListAdapter(data: MutableList<NewsListBean>? = null) :
    BaseMultiItemQuickAdapter<NewsListBean, BaseViewHolder>(data) {
    init {
        addItemType(MultiItemEntityType.ITEM_0, R.layout.item_news_list_layout_1)
        addItemType(MultiItemEntityType.ITEM_1, R.layout.item_news_list_layout_1)
        addItemType(MultiItemEntityType.ITEM_2, R.layout.item_news_list_layout_2)
    }

    override fun convert(helper: BaseViewHolder, item: NewsListBean) {
        helper ?: return
        item ?: return
        when (helper.itemViewType) {
            MultiItemEntityType.ITEM_0 -> {
                itemData1(helper, item)
            }
            MultiItemEntityType.ITEM_1 -> {
                itemData1(helper, item)
            }
            MultiItemEntityType.ITEM_2 -> {
                itemData2(helper, item)
            }
        }
    }

    private fun itemData2(helper: BaseViewHolder, item: NewsListBean) {
        val tvTitle = helper.getView<TextView>(R.id.text_title)
        val txtImgMore = helper.getView<TextView>(R.id.txt_imgmore)
        val textSource = helper.getView<TextView>(R.id.text_source)
        val commentNum = helper.getView<TextView>(R.id.text_comment_volume)
        val textTag = helper.getView<TextView>(R.id.text_tag)
        val imageView1 = helper.getView<ImageView>(R.id.image_view1)
        val imageView2 = helper.getView<ImageView>(R.id.image_view2)
        val imageView3 = helper.getView<ImageView>(R.id.image_view3)
        val imageDelete = helper.getView<ImageView>(R.id.image_delete)
        tvTitle.text = item.title
        textSource.text = item.dataSource
        if (item.isTop == 1) {
            textTag.visibility = View.VISIBLE
        } else {
            textTag.visibility = View.GONE
        }
        if (item.imgNum != null && item.imgNum!! > 0) {
            txtImgMore.visibility = View.VISIBLE
            txtImgMore.text = "+${item.imgNum}"
        } else {
            txtImgMore.visibility = View.GONE
        }
        commentNum.text = "${item.contentBehavior?.commentCount}评论"
        if (TextUtils.isEmpty(item.titleFilePath)) {

        } else {
            val imgUrl = item.titleFilePath.toString().split(",")
            if (imgUrl.isNotEmpty()) {
                GlideUtil.loadImage(context, imageView1, imgUrl[0])
            }
            if (imgUrl.size > 1) {
                GlideUtil.loadImage(context, imageView2, imgUrl[1])
            }
            if (imgUrl.size > 2) {
                GlideUtil.loadImage(context, imageView3, imgUrl[2])
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
        val imageDelete = helper.getView<ImageView>(R.id.image_delete)
        val llImgBg = helper.getView<LinearLayout>(R.id.ll_img_bg)
        tvTitle.text = item.title
        tvUser.text = item.dataSource
        if (TextUtils.isEmpty(item.titleFilePath)) {
            imgCover.visibility = View.GONE
        } else {
            imgCover.visibility = View.VISIBLE
            val imgUrl = item.titleFilePath.toString().split(",")
            if (imgUrl.isNotEmpty()) {
                GlideUtil.loadImage(context, imgCover, imgUrl[0])
            }
        }
        if (item.isTop == 1) {
            textTag.visibility = View.VISIBLE
        } else {
            textTag.visibility = View.GONE
        }
        if (item.isHot == 1) {
            textHot.visibility = View.VISIBLE
        } else {
            textHot.visibility = View.GONE
        }
        if (item.imgNum != null && item.imgNum!! > 0) {
            llImgBg.visibility = View.VISIBLE
            tvImgNum.text = "${item.imgNum}图"
        } else {
            llImgBg.visibility = View.GONE
        }
    }
}