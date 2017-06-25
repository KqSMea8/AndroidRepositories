package com.lenovo.androidrepositories.model.model;

import com.lenovo.androidrepositories.model.entity.MainBean;

import java.util.List;

/**
 * Created by lenovo on 2017/6/25.
 */

public interface MainModelListener {

    void onSucessful(List<MainBean> list);
    void onError(Exception e);
}
