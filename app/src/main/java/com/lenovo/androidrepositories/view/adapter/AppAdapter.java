package com.lenovo.androidrepositories.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.model.entity.AppBean;


/**
 * Created by jyjs on 2017/6/26.
 */

public class AppAdapter extends AbsRecycleAdapter<AppBean> {
    public AppAdapter(Context context) {
        super(context, R.layout.item_applist);
    }

    @Override
    public void bindView(ViewHolder holder, AppBean dataBean, int position) {
        ImageView imageView = holder.getView(R.id.iv_img);
        imageView.setImageDrawable(list.get(position).getDrawable());
        TextView textView = holder.getView(R.id.tv_name);
        textView.setText(list.get(position).getTitle());
    }
}
