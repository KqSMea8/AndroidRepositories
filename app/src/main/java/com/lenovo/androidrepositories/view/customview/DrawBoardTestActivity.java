package com.lenovo.androidrepositories.view.customview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.lenovo.androidrepositories.R;

/**
 * 画板测试activity
 * Created by jyjs on 2017/6/26.
 */

public class DrawBoardTestActivity extends AppCompatActivity {

    private ImageView imageView;
    private DrawBoardView drawBoardView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawboard_test);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_clear).setOnClickListener(onClickListener);
        findViewById(R.id.btn_save).setOnClickListener(onClickListener);
        imageView = (ImageView) findViewById(R.id.iv_img);
        drawBoardView = (DrawBoardView) findViewById(R.id.dbv_drawview);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_clear:
                    drawBoardView.clear();
                    break;
                case R.id.btn_save:
                    imageView.setImageBitmap(drawBoardView.getBitmap());
                    break;
            }
        }
    };


}
