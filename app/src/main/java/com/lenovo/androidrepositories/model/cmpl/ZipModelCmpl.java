package com.lenovo.androidrepositories.model.cmpl;

import android.os.Handler;

import com.lenovo.androidrepositories.model.entity.MainBean;
import com.lenovo.androidrepositories.model.impl.ZipModelImpl;
import com.lenovo.androidrepositories.model.listener.OnZipListener;
import com.lenovo.androidrepositories.util.ThreadUtil;
import com.lenovo.androidrepositories.util.ZipUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyjs on 2017/6/27.
 */

public class ZipModelCmpl implements ZipModelImpl {
    private OnZipListener onZipListener;
    private Handler handler = new Handler();

    public ZipModelCmpl(OnZipListener onZipListener) {
        this.onZipListener = onZipListener;
    }

    @Override
    public void zip(final String srcPath, final String zipPath) {
        ThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                ZipUtil.zip(srcPath, zipPath);
                final List<MainBean> beanList = show(zipPath);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onZipListener != null) {
                            onZipListener.onSucessful(beanList);
                        }

                    }
                });
            }
        });

    }

    @Override
    public void unZip(final String zipPath, final String unPath) {

        ThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                ZipUtil.unZip(zipPath, unPath);
                final List<MainBean> beanList = show(unPath);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onZipListener != null) {
                            onZipListener.onSucessful(beanList);
                        }
                    }
                });
            }
        });
    }


    public List<MainBean> show(String path) {
        List<MainBean> list = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            return list;
        }
        file = file.getParentFile();
        String[] strings = file.list();
        for (String str : strings) {
            MainBean mainBean = new MainBean();
            mainBean.setTitle(str);
            list.add(mainBean);
        }
        return list;
    }

}
