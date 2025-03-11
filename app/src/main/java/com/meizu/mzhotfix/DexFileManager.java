package com.meizu.mzhotfix;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by wuer on 16/10/18.
 */
public class DexFileManager {

    private final static String TAG = "MzHotFix";
    private final static String SUB_TAG = "DexFileManager-> ";

//    @TestFieldAnnotation(fieldVer = 2)
    @TestFieldAnnotation(fieldVer = 1)
    private int repairFiled;

//    @TestMethodAnnotation(methodVer = 12)
    @TestMethodAnnotation(methodVer = 11)
    public static String getRepairAnnotation() {
        String msg = "";
        try {
            Method method = DexFileManager.class.getDeclaredMethod("getRepairAnnotation");
            TestMethodAnnotation methodAnnotation = method.getAnnotation(TestMethodAnnotation.class);
            if (methodAnnotation!= null) {
                msg = msg + "methodAnnotation: " + methodAnnotation.methodVer();
                msg = msg + "\n";
            }
            TestFieldAnnotation fieldAnnotation = DexFileManager.class.getDeclaredField("repairFiled").getAnnotation(TestFieldAnnotation.class);
            if (fieldAnnotation!= null) {
                msg = msg + "methodAnnotation: " + fieldAnnotation.fieldVer();
                msg = msg + "\n";
            }
        } catch (Exception e) {
            Log.e(TAG, SUB_TAG + "Exception: " + e);
        }
        return msg;
    }

    public static String getRepairMethod() {
        return "这是一段通过方法返回的修复前的文本";
//        return "这是一段通过方法返回的修复后的文本";
    }

    public static String getRepairObjField() {
        DexFileOb dexOb = new DexFileOb();
        return "这是一段对变量的定义：" + "\n" +
                "旧变量 a: " + dexOb.a + "； " + "\n" +
//                "新变量 b: " + dexOb.b + "； " + "\n" +
                "旧变量 c: " + dexOb.c + "； ";
    }

    public static String getRepairObjMethod() {
        DexFileOb dexOb = new DexFileOb();
        return dexOb.getOldText();
//        return dexOb.getNewText();
    }

}





