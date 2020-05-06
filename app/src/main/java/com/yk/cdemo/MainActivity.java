package com.yk.cdemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private final String[] mPre = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 100;
    public static final String TAG = "MainActivity";

    static {
        System.loadLibrary("native-lib");
    }

    private TextView mTv;
    JniUtils jniUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.sample_text);
        jniUtils = new JniUtils();
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jniUtils.add();
            }
        });
        requestPermission();
    }


    @AfterPermissionGranted(WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
    private void requestPermission() {
        if (EasyPermissions.hasPermissions(this, mPre)) {
            CrashCpp.cpp().initExternalReportPath(getApplicationContext());
        } else {
            PermissionRequest request = new PermissionRequest.Builder(this, WRITE_EXTERNAL_STORAGE_REQUEST_CODE, mPre)
                    .setRationale("需要开启你手机的相关权限")
                    .setPositiveButtonText("确定")
                    .setNegativeButtonText("取消").build();
            EasyPermissions.requestPermissions(request);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(this, mPre)) {
                Toast.makeText(this, "获取权限成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
