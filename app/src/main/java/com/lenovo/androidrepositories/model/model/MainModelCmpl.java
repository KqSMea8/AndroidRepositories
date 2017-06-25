package com.lenovo.androidrepositories.model.model;

import android.content.Context;
import android.content.res.TypedArray;

import com.lenovo.androidrepositories.model.entity.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/6/25.
 */

public class MainModelCmpl implements MainModeImp {

    private Context context;
    private MainModelListener modelListener;

    public MainModelCmpl(Context context, MainModelListener modelListener) {
        this.context = context;
        this.modelListener = modelListener;
    }

    @Override
    public void getData(int resId) {
        List<MainBean> list = new ArrayList<MainBean>();
        try {
            TypedArray typedArray = context.getResources().obtainTypedArray(resId);
            for (int i = 0; i < typedArray.length(); i++) {
                MainBean mainBean = new MainBean();
                mainBean.setTitle(typedArray.getString(i));
                list.add(mainBean);
            }
            typedArray.recycle();
        } catch (Exception e) {
            modelListener.onError(e);
            return;
        }
        if (modelListener != null) {
            modelListener.onSucessful(list);
        }
    }
}
