package com.dobest.ray.raydo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by wangl01 on 2015/11/16.
 */
public class DisPlay {
    public int getDisPlayWidth(Activity thisone, Context context) {
        WindowManager wm = thisone.getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public int getDisPlayHeight(Activity thisone, Context context) {
        WindowManager wm = thisone.getWindowManager();

        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }


}
