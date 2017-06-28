package com.lenovo.androidrepositories.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.model.cmpl.ZipModelCmpl;
import com.lenovo.androidrepositories.model.entity.MainBean;
import com.lenovo.androidrepositories.model.impl.ZipModelImpl;
import com.lenovo.androidrepositories.model.listener.OnZipListener;
import com.lenovo.androidrepositories.util.ToastUtil;
import com.lenovo.androidrepositories.view.adapter.MainAdapter;

import java.util.List;


/**
 * Created by jyjs on 2017/6/27.
 */

public class ZipTestActivity extends AppCompatActivity implements View.OnClickListener, OnZipListener {

    private MainAdapter mainAdapter;
    private ZipModelImpl zipModelCmpl;
    private Toolbar toolBar;
    private FloatingActionButton fabZip, fabUnzip;
    private String srcPath = Environment.getExternalStorageDirectory().getPath() + "/Image/";
    private String zipPath = Environment.getExternalStorageDirectory().getPath() + "/img/image.zip";
    private String unzipPath = Environment.getExternalStorageDirectory().getPath() + "/unzip/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_test);
        initView();
        loadData();
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mainAdapter = new MainAdapter(this);
        recyclerView.setAdapter(mainAdapter);
        toolBar = (Toolbar) findViewById(R.id.tl_bar);
        setSupportActionBar(toolBar);

        fabZip = (FloatingActionButton) findViewById(R.id.fab_zip);
        fabZip.setOnClickListener(this);
        fabUnzip = (FloatingActionButton) findViewById(R.id.fab_unzip);
        fabUnzip.setOnClickListener(this);
    }

    private void loadData() {
        zipModelCmpl = new ZipModelCmpl(this);
        toolBar.setTitle(srcPath);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_zip:
                toolBar.setTitle(srcPath);
                zipModelCmpl.zip(srcPath, zipPath);
                break;
            case R.id.fab_unzip:
                toolBar.setTitle(unzipPath);
                zipModelCmpl.unZip(zipPath, unzipPath);
                break;
        }
    }

    @Override
    public void onSucessful(List<MainBean> list) {
        mainAdapter.setData(list);
    }

    @Override
    public void onError(Exception e) {
        ToastUtil.showMessage(e.getMessage());
    }
}
