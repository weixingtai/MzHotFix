package com.meizu.mzhotfix;

import android.app.Application;
import android.util.Log;

/**
 * User: xingzhi.wap
 * Date:16/5/17
 */
public class MainApplication extends Application {

    private final static String TAG = "MzHotFix";
    private final static String SUB_TAG = "MainApplication-> ";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, SUB_TAG + "原有的Application，可以通过补丁修改");
    }

}
