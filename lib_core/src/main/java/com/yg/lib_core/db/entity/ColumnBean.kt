package com.yg.lib_core.db.entity

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "db_column")
data class ColumnBean(
    @PrimaryKey
    @Nullable
    val id:String,
    val name:String,
    val type:String?,
    val mannerId:String?,
    val custom:String?,
    val typeName:String?,
    val speciaType:String?,
    val btnType:String?,
    val sort:Int,
    val articleTopNum:Int,
):Serializable
