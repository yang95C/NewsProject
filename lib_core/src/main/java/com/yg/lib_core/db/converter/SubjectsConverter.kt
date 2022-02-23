package com.yg.lib_core.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yg.lib_core.db.entity.MainMenuConfigBean
import java.io.Serializable

class SubjectsConverter : Serializable {
    @TypeConverter
    fun fromOptionValuesList(optionValues: List<MainMenuConfigBean?>?): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<MainMenuConfigBean?>?>() {}.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter
    fun toOptionValuesList(optionValuesString: String?): List<MainMenuConfigBean>? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<MainMenuConfigBean?>?>() {}.type
        return gson.fromJson<List<MainMenuConfigBean>>(optionValuesString, type)
    }
}