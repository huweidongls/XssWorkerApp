package com.jingna.xssworkerapp.widget;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018/10/10.
 */

public interface HttpCallBackListener {
    void onFinish(Bitmap bitmap);
    void onError(Exception e);
}
