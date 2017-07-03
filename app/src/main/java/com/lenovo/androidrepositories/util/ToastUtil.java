package com.lenovo.androidrepositories.util;

import android.widget.Toast;

import com.lenovo.androidrepositories.AppApplication;

/**
 * Created by jyjs on 2017/6/26.
 */

public class ToastUtil {

    public static void showMessage(String message) {
        Toast.makeText(AppApplication.getInstance(), message + "", Toast.LENGTH_SHORT).show();
    }


}
