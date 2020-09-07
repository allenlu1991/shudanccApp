//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.monke.monkeybook.presenter.impl;

import android.os.Handler;
import com.monke.basemvplib.impl.BasePresenterImpl;
import com.monke.monkeybook.MApplication;
import com.monke.monkeybook.base.observer.SimpleObserver;
import com.monke.monkeybook.bean.LibraryBean;
import com.monke.monkeybook.cache.ACache;
import com.monke.monkeybook.model.impl.GxwztvBookModelImpl;
import com.monke.monkeybook.presenter.ILibraryPresenter;
import com.monke.monkeybook.view.ILibraryView;
import java.util.LinkedHashMap;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LibraryPresenterImpl extends BasePresenterImpl<ILibraryView> implements ILibraryPresenter {
    public final static String LIBRARY_CACHE_KEY = "cache_library";
    private ACache mCache;
    private Boolean isFirst = true;

    private final LinkedHashMap<String,String> kinds = new LinkedHashMap<>();

    public LibraryPresenterImpl() {
        kinds.put("东方玄幻","https://www.ztv.la//xuanhuanxiaoshuo/");
        kinds.put("西方奇幻","https://www.ztv.la//qihuanxiaoshuo/");
        kinds.put("热血修真","https://www.ztv.la//xiuzhenxiaoshuo/");
        kinds.put("武侠仙侠","https://www.ztv.la//wuxiaxiaoshuo/");
        kinds.put("都市爽文","https://www.ztv.la//dushixiaoshuo/");
        kinds.put("言情暧昧","https://www.ztv.la//yanqingxiaoshuo/");
        kinds.put("灵异悬疑","https://www.ztv.la//lingyixiaoshuo/");
        kinds.put("运动竞技","https://www.ztv.la//jingjixiaoshuo/");
        kinds.put("历史架空","https://www.ztv.la//lishixiaoshuo/");
        kinds.put("耽美","https://www.ztv.la//danmeixiaoshuo/");
        kinds.put("科幻迷航","https://www.ztv.la//kehuanxiaoshuo/");
        kinds.put("游戏人生","https://www.ztv.la//youxixiaoshuo/");
        kinds.put("军事斗争","https://www.ztv.la//junshixiaoshuo/");
        kinds.put("商战人生","https://www.ztv.la//shangzhanxiaoshuo/");
        kinds.put("校园爱情","https://www.ztv.la//xiaoyuanxiaoshuo/");
        kinds.put("官场仕途","https://www.ztv.la//guanchangxiaoshuo/");
        kinds.put("娱乐明星","https://www.ztv.la//zhichangxiaoshuo/");
        kinds.put("其他","https://www.ztv.la//qitaxiaoshuo/");

        mCache = ACache.get(MApplication.getInstance());
    }

    @Override
    public void detachView() {

    }

    @Override
    public void getLibraryData() {
        if (isFirst) {
            isFirst = false;
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    String cache = mCache.getAsString(LIBRARY_CACHE_KEY);
                    e.onNext(cache);
                    e.onComplete();
                }
            }).flatMap(new Function<String, ObservableSource<LibraryBean>>() {
                @Override
                public ObservableSource<LibraryBean> apply(String s) throws Exception {
                    return GxwztvBookModelImpl.getInstance().analyLibraryData(s);
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SimpleObserver<LibraryBean>() {
                        @Override
                        public void onNext(LibraryBean value) {
                            //执行刷新界面
                            mView.updateUI(value);
                            getLibraryNewData();
                        }

                        @Override
                        public void onError(Throwable e) {
                            getLibraryNewData();
                        }
                    });
        }else{
            getLibraryNewData();
        }
    }

    private void getLibraryNewData() {
        GxwztvBookModelImpl.getInstance().getLibraryData(mCache).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<LibraryBean>() {
                    @Override
                    public void onNext(final LibraryBean value) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mView.updateUI(value);
                                mView.finishRefresh();
                            }
                        },1000);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.finishRefresh();
                    }
                });
    }

    @Override
    public LinkedHashMap<String, String> getKinds() {
        return kinds;
    }
}