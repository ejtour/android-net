package com.hll_sc_app.bean.report.customerLack;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerLackReq implements Parcelable {

    private String endDate;
    private String groupID;
    private int pageNum;
    private int pageSize;
    private String productKeyword;
    private String purchaserID;
    private String shopID;
    private String startDate;
    /**
     * 1:缺货汇总 2:缺货明细(shopID不能为空)
     */
    private int type = 1;


    public CustomerLackReq(){}

    protected CustomerLackReq(Parcel in) {
        endDate = in.readString();
        groupID = in.readString();
        pageNum = in.readInt();
        pageSize = in.readInt();
        productKeyword = in.readString();
        purchaserID = in.readString();
        shopID = in.readString();
        startDate = in.readString();
        type = in.readInt();
    }

    public static final Creator<CustomerLackReq> CREATOR = new Creator<CustomerLackReq>() {
        @Override
        public CustomerLackReq createFromParcel(Parcel in) {
            return new CustomerLackReq(in);
        }

        @Override
        public CustomerLackReq[] newArray(int size) {
            return new CustomerLackReq[size];
        }
    };

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getProductKeyword() {
        return productKeyword;
    }

    public void setProductKeyword(String productKeyword) {
        this.productKeyword = productKeyword;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(endDate);
        parcel.writeString(groupID);
        parcel.writeInt(pageNum);
        parcel.writeInt(pageSize);
        parcel.writeString(productKeyword);
        parcel.writeString(purchaserID);
        parcel.writeString(shopID);
        parcel.writeString(startDate);
        parcel.writeInt(type);
    }
}
