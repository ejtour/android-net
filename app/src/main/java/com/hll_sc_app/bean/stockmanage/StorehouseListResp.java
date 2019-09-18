package com.hll_sc_app.bean.stockmanage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 库存查询管理列表
 */
public class StorehouseListResp implements Parcelable {


    public static final Creator<StorehouseListResp> CREATOR = new Creator<StorehouseListResp>() {
        @Override
        public StorehouseListResp createFromParcel(Parcel source) {
            return new StorehouseListResp(source);
        }

        @Override
        public StorehouseListResp[] newArray(int size) {
            return new StorehouseListResp[size];
        }
    };
    private int totalSize;
    private List<Storehouse> list;

    public StorehouseListResp() {
    }

    protected StorehouseListResp(Parcel in) {
        this.totalSize = in.readInt();
        this.list = new ArrayList<Storehouse>();
        in.readList(this.list, Storehouse.class.getClassLoader());
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<Storehouse> getList() {
        return list;
    }

    public void setList(List<Storehouse> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalSize);
        dest.writeList(this.list);
    }

    public static class Storehouse implements Parcelable {
        public static final Creator<Storehouse> CREATOR = new Creator<Storehouse>() {
            @Override
            public Storehouse createFromParcel(Parcel source) {
                return new Storehouse(source);
            }

            @Override
            public Storehouse[] newArray(int size) {
                return new Storehouse[size];
            }
        };
        private String note;
        private String actionTime;
        private String address;
        private String charge;
        private String actionBy;
        private String groupID;
        private int isOpeningBalance;
        private int source;
        private int isActive;
        private String linkTel;
        private int isOpenWms;
        private String houseName;
        private String createBy;
        private int isDefault;
        private String demandID;
        private String thirdHouseID;
        private String createTime;
        private String houseCode;
        private String action;
        private String alias;
        private String id;

        public Storehouse() {
        }

        protected Storehouse(Parcel in) {
            this.note = in.readString();
            this.actionTime = in.readString();
            this.address = in.readString();
            this.charge = in.readString();
            this.actionBy = in.readString();
            this.groupID = in.readString();
            this.isOpeningBalance = in.readInt();
            this.source = in.readInt();
            this.isActive = in.readInt();
            this.linkTel = in.readString();
            this.isOpenWms = in.readInt();
            this.houseName = in.readString();
            this.createBy = in.readString();
            this.isDefault = in.readInt();
            this.demandID = in.readString();
            this.thirdHouseID = in.readString();
            this.createTime = in.readString();
            this.houseCode = in.readString();
            this.action = in.readString();
            this.alias = in.readString();
            this.id = in.readString();
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getActionTime() {
            return actionTime;
        }

        public void setActionTime(String actionTime) {
            this.actionTime = actionTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getActionBy() {
            return actionBy;
        }

        public void setActionBy(String actionBy) {
            this.actionBy = actionBy;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public int getIsOpeningBalance() {
            return isOpeningBalance;
        }

        public void setIsOpeningBalance(int isOpeningBalance) {
            this.isOpeningBalance = isOpeningBalance;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public String getLinkTel() {
            return linkTel;
        }

        public void setLinkTel(String linkTel) {
            this.linkTel = linkTel;
        }

        public int getIsOpenWms() {
            return isOpenWms;
        }

        public void setIsOpenWms(int isOpenWms) {
            this.isOpenWms = isOpenWms;
        }

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public String getDemandID() {
            return demandID;
        }

        public void setDemandID(String demandID) {
            this.demandID = demandID;
        }

        public String getThirdHouseID() {
            return thirdHouseID;
        }

        public void setThirdHouseID(String thirdHouseID) {
            this.thirdHouseID = thirdHouseID;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHouseCode() {
            return houseCode;
        }

        public void setHouseCode(String houseCode) {
            this.houseCode = houseCode;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.note);
            dest.writeString(this.actionTime);
            dest.writeString(this.address);
            dest.writeString(this.charge);
            dest.writeString(this.actionBy);
            dest.writeString(this.groupID);
            dest.writeInt(this.isOpeningBalance);
            dest.writeInt(this.source);
            dest.writeInt(this.isActive);
            dest.writeString(this.linkTel);
            dest.writeInt(this.isOpenWms);
            dest.writeString(this.houseName);
            dest.writeString(this.createBy);
            dest.writeInt(this.isDefault);
            dest.writeString(this.demandID);
            dest.writeString(this.thirdHouseID);
            dest.writeString(this.createTime);
            dest.writeString(this.houseCode);
            dest.writeString(this.action);
            dest.writeString(this.alias);
            dest.writeString(this.id);
        }
    }
}
