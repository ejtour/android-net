package com.hll_sc_app.bean.order.settle;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PayWaysReq implements Parcelable {
    private List<GroupList> groupList;
    private String source;
    private String payType;
    private String supplyID;

    public List<GroupList> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupList> groupList) {
        this.groupList = groupList;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSupplyID() {
        return supplyID;
    }

    public void setSupplyID(String supplyID) {
        this.supplyID = supplyID;
    }

    public static class GroupList implements Parcelable {
        private String payee;
        private String purchaserID;
        private String shipperType;
        private String agencyID;
        private String groupID;

        public String getPayee() {
            return payee;
        }

        public void setPayee(String payee) {
            this.payee = payee;
        }

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public String getShipperType() {
            return shipperType;
        }

        public void setShipperType(String shipperType) {
            this.shipperType = shipperType;
        }

        public String getAgencyID() {
            return agencyID;
        }

        public void setAgencyID(String agencyID) {
            this.agencyID = agencyID;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.payee);
            dest.writeString(this.purchaserID);
            dest.writeString(this.shipperType);
            dest.writeString(this.agencyID);
            dest.writeString(this.groupID);
        }

        public GroupList() {
        }

        protected GroupList(Parcel in) {
            this.payee = in.readString();
            this.purchaserID = in.readString();
            this.shipperType = in.readString();
            this.agencyID = in.readString();
            this.groupID = in.readString();
        }

        public static final Creator<GroupList> CREATOR = new Creator<GroupList>() {
            @Override
            public GroupList createFromParcel(Parcel source) {
                return new GroupList(source);
            }

            @Override
            public GroupList[] newArray(int size) {
                return new GroupList[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.groupList);
        dest.writeString(this.source);
        dest.writeString(this.payType);
        dest.writeString(this.supplyID);
    }

    public PayWaysReq() {
    }

    protected PayWaysReq(Parcel in) {
        this.groupList = in.createTypedArrayList(GroupList.CREATOR);
        this.source = in.readString();
        this.payType = in.readString();
        this.supplyID = in.readString();
    }

    public static final Parcelable.Creator<PayWaysReq> CREATOR = new Parcelable.Creator<PayWaysReq>() {
        @Override
        public PayWaysReq createFromParcel(Parcel source) {
            return new PayWaysReq(source);
        }

        @Override
        public PayWaysReq[] newArray(int size) {
            return new PayWaysReq[size];
        }
    };
}
