//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.shudan.shudancc;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.shudan.shudancc.R;
import com.shudan.shudancc.service.DownloadService;
import com.umeng.analytics.MobclickAgent;

public class MApplication extends Application {

    private static MApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.IS_RELEASE) {
            String channel = "debug";
            try {
                ApplicationInfo appInfo = getPackageManager()
                        .getApplicationInfo(getPackageName(),
                                PackageManager.GET_META_DATA);

                // 获取AndroidManifest中<meta-data>标签的值
                channel = appInfo.metaData.getString("UMENG_CHANNEL_VALUE");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, getString(R.string.umeng_key), channel, MobclickAgent.EScenarioType.E_UM_NORMAL, true));
        }
        instance = this;
        ProxyManager.initProxy();
        startService(new Intent(this, DownloadService.class));
    }

    public static MApplication getInstance() {
        return instance;
    }
}
