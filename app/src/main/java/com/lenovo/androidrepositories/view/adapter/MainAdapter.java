package com.lenovo.androidrepositories.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.model.entity.MainBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lenovo on 2017/6/25.
 */

public class MainAdapter extends AbsRecycleAdapter<MainBean> {


    public MainAdapter(Context context) {
        super(context, R.layout.item_main);
    }


    @Override
    public void bindView(ViewHolder holder, MainBean dataBean, int position) {
        TextView textView = holder.getView(R.id.item_tv_title);
        textView.setText(dataBean.getTitle());
    }
}
