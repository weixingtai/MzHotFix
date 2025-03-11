package com.meizu.mzhotfix;

/**
 * Created by wuer on 2017/1/10.
 */

public class SOFileManager {

    static {
        System.loadLibrary("mzhotfix");
    }

    public static native String stringFromJNI();

    public static String getRepairSo() {
        return stringFromJNI();
    }
}
