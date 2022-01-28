package com.yg.newsproject.bean

data class StartPageBean(
    val id: String,
    val startDate: String,
    val endDate: String,
    val title: String,
    val startDisplayTime: MutableList<String>,
    val titleFilePath: MutableList<String>,
    val fileType: Int,
    val status: Int
)
