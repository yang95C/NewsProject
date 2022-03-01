package com.yg.lib_core.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yg.lib_core.db.dao.MainMenuDao
import com.yg.lib_core.db.dao.TabColumnDao
import com.yg.lib_core.db.entity.ColumnBean
import com.yg.lib_core.db.entity.MainMenuBean

@Database(entities = [MainMenuBean::class,ColumnBean::class],version = 1,exportSchema = false)
abstract class CommonDatabase :RoomDatabase() {
    abstract fun mainMenuDao(): MainMenuDao
    abstract fun columnDao():TabColumnDao

    companion object{
        private var instance:CommonDatabase? = null
        fun getInstance(context: Context): CommonDatabase{
            if (instance == null){
                synchronized(CommonDatabase::class){
                    instance = Room.databaseBuilder(context,CommonDatabase::class.java,"common_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }
    }
}