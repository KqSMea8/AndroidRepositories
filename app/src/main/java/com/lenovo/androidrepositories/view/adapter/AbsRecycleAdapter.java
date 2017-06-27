package com.lenovo.androidrepositories.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jyjs on 2017/6/27.
 */

public abstract class AbsRecycleAdapter<T> extends RecyclerView.Adapter<AbsRecycleAdapter.ViewHolder> {
    private static AbsRecycleAdapter.OnRecyclerViewItemClickListener onItemClickListener;
    protected List<T> list;
    protected Context context;
    private int[] layoutId;
    private LayoutInflater inflater;

    public AbsRecycleAdapter(Context context, int... layoutId) {
        this.context = context;
        list = new ArrayList<T>();
        this.layoutId = layoutId;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<T> list) {
        if (list != null) {
            this.list.clear();
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<T> list) {
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public AbsRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(layoutId[viewType], parent, false));
    }

    @Override
    public void onBindViewHolder(AbsRecycleAdapter.ViewHolder holder, int position) {
        bindView(holder, list.get(position), position);
    }


    public abstract void bindView(AbsRecycleAdapter.ViewHolder holder, T dataBean, int position);


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private Map<Integer, View> map = new ConcurrentHashMap<Integer, View>();

        public ViewHolder(View itemView) {
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
                    onItemClickListener.onItemClickListener(v, getAdapterPosition());
                }
            }
        };
    }

    public void setOnItemClickListener(AbsRecycleAdapter.OnRecyclerViewItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClickListener(View itemView, int position);
    }

}
