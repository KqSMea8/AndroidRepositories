package com.lenovo.androidrepositories.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.model.cmpl.MainModelCmpl;
import com.lenovo.androidrepositories.model.impl.MainModeImp;
import com.lenovo.androidrepositories.model.listener.OnModelCmplListener;
import com.lenovo.androidrepositories.util.ToastUtil;
import com.lenovo.androidrepositories.view.adapter.AbsRecycleAdapter;
import com.lenovo.androidrepositories.view.adapter.MainAdapter;

import java.util.List;

/**
 * Created by jyjs on 2017/6/26.
 */

public class DragLayoutTestActivity extends AppCompatActivity implements AbsRecycleAdapter.OnRecyclerViewItemClickListener, OnModelCmplListener {

    private MainAdapter mainAdapter, leftAdapter;
    private MainModeImp mainModelCmpl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draglayout_test);
        initView();
        initData();
    }


    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainAdapter = new MainAdapter(this);
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setOnItemClickListener(this);

        RecyclerView reLeft = (RecyclerView) findViewById(R.id.rv_leftlist);
        reLeft.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        leftAdapter = new MainAdapter(this);
        reLeft.setAdapter(leftAdapter);
        leftAdapter.setOnItemClickListener(this);
    }

    private void initData() {
        mainModelCmpl = new MainModelCmpl(this, this);
        mainModelCmpl.getData(R.array.main_title);
    }

    @Override
    public void onItemClickListener(View itemView, int position) {
        ToastUtil.showMessage(leftAdapter.getList().get(position).getTitle());
    }

    @Override
    public void onSucessful(List list) {
        mainAdapter.setData(list);
        leftAdapter.setData(list);
    }

    @Override
    public void onError(Exception e) {
        ToastUtil.showMessage("解析数据出错");
    }
}
