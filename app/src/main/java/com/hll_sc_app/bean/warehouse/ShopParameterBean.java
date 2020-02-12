package com.hll_sc_app.bean.warehouse;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 代仓门店详情
 *
 * @author zhuyingsong
 * @date 2019-08-06
 */
public class ShopParameterBean implements Parcelable {
    private String payee;
    private String supportPay;
    private String payType;
    private String purchaserID;
    private String payTerm;
    private String groupID;
    private String settleDate;
    private String shopId;
    private String payTermType;

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getSupportPay() {
        return supportPay;
    }

    public void setSupportPay(String supportPay) {
        this.supportPay = supportPay;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPayTerm() {
        return payTerm;
    }

    public void setPayTerm(String payTerm) {
        this.payTerm = payTerm;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getPayTermType() {
        return payTermType;
    }

    public void setPayTermType(String payTermType) {
        this.payTermType = payTermType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.payee);
        dest.writeString(this.supportPay);
        dest.writeString(this.payType);
        dest.writeString(this.purchaserID);
        dest.writeString(this.payTerm);
        dest.writeString(this.groupID);
        dest.writeString(this.settleDate);
        dest.writeString(this.shopId);
        dest.writeString(this.payTermType);
    }

    public ShopParameterBean() {
    }

    protected ShopParameterBean(Parcel in) {
        this.payee = in.readString();
        this.supportPay = in.readString();
        this.payType = in.readString();
        this.purchaserID = in.readString();
        this.payTerm = in.readString();
        this.groupID = in.readString();
        this.settleDate = in.readString();
        this.shopId = in.readString();
        this.payTermType = in.readString();
    }

    public static final Parcelable.Creator<ShopParameterBean> CREATOR = new Parcelable.Creator<ShopParameterBean>() {
        @Override
        public ShopParameterBean createFromParcel(Parcel source) {
            return new ShopParameterBean(source);
        }

        @Override
        public ShopParameterBean[] newArray(int size) {
            return new ShopParameterBean[size];
        }
    };
}
