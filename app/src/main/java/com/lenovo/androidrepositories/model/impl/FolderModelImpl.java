package com.lenovo.androidrepositories.model.impl;


/**
 * Created by jyjs on 2017/6/27.
 */

public interface FolderModelImpl {

    void copy(String srcPath, String desPath);

    void delete(String desPath);

    void show(String desPath);
}
