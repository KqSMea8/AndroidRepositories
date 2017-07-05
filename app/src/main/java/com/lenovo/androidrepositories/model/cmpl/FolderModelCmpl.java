package com.lenovo.androidrepositories.model.cmpl;


import android.os.Handler;

import com.lenovo.androidrepositories.model.entity.MainBean;
import com.lenovo.androidrepositories.model.impl.FolderModelImpl;
import com.lenovo.androidrepositories.model.listener.OnFolderListener;
import com.lenovo.androidrepositories.util.FolderUtil;
import com.lenovo.androidrepositories.asytask.AbsRunnable;
import com.lenovo.androidrepositories.util.ThreadUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jyjs on 2017/6/27.
 */

public class FolderModelCmpl implements FolderModelImpl {
    private OnFolderListener folderListener;
    private Handler handler = new Handler();

    public FolderModelCmpl(OnFolderListener folderListener) {
        this.folderListener = folderListener;
    }


    @Override
    public void copy(final String srcPath, final String desPath) {
        ThreadUtil.execute(new AbsRunnable("FolderModelCmpl-copy") {
            @Override
            public void execute() {
                try {
                    FolderUtil.copyFolder(new File(srcPath), new File(desPath));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (folderListener != null) {
                                folderListener.onSucessful();
                            }
                        }
                    });

                } catch (final Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (folderListener != null) {
                                folderListener.onError(e);
                            }
                        }
                    });

                }
            }
        });

    }

    @Override
    public void delete(final String desPath) {
        ThreadUtil.execute(new AbsRunnable("FolderModelCmpl-delete") {
            @Override
            public void execute() {
                try {
                    FolderUtil.deleteFolder(new File(desPath));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (folderListener != null) {
                                folderListener.onSucessful();
                            }
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (folderListener != null) {
                                folderListener.onError(e);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void show(final String desPath) {
        ThreadUtil.execute(new AbsRunnable("FolderModelCmpl-show") {
            @Override
            public void execute() {
                final List<MainBean> list = new ArrayList<MainBean>();
                try {
                    File file = new File(desPath);
                    if (!file.exists()) {
                        return;
                    }
                    String[] strings = file.list();
                    for (String str : strings) {
                        MainBean mainBean = new MainBean();
                        mainBean.setTitle(str);
                        list.add(mainBean);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (folderListener != null) {
                                folderListener.onError(e);
                            }
                        }
                    });
                } finally {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (folderListener != null) {
                                folderListener.onShow(list);
                            }
                        }
                    });
                }

            }
        });
    }


}
