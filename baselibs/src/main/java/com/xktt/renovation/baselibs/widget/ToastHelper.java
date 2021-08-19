package com.xktt.renovation.baselibs.widget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/12/6.
 */

public class ToastHelper {
    private final Context mContext;
    private Toast mToast;

    public ToastHelper(Context context) {
        mContext = context;
    }

    public void showToast(CharSequence text, int duration) {
        if (mContext == null)
            return;

        if (TextUtils.isEmpty(text))
            return;

        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(mContext, text, duration);
        mToast.show();
    }

    public void showToast(int rsId, int duration) {
        if (mContext == null)
            return;
        showToast(mContext.getResources().getString(rsId), duration);
    }

    public void showToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void showToast(int rsId) {
        showToast(rsId, Toast.LENGTH_SHORT);
    }

    public boolean cancel() {
        if (mToast == null)
            return false;
        mToast.cancel();
        return true;
    }

}
