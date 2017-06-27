package com.lenovo.androidrepositories.model.cmpl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import com.lenovo.androidrepositories.model.entity.AppBean;
import com.lenovo.androidrepositories.model.impl.AppModelImp;
import com.lenovo.androidrepositories.model.listener.OnModelCmplListener;
import com.lenovo.androidrepositories.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jyjs on 2017/6/26.
 */

public class AppModelCmpl implements AppModelImp {

    private Context context;
    private OnModelCmplListener<AppBean> appModelListener;
    private static Handler handler = new Handler();
    private String packageName;

    public AppModelCmpl(Context context, OnModelCmplListener<AppBean> appModelListener) {
        this.context = context;
        this.appModelListener = appModelListener;
    }

    @Override
    public void getAppList() {
        final PackageManager packageManager = context.getPackageManager();
        packageName = context.getPackageName();
        ThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                final List<AppBean> list = new ArrayList<AppBean>();
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                try {
                    List<ResolveInfo> applicationInfos = packageManager.queryIntentActivities(mainIntent, 0);
                    for (ResolveInfo info : applicationInfos) {
                        AppBean appInfo = getAppInfo(info, packageManager);
                        if (appInfo != null) {
                            list.add(appInfo);
                        }
                    }
                    if (appModelListener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                appModelListener.onSucessful(list);
                            }
                        });

                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    if (appModelListener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                appModelListener.onError(e);
                            }
                        });

                    }
                }
            }
        });

    }

    /**
     * 读取一个可打开的应用信息
     *
     * @param info
     * @param packageManager
     * @return
     */
    private AppBean getAppInfo(ResolveInfo info, PackageManager packageManager) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(info.activityInfo.packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return null;
        }
        if (info.activityInfo.packageName.equals(packageName)) {
            return null;
        }
        AppBean appBean = new AppBean();
        appBean.setTitle(info.loadLabel(packageManager).toString());
        appBean.setPackageName(info.activityInfo.packageName);
        appBean.setDrawable((BitmapDrawable) info.loadIcon(packageManager));
        return appBean;
    }


}
