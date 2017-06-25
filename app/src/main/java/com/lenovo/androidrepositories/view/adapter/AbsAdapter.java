package com.lenovo.androidrepositories.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lenovo on 2017/6/25.
 */

public abstract class AbsAdapter<T> extends BaseAdapter {

    public Context context;
    private List<T> list;
    private int[] layoutId;
    private LayoutInflater inflater;

    public AbsAdapter(Context context, int... layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<T>();
    }

    /**
     * 设置适配器数据
     *
     * @param list
     */
    public void setData(List<T> list) {
        if (list != null) {
            this.list.clear();
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 追加适配器数据
     *
     * @param list
     */
    public void addData(List<T> list) {
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getViewTypeCount() {
        return layoutId.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(getItemViewType(position), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindView(holder, list.get(position), position);
        return convertView;
    }


    public abstract void bindView(ViewHolder holder, T data, int position);

    static class ViewHolder {
        private View itemView;
        private Map<Integer, View> map = new ConcurrentHashMap<Integer, View>();

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

        /**
         * 根据id获取控件
         *
         * @param viewId
         * @param <E>
         * @return
         */
        public <E extends View> E getView(int viewId) {
            if (map.containsKey(viewId)) {
                return (E) map.get(viewId);
            } else {
                View view = itemView.findViewById(viewId);
                map.put(viewId, view);
                return (E) view;
            }
        }

        public View getItemView() {
            return itemView;
        }


    }


}
