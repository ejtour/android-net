package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 售卖单位
 *
 * @author zhuyingsong
 * @date 2019-06-19
 */
public class SaleUnitNameBean implements Parcelable {
    private String nameFirstLetter;
    private String saleUnitID;
    private String saleUnitName;
    private String updateTime;

    public String getNameFirstLetter() {
        return nameFirstLetter;
    }

    public void setNameFirstLetter(String nameFirstLetter) {
        this.nameFirstLetter = nameFirstLetter;
    }

    public String getSaleUnitId() {
        return saleUnitID;
    }

    public void setSaleUnitId(String saleUnitId) {
        this.saleUnitID = saleUnitId;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public static final Parcelable.Creator<SaleUnitNameBean> CREATOR = new Parcelable.Creator<SaleUnitNameBean>() {
        @Override
        public SaleUnitNameBean createFromParcel(Parcel source) {
            return new SaleUnitNameBean(source);
        }

        @Override
        public SaleUnitNameBean[] newArray(int size) {
            return new SaleUnitNameBean[size];
        }
    };

    public SaleUnitNameBean() {
    }

    protected SaleUnitNameBean(Parcel in) {
        this.nameFirstLetter = in.readString();
        this.saleUnitID = in.readString();
        this.saleUnitName = in.readString();
        this.updateTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nameFirstLetter);
        dest.writeString(this.saleUnitID);
        dest.writeString(this.saleUnitName);
        dest.writeString(this.updateTime);
    }
}
