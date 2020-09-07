package com.shudan.shudancc.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * app通用配置信息
 */
@Entity
public class AppCommonBean implements Parcelable, Cloneable {
    @Id(autoincrement = true)
    private Long id;

    private String appCommonKey; //关键key
    private String appCommonValue; //key对应的值

    @Generated(hash = 626068260)
    public AppCommonBean(Long id, String appCommonKey, String appCommonValue) {
        this.id = id;
        this.appCommonKey = appCommonKey;
        this.appCommonValue = appCommonValue;
    }

    public AppCommonBean() {
    }

    protected AppCommonBean(Parcel in) {
        appCommonKey = in.readString();
        appCommonValue = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appCommonKey);
        dest.writeString(appCommonValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Transient
    public static final Creator<AppCommonBean> CREATOR = new Creator<AppCommonBean>() {
        @Override
        public AppCommonBean createFromParcel(Parcel in) {
            return new AppCommonBean(in);
        }

        @Override
        public AppCommonBean[] newArray(int size) {
            return new AppCommonBean[size];
        }
    };

    public String getAppCommonKey() {
        return appCommonKey;
    }

    public void setAppCommonKey(String appCommonKey) {
        this.appCommonKey = appCommonKey;
    }

    public String getAppCommonValue() {
        return appCommonValue;
    }

    public void setAppCommonValue(String appCommonValue) {
        this.appCommonValue = appCommonValue;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        AppCommonBean appCommonBean = (AppCommonBean) super.clone();
        appCommonBean.appCommonKey = appCommonKey;
        appCommonBean.appCommonValue = appCommonValue;
        return appCommonBean;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
