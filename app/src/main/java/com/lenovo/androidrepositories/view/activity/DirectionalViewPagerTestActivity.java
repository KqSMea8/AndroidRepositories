package com.lenovo.androidrepositories.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.model.cmpl.DirectionViewpagerNodelCmpl;
import com.lenovo.androidrepositories.model.impl.DirectionViewpagerModelImpl;
import com.lenovo.androidrepositories.model.listener.OnModelCmplListener;
import com.lenovo.androidrepositories.util.ToastUtil;
import com.lenovo.androidrepositories.view.adapter.DirectiViewPagerAdapter;
import com.lenovo.androidrepositories.view.customview.DirectionalViewPager;

import java.util.List;

/**
 * Created by jyjs on 2017/6/28.
 */

public class DirectionalViewPagerTestActivity extends AppCompatActivity implements OnModelCmplListener<Integer> {
    private DirectiViewPagerAdapter pagerAdapter;
    private DirectionViewpagerModelImpl directionViewpagerModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directional_viewpager_test);
        initView();
        loadData();
    }

    private void initView() {
        DirectionalViewPager viewPager = (DirectionalViewPager) findViewById(R.id.dvp_vierwpager);
        pagerAdapter = new DirectiViewPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
    }

    private void loadData() {
        directionViewpagerModel = new DirectionViewpagerNodelCmpl(this, this);
        directionViewpagerModel.getImageRes(R.array.img_array);
    }

    @Override
    public void onSucessful(List<Integer> list) {
        pagerAdapter.setList(list);
    }

    @Override
    public void onError(Exception e) {
        ToastUtil.showMessage(e.getMessage());
    }
}
