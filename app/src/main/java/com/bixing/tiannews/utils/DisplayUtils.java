package com.bixing.tiannews.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by sjw on 2017/9/27.
 */

public class DisplayUtils {

    public static int getW(Activity context) {
        WindowManager wm = context.getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return width;
    }

    public static int getH(Activity context) {
        WindowManager wm = context.getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
}
