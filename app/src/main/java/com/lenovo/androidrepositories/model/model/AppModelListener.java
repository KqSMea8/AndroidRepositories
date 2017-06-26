package com.lenovo.androidrepositories.model.model;

import com.lenovo.androidrepositories.model.entity.AppBean;

import java.util.List;

/**
 * Created by jyjs on 2017/6/26.
 */

public interface AppModelListener {

    void onSucessful(List<AppBean> list);

    void onError(Exception e);
}
