package com.lenovo.androidrepositories.model.listener;

import com.lenovo.androidrepositories.model.entity.MainBean;

import java.util.List;

/**
 * Created by lenovo on 2017/6/25.
 */

public interface OnModelCmplListener<T>{

    void onSucessful(List<T> list);
    void onError(Exception e);
}
