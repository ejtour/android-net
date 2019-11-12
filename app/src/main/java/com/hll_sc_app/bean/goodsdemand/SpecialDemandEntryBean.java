package com.hll_sc_app.bean.goodsdemand;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */

public class SpecialDemandEntryBean implements Parcelable {
    private int productNum;
    private String purchaserID;
    private String purchaserLogo;
    private String purchaserName;

    protected SpecialDemandEntryBean(Parcel in) {
        productNum = in.readInt();
        purchaserID = in.readString();
        purchaserLogo = in.readString();
        purchaserName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productNum);
        dest.writeString(purchaserID);
        dest.writeString(purchaserLogo);
        dest.writeString(purchaserName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpecialDemandEntryBean> CREATOR = new Creator<SpecialDemandEntryBean>() {
        @Override
        public SpecialDemandEntryBean createFromParcel(Parcel in) {
            return new SpecialDemandEntryBean(in);
        }

        @Override
        public SpecialDemandEntryBean[] newArray(int size) {
            return new SpecialDemandEntryBean[size];
        }
    };

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserLogo() {
        return purchaserLogo;
    }

    public void setPurchaserLogo(String purchaserLogo) {
        this.purchaserLogo = purchaserLogo;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }
}
