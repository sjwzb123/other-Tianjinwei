package com.bixing.tiannews.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sjw on 2017/9/25.
 */

public class ToastUtils {
    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
