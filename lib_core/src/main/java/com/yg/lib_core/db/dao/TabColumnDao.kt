package com.yg.lib_core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yg.lib_core.db.entity.ColumnBean

@Dao
interface TabColumnDao {
    @Query("SELECT * FROM db_column where type =:type")
    fun getColumnList(type: String):MutableList<ColumnBean>

    @Insert
    fun insertColumnList(list: MutableList<ColumnBean>)

    @Query("DELETE FROM db_column where type =:type")
    fun deleteAll(type: String)
}