package com.meizu.mzhotfix;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

/**
 * author : Samuel
 * e-mail : xingtai.wei@xjmz.com
 * time   : 2025/3/11 上午10:04
 * desc   :
 */
public class OriginActivity extends AppCompatActivity {

    private final static String TAG = "MzHotFix";
    private final static String SUB_TAG = "DexFileActivity-> ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin);
        Log.i(TAG, SUB_TAG + "onCreate");
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(R.string.label_origin);
        }

        TextView tvFun = findViewById(R.id.tv_fun);
        Button btnRepair = findViewById(R.id.btn_repair);

        tvFun.setText(StrUtil.INSTANCE.getRepairMethod());

        btnRepair.setOnClickListener(v -> {
            File file = new File(getFilesDir(), "patch/patch.dex");
            if (file.exists()) {
                Toast.makeText(this, "已完成热更新！", Toast.LENGTH_SHORT).show();
            } else {
                FileUtil.INSTANCE.copyAssetFileToPrivateDir(this,"patch.dex");
//                HotFixUtil.INSTANCE.inject(getClassLoader(), file);
                Toast.makeText(this, "热更新成功, 请重启应用！", Toast.LENGTH_SHORT).show();
            }
        });

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

