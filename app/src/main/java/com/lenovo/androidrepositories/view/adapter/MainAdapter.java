package com.lenovo.androidrepositories.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenovo.androidrepositories.R;
import com.lenovo.androidrepositories.model.entity.MainBean;
import com.lenovo.androidrepositories.view.customview.DragLayoutTestActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lenovo on 2017/6/25.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    public Context context;
private  LayoutInflater inflater;
    private int layoutId[];
    private static List<MainBean> list;
    private static OnRecyclerViewItemClickListener onItemClickListener;

    public MainAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<MainBean>();
        layoutId = new int[]{R.layout.item_main};
    }


    public void setData(List<MainBean> list) {
        if (list != null) {
            this.list.clear();
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<MainBean> list) {
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(inflater.inflate(layoutId[viewType], parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        MainBean mainBean = list.get(position);
        TextView textView = holder.getView(R.id.item_tv_title);
        textView.setText(mainBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {

        private Map<Integer, View> map = new ConcurrentHashMap<Integer, View>();

        public MainViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(onClickListener);
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
        void onItemClickListener(View itemView, MainBean mainBean, int position);
    }

}
