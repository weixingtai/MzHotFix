package com.meizu.mzhotfix;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DexFileActivity extends AppCompatActivity {

    private final static String TAG = "MzHotFix";
    private final static String SUB_TAG = "DexFileActivity-> ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex_file);
        Log.i(TAG, SUB_TAG + "onCreate");
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(R.string.repair_dex);
        }

        TextView tvFun = findViewById(R.id.tv_fun);
        TextView tvAnnotation = findViewById(R.id.tv_annotation);
        TextView tvObjFiled = findViewById(R.id.tv_obj_filed);
        TextView tvObjMethod = findViewById(R.id.tv_obj_method);

        tvFun.setText(DexFileManager.getRepairMethod());
        tvAnnotation.setText(DexFileManager.getRepairAnnotation());
        tvObjFiled.setText(DexFileManager.getRepairObjField());
        tvObjMethod.setText(DexFileManager.getRepairObjMethod());

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
