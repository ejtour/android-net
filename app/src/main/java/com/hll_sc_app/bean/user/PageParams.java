package com.hll_sc_app.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * webView协议展示页面
 *
 * @author zhuyingsong
 * @date 2019-06-05
 */
public class PageParams implements Parcelable {
    public static final Parcelable.Creator<PageParams> CREATOR = new Parcelable.Creator<PageParams>() {
        @Override
        public PageParams createFromParcel(Parcel source) {
            return new PageParams(source);
        }

        @Override
        public PageParams[] newArray(int size) {
            return new PageParams[size];
        }
    };
    private String title;
    private String protocolUrl;

    public PageParams() {
    }

    protected PageParams(Parcel in) {
        this.title = in.readString();
        this.protocolUrl = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }

    public void setProtocolUrl(String protocolUrl) {
        this.protocolUrl = protocolUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.protocolUrl);
    }
}
