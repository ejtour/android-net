package com.hll_sc_app.bean.notification;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 消息推送-收到的extraMap
 *
 * @author zhuyingsong
 * @date 2019/4/8
 */
public class Page implements Parcelable {
    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel source) {
            return new Page(source);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };
    private String pageCode;
    private String pageData;

    public Page() {
    }

    protected Page(Parcel in) {
        this.pageCode = in.readString();
        this.pageData = in.readString();
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getPageData() {
        return pageData;
    }

    public void setPageData(String pageData) {
        this.pageData = pageData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pageCode);
        dest.writeString(this.pageData);
    }
}
