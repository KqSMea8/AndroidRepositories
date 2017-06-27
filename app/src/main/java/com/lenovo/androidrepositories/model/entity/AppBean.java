package com.lenovo.androidrepositories.model.entity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by jyjs on 2017/6/26.
 */

public class AppBean {
    private String title;
    private BitmapDrawable drawable;
    private String packageName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(BitmapDrawable drawable) {
        this.drawable = drawable;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
