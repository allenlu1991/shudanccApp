//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc.listener;

import com.shudan.shudancc.bean.BookShelfBean;

public interface OnGetChapterListListener {
    public void success(BookShelfBean bookShelfBean);
    public void error();
}
