package com.lenovo.androidrepositories.model.entity;

/**
 * Created by lenovo on 2017/6/25.
 */

public class MainBean {

    private String title;
    private int resId;

    public MainBean() {
    }

    public MainBean(String title, int resId) {
        this.title = title;
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
