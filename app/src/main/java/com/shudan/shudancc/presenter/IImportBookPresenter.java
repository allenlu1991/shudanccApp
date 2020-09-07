//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc.presenter;

import com.shudan.basemvplib.IPresenter;

import java.io.File;
import java.util.List;

public interface IImportBookPresenter extends IPresenter {
    void searchLocationBook();

    void importBooks(List<File> books);
}
