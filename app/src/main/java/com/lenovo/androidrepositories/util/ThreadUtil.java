package com.lenovo.androidrepositories.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jyjs on 2017/6/27.
 */

public class ThreadUtil {

    private static LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque(16);
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 5, TimeUnit.SECONDS, linkedBlockingDeque);

    public static void run(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

}
