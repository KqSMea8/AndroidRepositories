package com.lenovo.androidrepositories;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lenovo.androidrepositories.model.entity.MainBean;
import com.lenovo.androidrepositories.model.model.MainModeImp;
import com.lenovo.androidrepositories.model.model.MainModelCmpl;
import com.lenovo.androidrepositories.model.model.MainModelListener;
import com.lenovo.androidrepositories.view.adapter.MainAdapter;
import com.lenovo.androidrepositories.view.customview.GifViewTestActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainModelListener {
    MainAdapter mainAdapter;
    private MainModeImp mainModeImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        mainAdapter = new MainAdapter(this);
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setOnItemClickListener(itemClickListener);

    }

    private MainAdapter.OnRecyclerViewItemClickListener itemClickListener = new MainAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClickListener(View itemView, MainBean mainBean, int position) {
            switch (position){
                case  0:
                    ActivtiyUtil.switchActivity(MainActivity.this, GifViewTestActivity.class);
                    break;
            }
        }
    };

    private void loadData() {
        mainModeImp = new MainModelCmpl(this, this);
        mainModeImp.getData(R.array.main_title);
    }

    @Override
    public void onSucessful(List<MainBean> list) {
        mainAdapter.setData(list);
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(this, "解析数据出错", Toast.LENGTH_LONG).show();
    }
}
