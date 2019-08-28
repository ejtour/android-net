package com.hll_sc_app.bean.marketingsetting;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * 发放优惠券请求
 */
public class CouponSendReq {


    private String note;
    private int sendType;
    private String groupID;
    private List<GroupandShopsBean> customerList;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public List<GroupandShopsBean> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<GroupandShopsBean> customerList) {
        this.customerList = customerList;
    }

    public static class GroupandShopsBean implements Parcelable {

        public static final Creator<GroupandShopsBean> CREATOR = new Creator<GroupandShopsBean>() {
            @Override
            public GroupandShopsBean createFromParcel(Parcel source) {
                return new GroupandShopsBean(source);
            }

            @Override
            public GroupandShopsBean[] newArray(int size) {
                return new GroupandShopsBean[size];
            }
        };
        private String purchaserName;
        private String purchaserID;
        private int scope;
        private String discountID;
        private int sendCount;
        private List<String> shopIDList;

        public GroupandShopsBean() {
        }

        protected GroupandShopsBean(Parcel in) {
            this.purchaserName = in.readString();
            this.purchaserID = in.readString();
            this.scope = in.readInt();
            this.discountID = in.readString();
            this.sendCount = in.readInt();
            this.shopIDList = in.createStringArrayList();
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return TextUtils.equals(purchaserID, ((GroupandShopsBean) obj).purchaserID);
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public int getScope() {
            return scope;
        }

        public void setScope(int scope) {
            this.scope = scope;
        }

        public String getDiscountID() {
            return discountID;
        }

        public void setDiscountID(String discountID) {
            this.discountID = discountID;
        }

        public int getSendCount() {
            return sendCount;
        }

        public void setSendCount(int sendCount) {
            this.sendCount = sendCount;
        }

        public List<String> getShopIDList() {
            return shopIDList;
        }

        public void setShopIDList(List<String> shopIDList) {
            this.shopIDList = shopIDList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.purchaserName);
            dest.writeString(this.purchaserID);
            dest.writeInt(this.scope);
            dest.writeString(this.discountID);
            dest.writeInt(this.sendCount);
            dest.writeStringList(this.shopIDList);
        }
    }
}
