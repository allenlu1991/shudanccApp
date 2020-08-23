package com.monke.monkeybook.bean;

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

    private String key; //关键key
    private String value; //key对应的值

    @Generated(hash = 606218941)
    public AppCommonBean(Long id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public AppCommonBean() {
    }

    protected AppCommonBean(Parcel in) {
        key = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        AppCommonBean appCommonBean = (AppCommonBean) super.clone();
        appCommonBean.key = key;
        appCommonBean.value = value;
        return appCommonBean;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
