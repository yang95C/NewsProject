package com.yg.newsproject.baselibs.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object AssetsUtils {
    fun getJson(context:Context, fileName:String):String{
        val stringBuilder = StringBuilder()
        try {
            //获取assets资源管理器
           val assetManager = context.assets
            //通过管理器打开文件并读取
            val bf = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName)
                )
            )
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}