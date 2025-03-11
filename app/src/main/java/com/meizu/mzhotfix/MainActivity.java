package com.meizu.mzhotfix;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.taobao.sophix.SophixManager;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MzHotFix";
    private final static String SUB_TAG = "MainActivity-> ";

    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 0;

    private RelativeLayout rlOrigin;
    private RelativeLayout rlSophix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, SUB_TAG + "onCreate");
        rlOrigin = findViewById(R.id.rl_origin);
        rlSophix = findViewById(R.id.rl_sophix);
        if (Build.VERSION.SDK_INT >= 23) {
            Log.i(TAG, SUB_TAG + "sdk version >= 23");
            requestExternalStoragePermission();
        }
    }

    /**
     * 如果本地补丁放在了外部存储卡中, 6.0以上需要申请读外部存储卡权限才能够使用. 应用内部存储则不受影响
     */

    private void requestExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE_PERMISSION);
        } else {
            rlOrigin.setOnClickListener(v -> startActivity(new Intent(this, OriginActivity.class)));
            rlSophix.setOnClickListener(v -> startActivity(new Intent(this, SophixActivity.class)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                rlOrigin.setOnClickListener(v -> startActivity(new Intent(this, OriginActivity.class)));
                rlSophix.setOnClickListener(v -> startActivity(new Intent(this, SophixActivity.class)));
            } else {
                Log.i(TAG, SUB_TAG + "request permission");
                rlOrigin.setOnClickListener(v -> Toast.makeText(this, "请先授予读取存储权限！",Toast.LENGTH_SHORT).show());
                rlSophix.setOnClickListener(v -> Toast.makeText(this, "请先授予读取存储权限！",Toast.LENGTH_SHORT).show());
            }
        }
    }

}
