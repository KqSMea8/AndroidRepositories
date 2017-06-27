package com.lenovo.androidrepositories.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.lenovo.androidrepositories.AppApplication;

import java.io.File;

/**
 * Created by jyjs on 2017/6/27.
 */

public class PackageUtil {

    public static void startApp(String packageName) {
        Context context = AppApplication.getInstance();
        PackageManager pm = context.getPackageManager();
        try {
            Intent intent = pm.getLaunchIntentForPackage(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showMessage("打开应用失败");
        }
    }

    /**
     * 备份模块方法-> 安装软件
     *
     * @param context
     * @param filePath apk文件的路径
     */
    public static boolean installAPKForSDFilePath(Context context,
                                                  String filePath) {

        try {
            if (null == filePath) {
                return false;
            }
            File file2 = new File(filePath);
            if (!file2.exists()) {
                return false;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                return false;
            }
            String cmd = "chmod 777 " + file.getAbsolutePath();
            String cmd2 = "chmod 777 " + file2.getAbsolutePath();
            try {
                Runtime.getRuntime().exec(cmd);
                Runtime.getRuntime().exec(cmd2);
            } catch (Exception e) {

                e.printStackTrace();
                return false;
            }
            try {
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri,
                        "application/vnd.android.package-archive");
                context.startActivity(intent);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
