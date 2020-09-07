//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc.presenter;

import com.shudan.basemvplib.IPresenter;

import java.util.LinkedHashMap;

public interface ILibraryPresenter extends IPresenter {

    LinkedHashMap<String, String> getKinds();

    void getLibraryData();
}
