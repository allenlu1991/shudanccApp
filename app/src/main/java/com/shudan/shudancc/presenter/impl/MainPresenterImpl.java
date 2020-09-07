//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc.presenter.impl;

import android.support.annotation.NonNull;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.shudan.basemvplib.IView;
import com.shudan.basemvplib.impl.BasePresenterImpl;
import com.shudan.shudancc.base.observer.SimpleObserver;
import com.shudan.shudancc.bean.AppCommonBean;
import com.shudan.shudancc.bean.BookInfoBean;
import com.shudan.shudancc.bean.BookShelfBean;
import com.shudan.shudancc.common.RxBusTag;
import com.shudan.shudancc.dao.AppCommonBeanDao;
import com.shudan.shudancc.dao.BookInfoBeanDao;
import com.shudan.shudancc.dao.BookShelfBeanDao;
import com.shudan.shudancc.dao.ChapterListBeanDao;
import com.shudan.shudancc.dao.DbHelper;
import com.shudan.shudancc.listener.OnGetChapterListListener;
import com.shudan.shudancc.model.impl.WebBookModelImpl;
import com.shudan.shudancc.presenter.IMainPresenter;
import com.shudan.shudancc.utils.NetworkUtil;
import com.shudan.shudancc.view.IMainView;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenterImpl extends BasePresenterImpl<IMainView> implements IMainPresenter {

    public void queryBookShelf(final Boolean needRefresh) {
        if (needRefresh)
            mView.activityRefreshView();
        Observable.create(new ObservableOnSubscribe<List<BookShelfBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookShelfBean>> e) throws Exception {
                List<BookShelfBean> bookShelfes = DbHelper.getInstance().getmDaoSession().getBookShelfBeanDao().queryBuilder().orderDesc(BookShelfBeanDao.Properties.FinalDate).list();
                for (int i = 0; i < bookShelfes.size(); i++) {
                    List<BookInfoBean> temp = DbHelper.getInstance().getmDaoSession().getBookInfoBeanDao().queryBuilder().where(BookInfoBeanDao.Properties.NoteUrl.eq(bookShelfes.get(i).getNoteUrl())).limit(1).build().list();
                    if (temp != null && temp.size() > 0) {
                        BookInfoBean bookInfoBean = temp.get(0);
                        bookInfoBean.setChapterlist(DbHelper.getInstance().getmDaoSession().getChapterListBeanDao().queryBuilder().where(ChapterListBeanDao.Properties.NoteUrl.eq(bookShelfes.get(i).getNoteUrl())).orderAsc(ChapterListBeanDao.Properties.DurChapterIndex).build().list());
                        bookShelfes.get(i).setBookInfoBean(bookInfoBean);
                    } else {
                        DbHelper.getInstance().getmDaoSession().getBookShelfBeanDao().delete(bookShelfes.get(i));
                        bookShelfes.remove(i);
                        i--;
                    }
                }
                //发送1个事件
                e.onNext(bookShelfes == null ? new ArrayList<BookShelfBean>() : bookShelfes);
            }
        })
                .subscribeOn(Schedulers.io()) //将发送事件切换到子线程中去执行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<BookShelfBean>>() {
                    @Override
                    public void onNext(List<BookShelfBean> value) {
                        //接受事件
                        if (null != value) {
                            mView.refreshBookShelf(value);
                            if (needRefresh) {
                                startRefreshBook(value);
                            } else {
                                mView.refreshFinish();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.refreshError(NetworkUtil.getErrorTip(NetworkUtil.ERROR_CODE_ANALY));
                    }
                });
    }

    public void queryAppCommonData(final Boolean needRefresh) {
//        AppCommonBean appCommonBean = new AppCommonBean(null,"homeTips","close");
//        DbHelper.getInstance().getmDaoSession().getAppCommonBeanDao().insertOrReplaceInTx(appCommonBean);

        Observable.create(new ObservableOnSubscribe<List<AppCommonBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AppCommonBean>> e) throws Exception {
                List<AppCommonBean> appCommonData = DbHelper.getInstance().getmDaoSession().getAppCommonBeanDao().queryBuilder().where(AppCommonBeanDao.Properties.AppCommonKey.eq("homeTips")).limit(1).build().list();

//                if (appCommonData != null && appCommonData.size() > 0) {
//                    AppCommonBean appCommon = appCommonData.get(0);
//                    Log.v("homeTips", appCommon.getAppCommonValue());
//                }

                //发送1个事件
                e.onNext(appCommonData == null ? new ArrayList<AppCommonBean>() : appCommonData);
            }
        })
                .subscribeOn(Schedulers.io()) //将发送事件切换到子线程中去执行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<AppCommonBean>>() {
                    @Override
                    public void onNext(List<AppCommonBean> value) {
                        //接受事件
                        if (null != value &&  value.size() > 0) {
                            AppCommonBean appCommon = value.get(0);
                            String appCommonValue = appCommon.getAppCommonValue();
                            if (appCommonValue.equals("close")) {
                                mView.setflWarnVisibility("GONE");
                            }else {
                                mView.setflWarnVisibility("VISIBLE");
                            }
                        } else {
                            mView.setflWarnVisibility("VISIBLE");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.refreshError(NetworkUtil.getErrorTip(NetworkUtil.ERROR_CODE_ANALY));
                    }
                });
    }

    public void saveAppCommonData(String appCommonKey, String appCommonValue) {
        AppCommonBean appCommonBean = new AppCommonBean(null,appCommonKey,appCommonValue);
        DbHelper.getInstance().getmDaoSession().getAppCommonBeanDao().insertOrReplaceInTx(appCommonBean);
    }

    public void startRefreshBook(List<BookShelfBean> value){
        if (value != null && value.size() > 0){
            mView.setRecyclerMaxProgress(value.size());
            refreshBookShelf(value,0);
        }else{
            mView.refreshFinish();
        }
    }

    private void refreshBookShelf(final List<BookShelfBean> value, final int index) {
        if (index<=value.size()-1) {
            WebBookModelImpl.getInstance().getChapterList(value.get(index), new OnGetChapterListListener() {
                @Override
                public void success(BookShelfBean bookShelfBean) {
                    saveBookToShelf(value,index);
                }

                @Override
                public void error() {
                    mView.refreshError(NetworkUtil.getErrorTip(NetworkUtil.ERROR_CODE_NONET));
                }
            });
        } else {
            queryBookShelf(false);
        }
    }

    private void saveBookToShelf(final List<BookShelfBean> datas, final int index){
        Observable.create(new ObservableOnSubscribe<BookShelfBean>() {
            @Override
            public void subscribe(ObservableEmitter<BookShelfBean> e) throws Exception {
                DbHelper.getInstance().getmDaoSession().getChapterListBeanDao().insertOrReplaceInTx(datas.get(index).getBookInfoBean().getChapterlist());
                e.onNext(datas.get(index));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<BookShelfBean>() {
                    @Override
                    public void onNext(BookShelfBean value) {
                        mView.refreshRecyclerViewItemAdd();
                        refreshBookShelf(datas,index+1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.refreshError(NetworkUtil.getErrorTip(NetworkUtil.ERROR_CODE_NONET));
                    }
                });
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void attachView(@NonNull IView iView) {
        super.attachView(iView);
        RxBus.get().register(this);
    }

    @Override
    public void detachView() {
        RxBus.get().unregister(this);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(RxBusTag.HAD_ADD_BOOK),
                    @Tag(RxBusTag.HAD_REMOVE_BOOK),
                    @Tag(RxBusTag.UPDATE_BOOK_PROGRESS)
            }
    )
    public void hadddOrRemoveBook(BookShelfBean bookShelfBean) {
        queryBookShelf(false);
    }
}
