package com.lenovo.androidrepositories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by lenovo on 2017/6/25.
 */

public class ActivtiyUtil {

    public  static  void switchActivity(Context context,Class<?> activityClass){
        Intent intent=new Intent(context,activityClass);
        context.startActivity(intent);
    }

}
