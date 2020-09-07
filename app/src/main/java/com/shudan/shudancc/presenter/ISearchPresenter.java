//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc.presenter;

import com.shudan.basemvplib.IPresenter;
import com.shudan.shudancc.bean.SearchBookBean;

public interface ISearchPresenter extends IPresenter {

    Boolean getHasSearch();

    void setHasSearch(Boolean hasSearch);

    void insertSearchHistory();

    void querySearchHistory();

    void cleanSearchHistory();

    int getPage();

    void initPage();

    void toSearchBooks(String key,Boolean fromError);

    void addBookToShelf(final SearchBookBean searchBookBean);

    Boolean getInput();

    void setInput(Boolean input);
}
