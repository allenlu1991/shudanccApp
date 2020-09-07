//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc.presenter;

import com.shudan.basemvplib.IPresenter;
import com.shudan.shudancc.bean.SearchBookBean;

public interface IChoiceBookPresenter extends IPresenter {

    int getPage();

    void initPage();

    void toSearchBooks(String key);

    void addBookToShelf(final SearchBookBean searchBookBean);

    String getTitle();
}