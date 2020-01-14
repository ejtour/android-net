package com.hll_sc_app.bean.report.customerreceive;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/30
 */

public class ReceiveCustomerBean implements Parcelable {
    public static final Creator<ReceiveCustomerBean> CREATOR = new Creator<ReceiveCustomerBean>() {
        @Override
        public ReceiveCustomerBean createFromParcel(Parcel in) {
            return new ReceiveCustomerBean(in);
        }

        @Override
        public ReceiveCustomerBean[] newArray(int size) {
            return new ReceiveCustomerBean[size];
        }
    };
    private String demandID;
    private String groupID;
    private boolean isShow;
    private double puchaseAmount;
    private String purchaserName;
    private double returnsAmount;
    private String shopName;
    private String supplierName;

    public ReceiveCustomerBean() {
    }

    protected ReceiveCustomerBean(Parcel in) {
        demandID = in.readString();
        groupID = in.readString();
        puchaseAmount = in.readDouble();
        purchaserName = in.readString();
        returnsAmount = in.readDouble();
        shopName = in.readString();
        supplierName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(demandID);
        dest.writeString(groupID);
        dest.writeDouble(puchaseAmount);
        dest.writeString(purchaserName);
        dest.writeDouble(returnsAmount);
        dest.writeString(shopName);
        dest.writeString(supplierName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDemandID() {
        return demandID;
    }

    public void setDemandID(String demandID) {
        this.demandID = demandID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public double getPuchaseAmount() {
        return puchaseAmount;
    }

    public void setPuchaseAmount(double puchaseAmount) {
        this.puchaseAmount = puchaseAmount;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public double getReturnsAmount() {
        return returnsAmount;
    }

    public void setReturnsAmount(double returnsAmount) {
        this.returnsAmount = returnsAmount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
