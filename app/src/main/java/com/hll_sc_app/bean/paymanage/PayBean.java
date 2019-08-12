package com.hll_sc_app.bean.paymanage;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zhuyingsong
 * @date 2019-08-12
 */
public class PayBean implements Parcelable {
    public static final Creator<PayBean> CREATOR = new Creator<PayBean>() {
        @Override
        public PayBean createFromParcel(Parcel source) {
            return new PayBean(source);
        }

        @Override
        public PayBean[] newArray(int size) {
            return new PayBean[size];
        }
    };
    private String payType;
    private String payMethodName;
    private String imgPath;
    private String id;
    private boolean select;
    private boolean enable;

    public PayBean() {
    }

    protected PayBean(Parcel in) {
        this.payType = in.readString();
        this.payMethodName = in.readString();
        this.imgPath = in.readString();
        this.id = in.readString();
        this.select = in.readByte() != 0;
        this.enable = in.readByte() != 0;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayMethodName() {
        return payMethodName;
    }

    public void setPayMethodName(String payMethodName) {
        this.payMethodName = payMethodName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.payType);
        dest.writeString(this.payMethodName);
        dest.writeString(this.imgPath);
        dest.writeString(this.id);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enable ? (byte) 1 : (byte) 0);
    }
}
