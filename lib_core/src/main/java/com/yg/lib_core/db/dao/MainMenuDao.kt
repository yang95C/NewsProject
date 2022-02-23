package com.yg.lib_core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yg.lib_core.db.entity.MainMenuBean

@Dao
interface MainMenuDao {

    @Query("SELECT * FROM db_main_menu")
    fun getMainMenuList():MutableList<MainMenuBean>

    @Insert
    fun insertMainMenuList(list: MutableList<MainMenuBean>)

    @Query("delete from db_main_menu")
    fun deleteAll()
}