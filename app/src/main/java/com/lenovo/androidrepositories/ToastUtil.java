package com.lenovo.androidrepositories;

import android.widget.Toast;

/**
 * Created by jyjs on 2017/6/26.
 */

public class ToastUtil {

    public static void showMessage(String message) {
        Toast.makeText(AppApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }


}
