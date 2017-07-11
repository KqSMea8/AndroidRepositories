package com.lenovo.androidrepositories.util.log;


import android.content.Context;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志收集类
 * Created by jyjs on 2017/7/11.
 */

public class Logs {

    private static File path;
    private static StringBuilder stringBuilder;
    private static SimpleDateFormat dateFormat, dateDayFormat;

    private Logs() {
    }

    public static void initLog(Context context) {
        stringBuilder = new StringBuilder();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        dateDayFormat = new SimpleDateFormat("MM-dd" );
        path = context.getExternalFilesDir("" );
    }

    /**
     * 设置日志路径
     * <p>默认日志路径context.getExternalFilesDir("")</p>
     *
     * @param logPath 日志路径
     */
    public static boolean setPath(String logPath) {
        try {
            File file = new File(logPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = file;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("log", "setPath: " + logPath + "  不可用" );
            return false;
        }

    }


    /**
     * 记录程序运行日志
     *
     * @param buf
     */
    public static void d(String buf) {
        Log.d("print", buf);
        File file = new File(path, "/log/" );
        if (!file.exists()) {
            file.mkdirs();
        }
        print(buf, new File(file, "log_" + dateDayFormat.format(new Date()) + ".txt" ));
    }

    /**
     * 记录程序异常日志
     *
     * @param buf 要写入到内容
     */
    public static void e(String buf) {
        Log.e("print", buf);
        File file = new File(path, "/error/" );
        if (!file.exists()) {
            file.mkdirs();
        }
        print(buf, new File(file, "err_" + dateDayFormat.format(new Date()) + ".txt" ));
    }

    /**
     * 写入打印内容
     *
     * @param str  要写入到内容
     * @param file 要写入的文件
     */
    private static synchronized void print(String str, File file) {
        stringBuilder.append(dateFormat.format(new Date()))
                .append("\t" )
                .append(str)
                .append("\n" );
        writer(file);
    }

    /**
     * 写入本地
     *
     * @param file
     */
    private static void writer(File file) {
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file, true));
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clearBuffer();
        }
    }

    /**
     * 清除缓存的log信息
     */
    private static void clearBuffer() {
        stringBuilder.delete(0, stringBuilder.length());
    }
}
