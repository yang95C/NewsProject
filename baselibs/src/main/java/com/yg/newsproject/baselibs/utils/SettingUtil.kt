package com.yg.newsproject.baselibs.utils

import android.graphics.Color
import androidx.preference.PreferenceManager
import com.blankj.utilcode.util.Utils
import com.yg.newsproject.baselibs.R


object SettingUtil {
    private var setting = PreferenceManager.getDefaultSharedPreferences(Utils.getApp())

    /**
     * 获取主题颜色
     */
    fun getColor(): Int {
        val defaultColor = Utils.getApp().resources.getColor(R.color.app_color_theme_1)
        val color = setting!!.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else color
    }

    /**
     * 设置主题颜色
     */
    fun setColor(color: Int,colorType: Int) {
        setting!!.edit().putInt("color", color).apply()
        setting!!.edit().putInt("colorType", colorType).apply()
    }

    fun getColorType(): Int {
        return setting!!.getInt("colorType", 0)
    }
}