package com.lenovo.androidrepositories.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.model.cmpl.FolderModelCmpl;
import com.lenovo.androidrepositories.model.entity.MainBean;
import com.lenovo.androidrepositories.model.impl.FolderModelImpl;
import com.lenovo.androidrepositories.model.listener.OnFolderListener;
import com.lenovo.androidrepositories.util.ToastUtil;
import com.lenovo.androidrepositories.view.adapter.MainAdapter;

import java.util.List;

/**
 * Created by jyjs on 2017/6/27.
 */

public class FolderTestActivity extends AppCompatActivity implements OnFolderListener {

    private MainAdapter mainAdapter;
    private FolderModelImpl folderModel;
    private String oldPath = Environment.getExternalStorageDirectory().getPath() + "/Image/";
    private String newPath = Environment.getExternalStorageDirectory().getPath() + "/img/";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_test);
        initView();
        loadData();
    }


    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(this);
        recyclerView.setAdapter(mainAdapter);

        FloatingActionButton fabCopy = (FloatingActionButton) findViewById(R.id.fab_copy);
        fabCopy.setOnClickListener(onClickListener);
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab_delete);
        fabDelete.setOnClickListener(onClickListener);
        FloatingActionButton fabShow = (FloatingActionButton) findViewById(R.id.fab_show);
        fabShow.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab_copy:
                    folderModel.copy(oldPath, newPath);
                    break;
                case R.id.fab_delete:
                    folderModel.delete(newPath);
                    break;
                case R.id.fab_show:
                    folderModel.show(newPath);
                    break;

            }
        }
    };

    private void loadData() {
        folderModel = new FolderModelCmpl(this);
    }


    @Override
    public void onSucessful() {
        folderModel.show(newPath);
    }

    @Override
    public void onProgress(String path) {

    }

    @Override
    public void onShow(List<MainBean> list) {
        mainAdapter.setData(list);
    }

    @Override
    public void onError(Exception e) {
        ToastUtil.showMessage(e.getMessage());
    }
}
