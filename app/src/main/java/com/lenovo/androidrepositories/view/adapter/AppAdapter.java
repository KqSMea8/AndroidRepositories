package com.lenovo.androidrepositories.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.model.entity.AppBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jyjs on 2017/6/26.
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private int layoutId[];
    private static List<AppBean> list;
    private static OnRecyclerViewItemClickListener onItemClickListener;

    public AppAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<AppBean>();
        layoutId = new int[]{R.layout.item_main};
    }

    public void setData(List<AppBean> list) {
        if (list != null) {
            this.list.clear();
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<AppBean> list) {
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppViewHolder(inflater.inflate(R.layout.item_applist, parent));
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        ImageView imageView = holder.getView(R.id.iv_img);
        imageView.setImageDrawable(list.get(position).getDrawable());
        TextView textView = holder.getView(R.id.tv_name);
        textView.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class AppViewHolder extends RecyclerView.ViewHolder {
        private Map<Integer, View> map = new ConcurrentHashMap<Integer, View>();

        public AppViewHolder(View itemView) {
            super(itemView);
        }

        public <T extends View> T getView(int viewId) {
            if (map.containsKey(viewId)) {
                return (T) map.get(viewId);
            } else {
                View view = itemView.findViewById(viewId);
                map.put(viewId, view);
                return (T) view;
            }
        }

        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(v, list.get(getAdapterPosition()), getAdapterPosition());
                }
            }
        };
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClickListener(View itemView, AppBean appBean, int position);
    }
}
