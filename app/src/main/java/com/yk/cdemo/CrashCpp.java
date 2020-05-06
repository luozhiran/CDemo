package com.yk.cdemo;

import android.content.Context;
import android.os.Environment;

import com.sample.breakpad.BreakpadInit;

import java.io.File;

public class CrashCpp {

    public static CrashCpp cpp() {
        return Holder.holder;
    }

    private static class Holder {
        private static final CrashCpp holder = new CrashCpp();
    }

    private File externalReportPath;

    /**
     * 一般来说，crash捕获初始化都会放到Application中，这里主要是为了大家有机会可以把崩溃文件输出到sdcard中
     * 做进一步的分析
     */
    private void initBreakPad(Context context) {
        if (externalReportPath == null) {
            externalReportPath = new File(context.getFilesDir(), "crashDump");
            if (!externalReportPath.exists()) {
                externalReportPath.mkdirs();
            }
        }
        BreakpadInit.initBreakpad(externalReportPath.getAbsolutePath());
    }


    public void initExternalReportPath(Context context) {
        externalReportPath = new File(Environment.getExternalStorageDirectory(), "crashDump");
        if (!externalReportPath.exists()) {
            externalReportPath.mkdirs();
        }
        initBreakPad(context);
    }

}
