package com.shudan.basemvplib.impl;

import android.support.annotation.NonNull;
import com.shudan.basemvplib.IPresenter;
import com.shudan.basemvplib.IView;

public abstract class BasePresenterImpl<T extends IView> implements IPresenter{
    protected T mView;

    @Override
    public void attachView(@NonNull IView iView) {
        mView = (T) iView;
    }
}
