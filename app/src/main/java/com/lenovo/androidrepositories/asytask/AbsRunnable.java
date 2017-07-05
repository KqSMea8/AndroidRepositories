package com.lenovo.androidrepositories.asytask;

/**
 * Created by jyjs on 2017/7/5.
 */

public abstract class AbsRunnable implements Runnable {

    private OnTaskComplete onTaskComplete;
    private String tag;

    public String getTag() {
        return tag;
    }


    public AbsRunnable(String tag) {
        if (tag == null) {
            throw new RuntimeException(" error: tag = null");
        }
        this.tag = tag;
    }

    @Override
    public void run() {
        execute();
        if (onTaskComplete != null) {
            onTaskComplete.onTaskComplete(this);
        }
    }

    public abstract void execute();


    public void setOnTaskComplete(OnTaskComplete onTaskComplete) {
        this.onTaskComplete = onTaskComplete;
    }

    public interface OnTaskComplete {
        void onTaskComplete(AbsRunnable runnable);
    }
}
