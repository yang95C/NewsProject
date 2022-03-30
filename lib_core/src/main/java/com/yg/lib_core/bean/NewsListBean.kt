package com.yg.lib_core.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

data class NewsListPageBean(
    val pageSize: Int,
    val total: Int,
    val lastPage: Boolean,
    val code: Int,
    val page: Int,
    val records: Int,
    val message: String,
    val rows: MutableList<NewsListBean>
)

data class NewsListBean(
    val id: String,
    val areaId: String?,
    val createDate: String?,
    val publishTime: String?,
    val title: String?,
    val titleFilePath: String?,
    val columnType: String?,
    val columnId: String?,
    val columnName: String?,
    val dataSource: String?,
    val dataSourceIcon: String?,
    val createById: String?,
    val videoCover: String?,
    val videoRadio: String?,
    val contentType: String?,
    val url: String?,
    val identifyInfo: String?,
    val isTop: Int,
    val isHot: Int,
    val isAgreeByMe: Int,
    val imgNum: Int?,
    val fileType: Int,
    val timeLen: Int,
    val followedByMe: Int,
    val contentBehavior: ContentBehaviorBean
):Serializable, MultiItemEntity {
    override val itemType: Int
        get() = if (fileType > 3) 2 else fileType
}

data class ContentBehaviorBean(
    val id: String?,
    val agreeWithCount: Int,
    val collectionCount: Int,
    val shareCount: Int,
    val seeCount: Int,
    val commentCount: Int,
    val browseCount: Int,
    val isAddSee: Boolean = false
) {
    fun getPageViewCount():Int{
        return if (isAddSee){
            browseCount + seeCount
        } else {
            browseCount
        }
    }
}
