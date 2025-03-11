package com.meizu.mzhotfix;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.taobao.sophix.SophixManager;

/**
 * author : Samuel
 * e-mail : xingtai.wei@xjmz.com
 * time   : 2025/3/11 上午10:04
 * desc   :
 */
public class OriginActivity extends AppCompatActivity {

    private final static String TAG = "MzHotFix";
    private final static String SUB_TAG = "SophixActivity-> ";

    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 0;

    private TextView mStatusTv;
    private String mStatusStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mStatusTv = findViewById(R.id.tv_status);
        updateConsole(SophixStubApplication.cacheMsg.toString());

        if (Build.VERSION.SDK_INT >= 23) {
            requestExternalStoragePermission();
        }
        SophixStubApplication.msgDisplayListener = msg -> runOnUiThread(() -> updateConsole(msg));
    }

    /**
     * 如果本地补丁放在了外部存储卡中, 6.0以上需要申请读外部存储卡权限才能够使用. 应用内部存储则不受影响
     */

    private void requestExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    updateConsole("local external storage patch is invalid as not read external storage permission");
                }
                break;
            default:
        }
    }

    /**
     * 更新监控台的输出信息
     *
     * @param content 更新内容
     */
    private void updateConsole(String content) {
        mStatusStr += content + "\n";
        if (mStatusTv != null) {
            mStatusTv.setText(mStatusStr);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_restest:
                startActivity(new Intent(this, ResourceActivity.class));
                break;
            case R.id.btn_download:
                SophixManager.getInstance().queryAndLoadNewPatch();
                break;
            case R.id.btn_test:
//                DexFixDemo.test_normal(this);
//                DexFixDemo.test_annotation();
//                DexFixDemo.test_addField();
//                DexFixDemo.test_addMethod();
                updateConsole("old apk from java...");
                break;
            case R.id.btn_sotest:
//                SOFileManager.test(this);
                updateConsole("apk from native...");
                break;
            case R.id.btn_kill:
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            case R.id.btn_clean_patch:
                SophixManager.getInstance().cleanPatches();
                updateConsole("clean patches");
                break;
            case R.id.btn_clean_console:
                mStatusStr = "";
                updateConsole("");
                break;
            default:
                break;
        }
    }

}

