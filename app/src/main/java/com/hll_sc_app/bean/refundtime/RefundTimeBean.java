package com.hll_sc_app.bean.refundtime;

import android.os.Parcel;
import android.os.Parcelable;

public class RefundTimeBean implements Parcelable {
    private Long refundID;
    private Integer categoryID;
    private String categoryName;
    private Integer num;
    private Integer customerLevel;
    private Long groupID;

    public Integer getCategoryID() {
        return categoryID;
    }

    public Integer getCustomerLevel() {
        return customerLevel;
    }

    public Integer getNum() {
        return num;
    }

    public Long getGroupID() {
        return groupID;
    }

    public Long getRefundID() {
        return refundID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCustomerLevel(Integer customerLevel) {
        this.customerLevel = customerLevel;
    }

    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setRefundID(Long refundID) {
        this.refundID = refundID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.refundID);
        dest.writeValue(this.categoryID);
        dest.writeString(this.categoryName);
        dest.writeValue(this.num);
        dest.writeValue(this.customerLevel);
        dest.writeValue(this.groupID);
    }

    public RefundTimeBean() {
    }

    protected RefundTimeBean(Parcel in) {
        this.refundID = (Long) in.readValue(Long.class.getClassLoader());
        this.categoryID = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryName = in.readString();
        this.num = (Integer) in.readValue(Integer.class.getClassLoader());
        this.customerLevel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.groupID = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<RefundTimeBean> CREATOR = new Creator<RefundTimeBean>() {
        @Override
        public RefundTimeBean createFromParcel(Parcel source) {
            return new RefundTimeBean(source);
        }

        @Override
        public RefundTimeBean[] newArray(int size) {
            return new RefundTimeBean[size];
        }
    };
}
