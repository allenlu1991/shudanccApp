//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc.presenter;

import com.shudan.basemvplib.IPresenter;

public interface IMainPresenter extends IPresenter{
    void queryBookShelf(Boolean needRefresh);
    void queryAppCommonData(Boolean needRefresh);
    void saveAppCommonData(String appCommonKey, String appCommonValue);
}
