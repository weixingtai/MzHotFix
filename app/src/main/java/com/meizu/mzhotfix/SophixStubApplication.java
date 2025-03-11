package com.meizu.mzhotfix;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

public class SophixStubApplication extends SophixApplication {

    private final String TAG = "MzHotFix";
    private final String SUB_TAG = "SophixStubApplication-> ";

    @Keep
    @SophixEntry(MainApplication.class)//只有这里改成自己的Application类，下面static不要改
    static class RealApplicationStub {
    }

    //这里不能调用非系统API的类
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //如用到MultiDex，必须在此初始化
//        MultiDex.install(this);

        initSophix();
    }

    private void initSophix() {
        Log.i(TAG, SUB_TAG + "initSophix");
        String appVersion = "0.0.0";
        try {
            appVersion = getBaseContext().getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }

        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    //mode 无实际意义，为了兼容老版本，默认始终为0
                    //code 补丁加载状态码，详情查看PatchStatus类说明
                    //info 补丁加载详细说明
                    //handlePatchVersion 当前处理的补丁版本号，0：无；-1：本地补丁；其它：后台补丁
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        String msg = "";
                        switch (code) {
                            case PatchStatus.CODE_LOAD_SUCCESS:
                                msg = "加载阶段, 成功";
                                break;
                            case PatchStatus.CODE_ERR_INBLACKLIST:
                                msg = "加载阶段, 失败设备不支持";
                                break;
                            case PatchStatus.CODE_REQ_NOUPDATE:
                                msg = "查询阶段, 没有发布新补丁";
                                break;
                            case PatchStatus.CODE_REQ_NOTNEWEST:
                                msg = "查询阶段, 补丁不是最新的";
                                break;
                            case PatchStatus.CODE_DOWNLOAD_SUCCESS:
                                msg = "查询阶段, 补丁下载成功";
                                break;
                            case PatchStatus.CODE_DOWNLOAD_BROKEN:
                                msg = "查询阶段, 补丁文件损坏下载失败";
                                break;
                            case PatchStatus.CODE_UNZIP_FAIL:
                                msg = "查询阶段, 补丁解密失败";
                                break;
                            case PatchStatus.CODE_LOAD_RELAUNCH:
                                msg = "预加载阶段, 需要重启";
                                break;
                            case PatchStatus.CODE_REQ_APPIDERR:
                                msg = "查询阶段, appid异常";
                                break;
                            case PatchStatus.CODE_REQ_SIGNERR:
                                msg = "查询阶段, 签名异常";
                                break;
                            case PatchStatus.CODE_REQ_UNAVAIABLE:
                                msg = "查询阶段, 系统无效";
                                break;
                            case PatchStatus.CODE_REQ_SYSTEMERR:
                                msg = "查询阶段, 系统异常";
                                break;
                            case PatchStatus.CODE_REQ_CLEARPATCH:
                                msg = "查询阶段, 一键清除补丁";
                                break;
                            case PatchStatus.CODE_PATCH_INVAILD:
                                msg = "加载阶段, 补丁格式非法";
                                break;
                                //查询阶段的code说明
                            case PatchStatus.CODE_QUERY_UNDEFINED:
                                msg = "未定义异常";
                                break;
                            case PatchStatus.CODE_QUERY_CONNECT:
                                msg = "连接异常";
                                break;
                            case PatchStatus.CODE_QUERY_STREAM:
                                msg = "流异常";
                                break;
                            case PatchStatus.CODE_QUERY_EMPTY:
                                msg = "请求空异常";
                                break;
                            case PatchStatus.CODE_QUERY_BROKEN:
                                msg = "请求完整性校验失败异常";
                                break;
                            case PatchStatus.CODE_QUERY_PARSE:
                                msg = "请求解析异常";
                                break;
                            case PatchStatus.CODE_QUERY_LACK:
                                msg = "请求缺少必要参数异常";
                                break;
                                //预加载阶段的code说明
                            case PatchStatus.CODE_PRELOAD_SUCCESS:
                                msg = "预加载成功";
                                break;
                            case PatchStatus.CODE_PRELOAD_UNDEFINED:
                                msg = "未定义异常";
                                break;
                            case PatchStatus.CODE_PRELOAD_HANDLE_DEX:
                                msg = "dex加载异常";
                                break;
                            case PatchStatus.CODE_PRELOAD_NOT_ZIP_FORMAT:
                                msg = "基线dex非zip格式异常";
                                break;
                            case PatchStatus.CODE_PRELOAD_REMOVE_BASEDEX:
                                msg = "基线dex处理异常";
                                break;
                                //加载阶段的code说明 分三部分dex加载, resource加载, lib加载
                                //dex加载
                            case PatchStatus.CODE_LOAD_UNDEFINED:
                                msg = "未定义异常";
                                break;
                            case PatchStatus.CODE_LOAD_AES_DECRYPT:
                                msg = "aes对称解密异常";
                                break;
                            case PatchStatus.CODE_LOAD_MFITEM:
                                msg = "补丁SOPHIX.MF文件解析异常";
                                break;
                            case PatchStatus.CODE_LOAD_COPY_FILE:
                                msg = "补丁拷贝异常";
                                break;
                            case PatchStatus.CODE_LOAD_SIGNATURE:
                                msg = "补丁签名校验异常";
                                break;
                            case PatchStatus.CODE_LOAD_SOPHIX_VERSION:
                                msg = "补丁和补丁工具版本不一致异常";
                                break;
                            case PatchStatus.CODE_LOAD_NOT_ZIP_FORMAT:
                                msg = "补丁zip解析异常";
                                break;
                            case PatchStatus.CODE_LOAD_DELETE_OPT:
                                msg = "删除无效odex文件异常";
                                break;
                            case PatchStatus.CODE_LOAD_HANDLE_DEX:
                                msg = "加载dex异常";
                                break;
                                // 反射调用异常
                            case PatchStatus.CODE_LOAD_FIND_CLASS:
                                msg = "反射调用异常: CODE_LOAD_FIND_CLASS";
                                break;
                            case PatchStatus.CODE_LOAD_FIND_CONSTRUCTOR:
                                msg = "反射调用异常: CODE_LOAD_FIND_CONSTRUCTOR";
                                break;
                            case PatchStatus.CODE_LOAD_FIND_METHOD:
                                msg = "反射调用异常: CODE_LOAD_FIND_METHOD";
                                break;
                            case PatchStatus.CODE_LOAD_FIND_FIELD:
                                msg = "反射调用异常: CODE_LOAD_FIND_FIELD";
                                break;
                            case PatchStatus.CODE_LOAD_ILLEGAL_ACCESS:
                                msg = "反射调用异常: CODE_LOAD_ILLEGAL_ACCESS";
                                break;
                                //resource加载
                            case PatchStatus.CODE_LOAD_RES_ADDASSERTPATH:
                                msg = "新增资源补丁包异常";
                                break;
                                //lib加载
                            case PatchStatus.CODE_LOAD_LIB_UNDEFINED:
                                msg = "未定义异常";
                                break;
                            case PatchStatus.CODE_LOAD_LIB_CPUABIS:
                                msg = "获取primaryCpuAbis异常";
                                break;
                            case PatchStatus.CODE_LOAD_LIB_JSON:
                                msg = "json格式异常";
                                break;
                            case PatchStatus.CODE_LOAD_LIB_LOST:
                                msg = "lib库不完整异常";
                                break;
                            case PatchStatus.CODE_LOAD_LIB_UNZIP:
                                msg = "解压异常";
                                break;
                            case PatchStatus.CODE_LOAD_LIB_INJECT:
                                msg = "注入异常";
                                break;
                            default:
                                break;
                        }

                        if (msgDisplayListener != null) {
                            msgDisplayListener.handle(msg);
                        }
                    }
                }).initialize();
    }

    public static MsgDisplayListener msgDisplayListener = null;
    public static StringBuilder cacheMsg = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
//        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    public interface MsgDisplayListener {
        void handle(String msg);
    }

}
