package com.lenovo.androidrepositories;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lenovo.androidrepositories.model.cmpl.MainModelCmpl;
import com.lenovo.androidrepositories.model.entity.MainBean;
import com.lenovo.androidrepositories.model.impl.MainModeImp;
import com.lenovo.androidrepositories.model.listener.OnModelCmplListener;
import com.lenovo.androidrepositories.util.ActivtiyUtil;
import com.lenovo.androidrepositories.view.activity.AppTestActivity;
import com.lenovo.androidrepositories.view.activity.CustomAnimatorActivity;
import com.lenovo.androidrepositories.view.activity.CustomViewPagerTestActivity;
import com.lenovo.androidrepositories.view.activity.DirectionalViewPagerTestActivity;
import com.lenovo.androidrepositories.view.activity.DragLayoutTestActivity;
import com.lenovo.androidrepositories.view.activity.DrawBoardTestActivity;
import com.lenovo.androidrepositories.view.activity.FolderTestActivity;
import com.lenovo.androidrepositories.view.activity.GifViewTestActivity;
import com.lenovo.androidrepositories.view.activity.SlideBackTestActivity;
import com.lenovo.androidrepositories.view.activity.ThreadTestActivity;
import com.lenovo.androidrepositories.view.activity.ZipTestActivity;
import com.lenovo.androidrepositories.view.adapter.AbsRecycleAdapter;
import com.lenovo.androidrepositories.view.adapter.MainAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnModelCmplListener<MainBean> {
    MainAdapter mainAdapter;
    private MainModeImp mainModeImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        mainAdapter = new MainAdapter(this);
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setOnItemClickListener(itemClickListener);
        CollapsingToolbarLayout toolbarLayouy = (CollapsingToolbarLayout) findViewById(R.id.ctb_tool);
        toolbarLayouy.setCollapsedTitleTextColor(Color.WHITE);
        toolbarLayouy.setExpandedTitleColor(Color.RED);

    }

    /**
     * 加载数据
     */
    private void loadData() {
        mainModeImp = new MainModelCmpl(this, this);
        mainModeImp.getData(R.array.main_title);
        String a = "fs";
        Integer.valueOf(a);
    }

    /**
     * recycleView   点击事件回调
     */
    private MainAdapter.OnRecyclerViewItemClickListener itemClickListener = new AbsRecycleAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClickListener(View itemView, int position) {
            switch (position) {
                case 0:
                    ActivtiyUtil.switchActivity(MainActivity.this, GifViewTestActivity.class);
                    break;
                case 1:
                    ActivtiyUtil.switchActivity(MainActivity.this, DrawBoardTestActivity.class);
                    break;
                case 2:
                    ActivtiyUtil.switchActivity(MainActivity.this, DragLayoutTestActivity.class);
                    break;
                case 3:
                    ActivtiyUtil.switchActivity(MainActivity.this, AppTestActivity.class);
                    break;
                case 4:
                    ActivtiyUtil.switchActivity(MainActivity.this, FolderTestActivity.class);
                    break;
                case 5:
                    ActivtiyUtil.switchActivity(MainActivity.this, ZipTestActivity.class);
                    break;
                case 6:
                    ActivtiyUtil.switchActivity(MainActivity.this, SlideBackTestActivity.class);
                    break;
                case 7:
                    ActivtiyUtil.switchActivity(MainActivity.this, DirectionalViewPagerTestActivity.class);
                    break;
                case 8:
                    ActivtiyUtil.switchActivity(MainActivity.this, CustomViewPagerTestActivity.class);
                    break;
                case 9:
                    ActivtiyUtil.switchActivity(MainActivity.this, CustomAnimatorActivity.class);
                    break;
                case 10:
                    ActivtiyUtil.switchActivity(MainActivity.this, ThreadTestActivity.class);
                    break;
            }
        }
    };

    @Override
    public void onSucessful(List<MainBean> list) {
        mainAdapter.setData(list);
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(this, "解析数据出错", Toast.LENGTH_LONG).show();
    }
}
