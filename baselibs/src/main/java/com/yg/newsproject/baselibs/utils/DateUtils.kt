package com.yg.newsproject.baselibs.utils

import java.lang.Exception
import java.text.SimpleDateFormat

object DateUtils {
    private val formatShort = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //2018-07-27 15:55:26

    fun isThanTarget(target:String):Boolean{
        val nowTime = System.currentTimeMillis()
        return try {
            val date = formatShort.parse(target)
            nowTime < date.time
        }catch (e : Exception){
            false
        }
    }
}