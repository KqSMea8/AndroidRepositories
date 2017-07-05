package com.lenovo.androidrepositories.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.asytask.AbsRunnable;
import com.lenovo.androidrepositories.util.ThreadUtil;


/**
 * Created by jyjs on 2017/7/5.
 */

public class ThreadTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test);
        initView();
    }

    private void initView() {

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                ThreadUtil.execute(new AbsRunnable("tag1") {

                    @Override
                    public void execute() {
                        try {
                            while (true) {
                                Log.e("print", "execute: " + Thread.currentThread().toString());
                                Thread.sleep(200);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;
            case R.id.btn_stop:
                ThreadUtil.stop("tag1");
                break;
        }
    }


}
