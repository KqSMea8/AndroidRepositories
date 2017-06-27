package com.lenovo.androidrepositories.model.listener;

import com.lenovo.androidrepositories.model.entity.MainBean;

import java.util.List;

/**
 * Created by jyjs on 2017/6/27.
 */

public interface OnZipListener {

    void onSucessful(List<MainBean> list);

    void onError(Exception e);

}
