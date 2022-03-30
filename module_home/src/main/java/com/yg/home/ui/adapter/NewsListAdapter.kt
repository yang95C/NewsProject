package com.yg.home.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yg.home.R
import com.yg.lib_core.bean.MultiItemEntityType
import com.yg.lib_core.bean.NewsListBean
import com.yg.newsproject.baselibs.config.UserManager
import com.yg.newsproject.baselibs.utils.CountUtil
import com.yg.newsproject.baselibs.utils.DateUtils
import com.yg.newsproject.baselibs.utils.GlideUtil
import com.yg.newsproject.baselibs.widget.CircleImageView
import java.lang.Exception

class NewsListAdapter(data: MutableList<NewsListBean>? = null) :
    BaseMultiItemQuickAdapter<NewsListBean, BaseViewHolder>(data) {
    init {
        addItemType(MultiItemEntityType.ITEM_0, R.layout.item_news_list_layout_1)
        addItemType(MultiItemEntityType.ITEM_1, R.layout.item_news_list_layout_1)
        addItemType(MultiItemEntityType.ITEM_2, R.layout.item_news_list_layout_2)
        addItemType(MultiItemEntityType.ITEM_3, R.layout.item_news_list_layout_3)
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
            MultiItemEntityType.ITEM_3 -> {
                itemData3(helper, item)
            }
        }
    }

    private fun itemData3(helper: BaseViewHolder, item: NewsListBean) {
        val imgCover = helper.getView<ImageView>(R.id.img_cover)
        val imagePlay = helper.getView<ImageView>(R.id.image_play)
        val imgV = helper.getView<ImageView>(R.id.img_v)
        val imageZan = helper.getView<ImageView>(R.id.image_zan)
        val imageComment = helper.getView<ImageView>(R.id.image_comment)
        val imageShare = helper.getView<ImageView>(R.id.image_share)
        val imageSource = helper.getView<CircleImageView>(R.id.image_source)
        val textTitle = helper.getView<TextView>(R.id.text_title)
        val textTimes = helper.getView<TextView>(R.id.text_times)
        val textCount = helper.getView<TextView>(R.id.text_count)
        val textSource = helper.getView<TextView>(R.id.text_source)
        val textTime = helper.getView<TextView>(R.id.text_time)
        val textFollow = helper.getView<TextView>(R.id.text_follow)
        val textZanVolume = helper.getView<TextView>(R.id.text_zan_volume)
        val textCommentVolume = helper.getView<TextView>(R.id.text_comment_volume)
        val viewVideo = helper.getView<View>(R.id.view_video)
        textTitle.text = item.title
        textSource.text = item.dataSource
        //设置时间
        setVideoTimeLen(textTimes, item.timeLen.toString())

        if (item.followedByMe === 1) {
            textFollow.text = "已关注"
            textFollow.setTextColor(
                context.resources.getColor(R.color.text_grey)
            )
        } else {
            textFollow.text = "关注"
            textFollow.setTextColor(
                context.resources.getColor(R.color.Red)
            )
        }

        if ("null" == item.identifyInfo || StringUtils.isEmpty(item.identifyInfo)) {
            imgV.visibility = View.GONE
        } else {
            imgV.visibility = View.VISIBLE
        }
        if (TextUtils.isEmpty(item.createById) || item.createById.equals(
                UserManager.get().getUserId()
            )
        ) {
            textFollow.visibility = View.INVISIBLE
        } else {
            textFollow.visibility = View.VISIBLE
        }

        //视频图片
        if (StringUtils.isEmpty(item.videoCover)) {
            Glide.with(context)
                .load(R.mipmap.icon_timg)
                .into(imgCover)
        } else {
            GlideUtil.loadImage(context, imgCover, item.videoCover!!)
        }
        GlideUtil.loadImage(context, imageSource, item.dataSourceIcon!!)

        //播放次数
        if (item.contentBehavior != null) {
            val counts: Int = item.contentBehavior.getPageViewCount()
            if (counts > 0) {
                textCount.visibility = View.VISIBLE
                textCount.text ="${CountUtil.judge(counts)}人气"
            } else {
                textCount.visibility = View.GONE
                textCount.text = ""
            }
            val comment: Int = item.contentBehavior.commentCount
            if (comment > 0) {
                imageComment.visibility = View.VISIBLE
                textCommentVolume.text = comment.toString() + ""
            } else {
                imageComment.visibility = View.VISIBLE
                textCommentVolume.text = ""
            }
            val zan: Int = item.contentBehavior.agreeWithCount
            if (item.isAgreeByMe === 1) {
                imageZan.setImageResource(R.mipmap.icon_zan_selected_style1)
            } else {
                imageZan.setImageResource(R.mipmap.icon_zan_normal_style1)
            }
            if (zan > 0) {
                textZanVolume.text = CountUtil.judge(zan).toString() + ""
            } else {
                textZanVolume.text = ""
            }
        } else {
            textCount.text = ""
            textCount.visibility = View.GONE
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

    private fun setVideoTimeLen(textView: TextView, time: String) {
        if (timeMatch(time)) {
            val finalTime: String = DateUtils.formatTimeS(time.toLong()).toString()
            textView.text = finalTime
            textView.visibility = View.VISIBLE
        } else {
            textView.text = ""
            textView.visibility = View.GONE
        }
    }

    private fun timeMatch(time: String): Boolean {
        if (StringUtils.isEmpty(time)) {
            //数据为空
            return false
        } else if ("0" == time) {
            //数据为0
            return false
        }
        return true
    }
}