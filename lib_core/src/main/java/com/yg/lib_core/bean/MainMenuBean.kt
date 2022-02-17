package com.yg.lib_core.bean

data class MainMenuBean(
    val id: String,
    val areaId: String,
    val name: String,
    val imgUrl: String,
    val type: Int,
    val sort: Int,
    val subjects: MutableList<MainMenuConfigBean>
)

data class MainMenuConfigBean(
    val name: String,
    val subjectImgUrl: String
)
