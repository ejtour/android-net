package com.hll_sc_app.bean.contract;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContractListResp {
    private List<ContractBean> list;
    private PageInfo pageInfo;

    public List<ContractBean> getList() {
        return list;
    }

    public void setList(List<ContractBean> list) {
        this.list = list;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static class ContractBean implements Parcelable {
        private String attachment;
        private String contractCode;
        private String contractName;
        private int distanceExpirationDate;
        private String endDate;
        private String groupName;
        private String groupID;
        private String id;
        private String purchaserID;
        private String purchaserName;
        /**
         * 采购商类型 1-合作关系，2-意向客户
         */
        private int purchaserType;

        private String remarks;
        private String signDate;
        private String signEmployeeName;
        private String startDate;
        /**
         * 合同状态 0-待审核，1-已审核，2-执行中，3-已终止，4-已到期
         */
        private int status;

        public String getTransformStatus() {
            switch (status) {
                case 0:
                    return "待审核";
                case 1:
                    return "已审核";
                case 2:
                    return "执行中";
                case 3:
                    return "已终止";
                case 4:
                    return "已到期";
                default:
                    return "";
            }
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public String getContractName() {
            return contractName;
        }

        public void setContractName(String contractName) {
            this.contractName = contractName;
        }

        public int getDistanceExpirationDate() {
            return distanceExpirationDate;
        }

        public void setDistanceExpirationDate(int distanceExpirationDate) {
            this.distanceExpirationDate = distanceExpirationDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPurchaserID() {
            return purchaserID;
        }

        public void setPurchaserID(String purchaserID) {
            this.purchaserID = purchaserID;
        }

        public String getPurchaserName() {
            return purchaserName;
        }

        public void setPurchaserName(String purchaserName) {
            this.purchaserName = purchaserName;
        }

        public int getPurchaserType() {
            return purchaserType;
        }

        public void setPurchaserType(int purchaserType) {
            this.purchaserType = purchaserType;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getSignDate() {
            return signDate;
        }

        public void setSignDate(String signDate) {
            this.signDate = signDate;
        }

        public String getSignEmployeeName() {
            return signEmployeeName;
        }

        public void setSignEmployeeName(String signEmployeeName) {
            this.signEmployeeName = signEmployeeName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.attachment);
            dest.writeString(this.contractCode);
            dest.writeString(this.contractName);
            dest.writeInt(this.distanceExpirationDate);
            dest.writeString(this.endDate);
            dest.writeString(this.groupName);
            dest.writeString(this.groupID);
            dest.writeString(this.id);
            dest.writeString(this.purchaserID);
            dest.writeString(this.purchaserName);
            dest.writeInt(this.purchaserType);
            dest.writeString(this.remarks);
            dest.writeString(this.signDate);
            dest.writeString(this.signEmployeeName);
            dest.writeString(this.startDate);
            dest.writeInt(this.status);
        }

        public ContractBean() {
        }

        protected ContractBean(Parcel in) {
            this.attachment = in.readString();
            this.contractCode = in.readString();
            this.contractName = in.readString();
            this.distanceExpirationDate = in.readInt();
            this.endDate = in.readString();
            this.groupName = in.readString();
            this.groupID = in.readString();
            this.id = in.readString();
            this.purchaserID = in.readString();
            this.purchaserName = in.readString();
            this.purchaserType = in.readInt();
            this.remarks = in.readString();
            this.signDate = in.readString();
            this.signEmployeeName = in.readString();
            this.startDate = in.readString();
            this.status = in.readInt();
        }

        public static final Parcelable.Creator<ContractBean> CREATOR = new Parcelable.Creator<ContractBean>() {
            @Override
            public ContractBean createFromParcel(Parcel source) {
                return new ContractBean(source);
            }

            @Override
            public ContractBean[] newArray(int size) {
                return new ContractBean[size];
            }
        };
    }

    public static class PageInfo {
        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

}
