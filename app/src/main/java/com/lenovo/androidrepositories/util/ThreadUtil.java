package com.lenovo.androidrepositories.util;

import android.util.Log;

import com.lenovo.androidrepositories.asytask.AbsRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by jyjs on 2017/6/27.
 */

public class ThreadUtil {

    private static Map<String, Future<?>> futures = new ConcurrentHashMap<String, Future<?>>(32);
    private static LinkedBlockingQueue<Runnable> linkedBlockingDeque = new LinkedBlockingQueue(32);
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 5, TimeUnit.SECONDS, linkedBlockingDeque);


    public static void execute(final AbsRunnable runnable) {
        if (!futures.containsKey(runnable.getTag())) {
            runnable.setOnTaskComplete(onTaskComplete);
            Future<?> future = threadPoolExecutor.submit(runnable);
            futures.put(runnable.getTag(), future);
        }
    }

    /**
     * 结束任务
     *
     * @param tag 任务tag
     */
    public static boolean stop(String tag) {
        if (futures.containsKey(tag)) {
            Future future = futures.get(tag);
            if (future != null && (!future.isCancelled() || !future.isDone())) {
                future.cancel(true);
                return true;
            }
        }
        return false;
    }


    /**
     * 结束所有任务
     * # 正在执行的任务
     */
    public void stopAll() {
        for (String tag : futures.keySet()) {
            stop(tag);
        }
    }

    public boolean isRunning(String tag) {

        if (futures.containsKey(tag)) {
            Future future = futures.get(tag);

            return future.isCancelled() && future.isDone();
        } else {
            return false;
        }
    }

    private static AbsRunnable.OnTaskComplete onTaskComplete = new AbsRunnable.OnTaskComplete() {
        @Override
        public void onTaskComplete(AbsRunnable runnable) {
            Future future = futures.get(runnable.getTag());

            Log.e("print", "onTaskComplete: " + future.isCancelled());
            futures.remove(runnable.getTag());
        }
    };


}
