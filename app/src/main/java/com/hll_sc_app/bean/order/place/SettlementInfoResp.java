package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/17
 */

public class SettlementInfoResp implements Parcelable {
    public static final Creator<SettlementInfoResp> CREATOR = new Creator<SettlementInfoResp>() {
        @Override
        public SettlementInfoResp createFromParcel(Parcel in) {
            return new SettlementInfoResp(in);
        }

        @Override
        public SettlementInfoResp[] newArray(int size) {
            return new SettlementInfoResp[size];
        }
    };
    private String purchaserShopID;
    private String purchaserShopName;
    private String receiverAddress;
    private String receiverMobile;
    private String receiverName;
    private String shopCartKey;
    private List<SupplierGroupBean> supplierGroupList;

    protected SettlementInfoResp(Parcel in) {
        purchaserShopID = in.readString();
        purchaserShopName = in.readString();
        receiverAddress = in.readString();
        receiverMobile = in.readString();
        receiverName = in.readString();
        shopCartKey = in.readString();
        supplierGroupList = in.createTypedArrayList(SupplierGroupBean.CREATOR);
    }

    public static Creator<SettlementInfoResp> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(purchaserShopID);
        dest.writeString(purchaserShopName);
        dest.writeString(receiverAddress);
        dest.writeString(receiverMobile);
        dest.writeString(receiverName);
        dest.writeString(shopCartKey);
        dest.writeTypedList(supplierGroupList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPurchaserShopID() {
        return purchaserShopID;
    }

    public void setPurchaserShopID(String purchaserShopID) {
        this.purchaserShopID = purchaserShopID;
    }

    public String getPurchaserShopName() {
        return purchaserShopName;
    }

    public void setPurchaserShopName(String purchaserShopName) {
        this.purchaserShopName = purchaserShopName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getShopCartKey() {
        return shopCartKey;
    }

    public void setShopCartKey(String shopCartKey) {
        this.shopCartKey = shopCartKey;
    }

    public List<SupplierGroupBean> getSupplierGroupList() {
        return supplierGroupList;
    }

    public void setSupplierGroupList(List<SupplierGroupBean> supplierGroupList) {
        this.supplierGroupList = supplierGroupList;
    }
}
