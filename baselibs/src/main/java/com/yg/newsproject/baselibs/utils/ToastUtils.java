package com.yg.newsproject.baselibs.utils;

import android.content.Context;

import com.yg.newsproject.baselibs.widget.ToastHelper;


public class ToastUtils {
    static ToastHelper sToastHelper;

    public static void init(Context context) {
        if (sToastHelper == null) {
            sToastHelper = new ToastHelper(context);
        }
    }

    public static void showToast(CharSequence text) {
        if (sToastHelper != null) {
            sToastHelper.showToast(text);
        }
    }

    public static void showToast(int rsId) {
        if (sToastHelper != null) {
            sToastHelper.showToast(rsId);
        }
    }

    static public ToastHelper getToastHelper() {
        return sToastHelper;
    }

}
