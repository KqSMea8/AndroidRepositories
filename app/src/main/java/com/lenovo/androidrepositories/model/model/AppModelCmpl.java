package com.lenovo.androidrepositories.model.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.lenovo.androidrepositories.model.entity.AppBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyjs on 2017/6/26.
 */

public class AppModelCmpl implements AppModelImp {

    private AppModelListener appModelListener;
    private Context context;

    public AppModelCmpl(Context context, AppModelListener appModelListener) {
        this.context = context;
        this.appModelListener = appModelListener;
    }

    @Override
    public void getAppList() {
        PackageManager packageManager = context.getPackageManager();
        List<AppBean> list = new ArrayList<AppBean>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        Long s = System.currentTimeMillis();
        List<ResolveInfo> applicationInfos = packageManager.queryIntentActivities(mainIntent, 0);
        Long b = System.currentTimeMillis();
        for (ResolveInfo info : applicationInfos) {
            AppBean appInfo = getAppInfo(info, packageManager);
            if (appInfo != null) {
                list.add(appInfo);
            }
        }


    }
    /**
     * 读取一个可打开的应用信息
     *
     * @param info
     * @param packageManager
     * @return
     */
    private AppInfo getAppInfo(ResolveInfo info, PackageManager packageManager) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(info.activityInfo.packageName, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return null;
        }
        AppInfo appInfo = new AppInfo();
        appInfo.setAppName(info.loadLabel(packageManager).toString());
        appInfo.setPackageName(info.activityInfo.packageName);
        appInfo.setAppLogo(EncodedUtil.DrawableToBase64(info.loadIcon(packageManager)));
        appInfo.setVersionCode(packageInfo.versionCode);
        appInfo.setVersionName(packageInfo.versionName);
        AppInfo info2 = new Select().from(AppInfo.class).where("packageName= ?", info.activityInfo.packageName)
                .executeSingle();
        if (info2 == null) {
            appInfo.setDeskTop(0);
        } else {
            appInfo.setDeskTop(info2.isDeskTop());
        }
        new Delete().from(AppInfo.class).where("packageName= ?", info.activityInfo.packageName).execute();
        appInfo.save();
        return appInfo;
    }


}
