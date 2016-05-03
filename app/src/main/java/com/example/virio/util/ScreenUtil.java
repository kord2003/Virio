package com.example.virio.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class ScreenUtil {
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }
}