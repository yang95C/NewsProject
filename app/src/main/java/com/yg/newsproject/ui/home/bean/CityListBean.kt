package com.yg.newsproject.ui.home.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

data class CityListBean(override val itemType: Int,
                        val name : String ,
                        val index : String ,
                        val city : ArrayList<CityBean>
                        ) : MultiItemEntity

data class CityBean (val name : String,
                     var index : String )