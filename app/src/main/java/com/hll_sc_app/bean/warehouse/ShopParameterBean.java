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
    private String codPayMethod = "";
    private String onlinePayMethod = "";

    protected ShopParameterBean(Parcel in) {
        payee = in.readString();
        supportPay = in.readString();
        payType = in.readString();
        purchaserID = in.readString();
        payTerm = in.readString();
        groupID = in.readString();
        settleDate = in.readString();
        shopId = in.readString();
        payTermType = in.readString();
        codPayMethod = in.readString();
        onlinePayMethod = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(payee);
        dest.writeString(supportPay);
        dest.writeString(payType);
        dest.writeString(purchaserID);
        dest.writeString(payTerm);
        dest.writeString(groupID);
        dest.writeString(settleDate);
        dest.writeString(shopId);
        dest.writeString(payTermType);
        dest.writeString(codPayMethod);
        dest.writeString(onlinePayMethod);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopParameterBean> CREATOR = new Creator<ShopParameterBean>() {
        @Override
        public ShopParameterBean createFromParcel(Parcel in) {
            return new ShopParameterBean(in);
        }

        @Override
        public ShopParameterBean[] newArray(int size) {
            return new ShopParameterBean[size];
        }
    };

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

    public String getCodPayMethod() {
        return codPayMethod;
    }

    public void setCodPayMethod(String codPayMethod) {
        this.codPayMethod = codPayMethod;
    }

    public String getOnlinePayMethod() {
        return onlinePayMethod;
    }

    public void setOnlinePayMethod(String onlinePayMethod) {
        this.onlinePayMethod = onlinePayMethod;
    }

    public ShopParameterBean() {
    }
}
