package com.yg.lib_core.db.entity

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yg.lib_core.db.converter.SubjectsConverter

@Entity(tableName = "db_main_menu")
@TypeConverters(SubjectsConverter::class)
data class MainMenuBean(
    @PrimaryKey
    @Nullable
    val id: String,
    val areaId: String?,
    val name: String?,
    val imgUrl: String?,
    val type: Int?,
    val sort: Int?,
    val subjects: MutableList<MainMenuConfigBean>?
)

data class MainMenuConfigBean(
    val name: String?,
    val subjectImgUrl: String?
)
