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

    fun formatTimeS(seconds: Long): String? {
        var temp = 0
        val sb = StringBuffer()
        if (seconds > 3600) {
            temp = (seconds / 3600).toInt()
            sb.append(if (seconds / 3600 < 10) "0$temp:" else "$temp:")
            temp = (seconds % 3600 / 60).toInt()
            changeSeconds(seconds, temp, sb)
        } else {
            temp = (seconds % 3600 / 60).toInt()
            changeSeconds(seconds, temp, sb)
        }
        return sb.toString()
    }

    private fun changeSeconds(seconds: Long, temp: Int, sb: StringBuffer) {
        var temp = temp
        sb.append(if (temp < 10) "0$temp:" else "$temp:")
        temp = (seconds % 3600 % 60).toInt()
        sb.append(if (temp < 10) "0$temp" else "" + temp)
    }
}