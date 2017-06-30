package com.lenovo.androidrepositories.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.util.ToastUtil;
import com.lenovo.androidrepositories.view.customview.SlideBackLayout;
import com.lenovo.androidrepositories.view.customview.SlideBackView;

/**
 * Created by jyjs on 2017/6/28.
 */

public class SlideBackTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideback);
        SlideBackLayout slideBackLayout = new SlideBackLayout(this);
        slideBackLayout.bind();
//        SlideBackView slideBackView = new SlideBackView(this);


    }

    public void onClick(View v) {
        ToastUtil.showMessage(v.getId() + "");
    }


}
