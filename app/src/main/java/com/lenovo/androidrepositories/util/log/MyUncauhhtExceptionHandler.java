package com.lenovo.androidrepositories.util.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 程序异常捕捉类
 * Created by jyjs on 2017/7/11.
 */

public class MyUncauhhtExceptionHandler implements Thread.UncaughtExceptionHandler {


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            Throwable cause = e;
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            String result = writer.toString();
            Logs.e(result);
            printWriter.close();
            Logs.e(collect(t));
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    public String collect(Thread thread) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("thread_info" ).append("\n" );
        if (thread != null) {
            stringBuilder.append("id=" ).append(thread.getId()).append("\n" );
            stringBuilder.append("name=" ).append(thread.getName()).append("\n" );
            stringBuilder.append("priority=" ).append(thread.getPriority()).append("\n" );
            if (thread.getThreadGroup() != null) {
                stringBuilder.append("groupName= " ).append(thread.getThreadGroup().getName()).append("\n" );
            }
        }
        return stringBuilder.toString();
    }


}
