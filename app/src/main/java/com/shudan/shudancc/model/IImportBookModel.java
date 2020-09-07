//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc.model;

import com.shudan.shudancc.bean.LocBookShelfBean;
import java.io.File;
import io.reactivex.Observable;

public interface IImportBookModel {

    Observable<LocBookShelfBean> importBook(File book);
}
