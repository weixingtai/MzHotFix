package com.meizu.mzhotfix;

/**
 * Created by wuer on 2017/1/10.
 */

public class SOFileManager {

    static {
        System.loadLibrary("jnitest");
    }

    public static native void print();

    public static native String test1(String value);

    public static native void test2();

    public static String getRepairSo() {
        return test1("apk from native");
    }
}
