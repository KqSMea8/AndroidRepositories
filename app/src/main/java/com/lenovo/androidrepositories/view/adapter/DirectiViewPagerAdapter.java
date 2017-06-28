package com.lenovo.androidrepositories.view.adapter;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyjs on 2017/6/28.
 */

public class DirectiViewPagerAdapter extends PagerAdapter {


    private List<Integer> list;

    public DirectiViewPagerAdapter() {
        list = new ArrayList<Integer>();
    }

    public void setList(List<Integer> list) {
        if (list != null) {
            this.list.clear();
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(list.get(position));
        container.addView(imageView);
        return imageView;
    }
}
