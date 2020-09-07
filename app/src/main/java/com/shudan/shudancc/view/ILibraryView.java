//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc.view;

import com.shudan.basemvplib.IView;
import com.shudan.shudancc.bean.LibraryBean;

public interface ILibraryView extends IView {

    /**
     * 书城书籍获取成功  更新UI
     * @param library
     */
    void updateUI(LibraryBean library);

    /**
     * 书城数据刷新成功 更新UI
     */
    void finishRefresh();
}
