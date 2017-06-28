package com.lenovo.androidrepositories.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.view.customview.SlideBackLayout;

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
    }
}
