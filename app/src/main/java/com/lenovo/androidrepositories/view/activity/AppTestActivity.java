package com.lenovo.androidrepositories.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.model.cmpl.AppModelCmpl;
import com.lenovo.androidrepositories.model.entity.AppBean;
import com.lenovo.androidrepositories.model.impl.AppModelImp;
import com.lenovo.androidrepositories.model.listener.OnModelCmplListener;
import com.lenovo.androidrepositories.util.PackageUtil;
import com.lenovo.androidrepositories.util.ToastUtil;
import com.lenovo.androidrepositories.view.adapter.AbsRecycleAdapter;
import com.lenovo.androidrepositories.view.adapter.AppAdapter;

import java.util.List;

/**
 * Created by jyjs on 2017/6/26.
 */

public class AppTestActivity extends AppCompatActivity implements AbsRecycleAdapter.OnRecyclerViewItemClickListener, OnModelCmplListener {

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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        appAdapter = new AppAdapter(this);
        recyclerView.setAdapter(appAdapter);
        appAdapter.setOnItemClickListener(this);
    }

    private void loadData() {
        appModelImp = new AppModelCmpl(this, this);
        appModelImp.getAppList();
    }

    @Override
    public void onItemClickListener(View itemView, int position) {
        AppBean appBean = appAdapter.getList().get(position);
        PackageUtil.startApp(appBean.getPackageName());
    }


    @Override
    public void onSucessful(List list) {
        appAdapter.setData(list);
    }

    @Override
    public void onError(Exception e) {
        ToastUtil.showMessage(e.getMessage());
    }
}
