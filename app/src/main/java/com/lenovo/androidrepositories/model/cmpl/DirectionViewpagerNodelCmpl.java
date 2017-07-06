package com.lenovo.androidrepositories.model.cmpl;

import android.content.Context;
import android.content.res.TypedArray;

import com.lenovo.androidrepositories.model.impl.DirectionViewpagerModelImpl;
import com.lenovo.androidrepositories.model.listener.OnModelCmplListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyjs on 2017/6/28.
 */

public class DirectionViewpagerNodelCmpl implements DirectionViewpagerModelImpl {

    private Context context;
    private OnModelCmplListener cmplListener;


    public DirectionViewpagerNodelCmpl(Context context, OnModelCmplListener<Integer> cmplListener) {
        this.context = context;
        this.cmplListener = cmplListener;
    }

    @Override
    public void getImageRes(int imageResArray) {
        List<Integer> list = new ArrayList<>();
        try {
            TypedArray typedArray = context.getResources().obtainTypedArray(imageResArray);
            for (int i = 0; i < typedArray.length(); i++) {
                list.add(typedArray.getResourceId(i, -1));
            }
            typedArray.recycle();
            if (cmplListener != null) {
                cmplListener.onSucessful(list);
            }
        } catch (Exception e) {
            if (cmplListener != null) {
                cmplListener.onError(e);
            }
        }

    }
}
