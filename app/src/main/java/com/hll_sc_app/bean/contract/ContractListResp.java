package com.hll_sc_app.bean.contract;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.user.CategoryItem;

import java.util.Arrays;
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
        private String shopID;
        private String shopName;


        @Override
        public int hashCode() {
            return Arrays.hashCode(new Object[]{contractCode});
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final ContractBean other = (ContractBean) obj;
            return UIUtils.equals(this.contractCode, other.contractCode);
        }


        protected ContractBean(Parcel in) {
            attachment = in.readString();
            contractCode = in.readString();
            contractName = in.readString();
            distanceExpirationDate = in.readInt();
            endDate = in.readString();
            groupName = in.readString();
            groupID = in.readString();
            id = in.readString();
            purchaserID = in.readString();
            purchaserName = in.readString();
            purchaserType = in.readInt();
            remarks = in.readString();
            signDate = in.readString();
            signEmployeeName = in.readString();
            startDate = in.readString();
            shopID = in.readString();
            shopName = in.readString();
            status = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(attachment);
            dest.writeString(contractCode);
            dest.writeString(contractName);
            dest.writeInt(distanceExpirationDate);
            dest.writeString(endDate);
            dest.writeString(groupName);
            dest.writeString(groupID);
            dest.writeString(id);
            dest.writeString(purchaserID);
            dest.writeString(purchaserName);
            dest.writeInt(purchaserType);
            dest.writeString(remarks);
            dest.writeString(signDate);
            dest.writeString(signEmployeeName);
            dest.writeString(startDate);
            dest.writeString(shopID);
            dest.writeString(shopName);
            dest.writeInt(status);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ContractBean> CREATOR = new Creator<ContractBean>() {
            @Override
            public ContractBean createFromParcel(Parcel in) {
                return new ContractBean(in);
            }

            @Override
            public ContractBean[] newArray(int size) {
                return new ContractBean[size];
            }
        };

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

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

        public ContractBean() {
        }

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
