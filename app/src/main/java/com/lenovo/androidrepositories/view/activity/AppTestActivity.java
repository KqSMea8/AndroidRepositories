package com.lenovo.androidrepositories.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.model.entity.AppBean;
import com.lenovo.androidrepositories.model.model.AppModelCmpl;
import com.lenovo.androidrepositories.model.model.AppModelImp;
import com.lenovo.androidrepositories.model.model.AppModelListener;
import com.lenovo.androidrepositories.view.adapter.AppAdapter;

import java.util.List;

/**
 * Created by jyjs on 2017/6/26.
 */

public class AppTestActivity extends AppCompatActivity implements AppAdapter.OnRecyclerViewItemClickListener, AppModelListener {

    private AppAdapter appAdapter;
    private AppModelImp appModelImp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apptest);
        initView();
        loadData();
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        appAdapter = new AppAdapter(this);
        recyclerView.setAdapter(appAdapter);
        appAdapter.setOnItemClickListener(this);
    }

    private void loadData() {
        appModelImp = new AppModelCmpl(this, this);
        appModelImp.getAppList();
    }

    @Override
    public void onItemClickListener(View itemView, AppBean appBean, int position) {

    }

    @Override
    public void onSucessful(List<AppBean> list) {

    }

    @Override
    public void onError(Exception e) {

    }
}
