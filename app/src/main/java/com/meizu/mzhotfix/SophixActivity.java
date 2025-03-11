package com.meizu.mzhotfix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.taobao.sophix.SophixManager;

/**
 * author : Samuel
 * e-mail : xingtai.wei@xjmz.com
 * time   : 2025/3/11 上午10:04
 * desc   :
 */
public class SophixActivity extends AppCompatActivity {

    private final static String TAG = "MzHotFix";
    private final static String SUB_TAG = "SophixActivity-> ";

    private TextView tvStatus;
    private String strStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sophix);
        Log.i(TAG, SUB_TAG + "onCreate");
        RelativeLayout rlResource = findViewById(R.id.rl_resource);
        RelativeLayout rlDex = findViewById(R.id.rl_dex);
        RelativeLayout rlSo = findViewById(R.id.rl_so);
        tvStatus = findViewById(R.id.tv_status);
        Button btnRepair = findViewById(R.id.btn_repair);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(R.string.label_sophix);
        }

        rlResource.setOnClickListener(v -> {
            startActivity(new Intent(this, ResourceActivity.class));
        });
        rlDex.setOnClickListener(v -> {
            startActivity(new Intent(this, DexFileActivity.class));
        });
        rlSo.setOnClickListener(v -> {
            startActivity(new Intent(this, SoFileActivity.class));
        });
        btnRepair.setOnClickListener(v -> {
            updateConsole("开始拉取补丁...");
            SophixManager.getInstance().queryAndLoadNewPatch();
        });

        SophixStubApplication.msgDisplayListener = msg -> runOnUiThread(() -> updateConsole(msg));
    }

    /**
     * 更新监控台的输出信息
     *
     * @param content 更新内容
     */
    private void updateConsole(String content) {
        strStatus += content + "\n";
        if (tvStatus != null) {
            tvStatus.setText(strStatus);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
