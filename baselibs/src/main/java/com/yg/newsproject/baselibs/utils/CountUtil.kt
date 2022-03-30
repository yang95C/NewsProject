package com.yg.newsproject.baselibs.utils

import java.text.DecimalFormat

object CountUtil {

    var myformat = DecimalFormat("0.00")
    fun judge(count: Int): String? {
        var resolt = ""
        resolt = if (count < 10000) {
            count.toString() + ""
        } else {
            val str = myformat.format((count / 10000f).toDouble())
            str + "万"
            //resolt = count/10000.f+"万";
            //resolt = count / 10000 + "万+";
        }
        return resolt
    }
}