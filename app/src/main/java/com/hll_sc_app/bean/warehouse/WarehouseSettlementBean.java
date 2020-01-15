package com.hll_sc_app.bean.warehouse;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 获取结算方式列表
 *
 */
public class WarehouseSettlementBean implements Parcelable {
    /**
     * 结算方式：1-货到付款 2- 账期支付 3-线上支付
     */
    private String settlementWay;

    public static final Creator<WarehouseSettlementBean> CREATOR = new Creator<WarehouseSettlementBean>() {
        @Override
        public WarehouseSettlementBean createFromParcel(Parcel source) {
            return new WarehouseSettlementBean(source);
        }

        @Override
        public WarehouseSettlementBean[] newArray(int size) {
            return new WarehouseSettlementBean[size];
        }
    };
    private String settleDate;

    public WarehouseSettlementBean() {
    }

    /**
     * 采购商id
     */
    private String purchaserID;
    /**
     * 门店账期日类型,0-未设置,1-按周,2-按月
     */
    private String accountPeriodType;
    /**
     * 代仓ID(非代仓发货传0)
     */
    private String warehouseID;
    /**
     * 供应商id
     */
    private String groupID;
    /**
     * 门店账期日
     */
    private String accountPeriod;
    /**
     * 门店id
     */
    private String shopID;

    public String getSettlementWay() {
        return settlementWay;
    }

    public void setSettlementWay(String settlementWay) {
        this.settlementWay = settlementWay;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getAccountPeriodType() {
        return accountPeriodType;
    }

    public void setAccountPeriodType(String accountPeriodType) {
        this.accountPeriodType = accountPeriodType;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    protected WarehouseSettlementBean(Parcel in) {
        this.settlementWay = in.readString();
        this.settleDate = in.readString();
        this.purchaserID = in.readString();
        this.accountPeriodType = in.readString();
        this.warehouseID = in.readString();
        this.groupID = in.readString();
        this.accountPeriod = in.readString();
        this.shopID = in.readString();
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.settlementWay);
        dest.writeString(this.settleDate);
        dest.writeString(this.purchaserID);
        dest.writeString(this.accountPeriodType);
        dest.writeString(this.warehouseID);
        dest.writeString(this.groupID);
        dest.writeString(this.accountPeriod);
        dest.writeString(this.shopID);
    }
}
