package com.lenovo.androidrepositories;

import android.app.Application;
import android.content.Context;

import com.lenovo.androidrepositories.util.log.Logs;
import com.lenovo.androidrepositories.util.log.MyUncauhhtExceptionHandler;

/**
 * Created by jyjs on 2017/6/26.
 */

public class AppApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Thread.setDefaultUncaughtExceptionHandler(new MyUncauhhtExceptionHandler());
        Logs.initLog(context);
    }

    public static AppApplication getInstance() {
        return (AppApplication) context;
    }


}