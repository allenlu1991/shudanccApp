//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc.presenter;

import android.app.Activity;

import com.shudan.basemvplib.IPresenter;
import com.shudan.shudancc.bean.BookShelfBean;
import com.shudan.shudancc.presenter.impl.ReadBookPresenterImpl;
import com.shudan.shudancc.widget.contentswitchview.BookContentView;

public interface IBookReadPresenter extends IPresenter {

    int getOpen_from();

    BookShelfBean getBookShelf();

    void initContent();

    void loadContent(BookContentView bookContentView,long bookTag, final int chapterIndex, final int page);

    void updateProgress(int chapterIndex, int pageIndex);

    void saveProgress();

    String getChapterTitle(int chapterIndex);

    void setPageLineCount(int pageLineCount);

    void addToShelf(final ReadBookPresenterImpl.OnAddListner addListner);

    Boolean getAdd();

    void initData(Activity activity);

    void openBookFromOther(Activity activity);
}
