/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.jingna.xssworkerapp.baidu;

import android.content.Context;


public class NormalUtils {

//    public static void gotoSettings(Activity activity) {
//        Intent it = new Intent(activity, DemoNaviSettingActivity.class);
//        activity.startActivity(it);
//    }
//
//    public static void gotoDriving(Activity activity) {
//        Intent it = new Intent(activity, DemoDrivingActivity.class);
//        activity.startActivity(it);
//    }

    public static String getTTSAppID() {
        return "16699561";
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
}
