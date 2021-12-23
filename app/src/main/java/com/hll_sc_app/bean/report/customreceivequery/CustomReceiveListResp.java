package com.hll_sc_app.bean.report.customreceivequery;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.app.report.customreceivequery.FilterParams;

import java.util.ArrayList;
import java.util.List;

/***
 * 客户收货查询
 * 列表请求响应
 */
public class CustomReceiveListResp {

    private List<RecordsBean> records;

    public static List<FilterParams.TypeBean> getTypeList() {
        List<FilterParams.TypeBean> typeBeans = new ArrayList<>();
        typeBeans.add(new FilterParams.TypeBean("全部", 0));
        typeBeans.add(new FilterParams.TypeBean("验货入库", 1));
        typeBeans.add(new FilterParams.TypeBean("入库冲销", 3));
        typeBeans.add(new FilterParams.TypeBean("入库退货", 4));
        typeBeans.add(new FilterParams.TypeBean("直发单", 13));
        typeBeans.add(new FilterParams.TypeBean("采购验货", 18));
        typeBeans.add(new FilterParams.TypeBean("采购退货", 19));
        typeBeans.add(new FilterParams.TypeBean("直发冲销", 22));
        typeBeans.add(new FilterParams.TypeBean("直发退货", 23));
        typeBeans.add(new FilterParams.TypeBean("赠品入库", 24));
//        typeBeans.add(new FilterParams.TypeBean("代仓验收入库单", 30));
//        typeBeans.add(new FilterParams.TypeBean("代仓入库冲销单", 31));
//        typeBeans.add(new FilterParams.TypeBean("代仓入库退货单", 32));
        typeBeans.add(new FilterParams.TypeBean("司机补货单", 27));
        typeBeans.add(new FilterParams.TypeBean("库存差异调整", 28));
        return typeBeans;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean implements Parcelable {
        private String voucherID;
        private String groupID;
        private String voucherNo;
        private String voucherDate;
        private int voucherType;
        private int voucherStatus;
        private String auditTime;
        private String voucherRemark;
        private String houseName;
        private String supplierName;
        private double totalPrice;
        private String createBy;
        private String createTime;
        /**
         * 0:未对账 1：已对账
         */
        private int checkAccountSupplier;
        /**
         * 0-未结算 1-部分已结算 2-已结算
         */
        private int settlementStatus;
        private String demandName;
        private transient boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        protected RecordsBean(Parcel in) {
            voucherID = in.readString();
            groupID = in.readString();
            voucherNo = in.readString();
            voucherDate = in.readString();
            voucherType = in.readInt();
            voucherStatus = in.readInt();
            auditTime = in.readString();
            voucherRemark = in.readString();
            houseName = in.readString();
            supplierName = in.readString();
            totalPrice = in.readDouble();
            createBy = in.readString();
            createTime = in.readString();
            checkAccountSupplier = in.readInt();
            settlementStatus = in.readInt();
            demandName = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(voucherID);
            dest.writeString(groupID);
            dest.writeString(voucherNo);
            dest.writeString(voucherDate);
            dest.writeInt(voucherType);
            dest.writeInt(voucherStatus);
            dest.writeString(auditTime);
            dest.writeString(voucherRemark);
            dest.writeString(houseName);
            dest.writeString(supplierName);
            dest.writeDouble(totalPrice);
            dest.writeString(createBy);
            dest.writeString(createTime);
            dest.writeInt(checkAccountSupplier);
            dest.writeInt(settlementStatus);
            dest.writeString(demandName);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<RecordsBean> CREATOR = new Creator<RecordsBean>() {
            @Override
            public RecordsBean createFromParcel(Parcel in) {
                return new RecordsBean(in);
            }

            @Override
            public RecordsBean[] newArray(int size) {
                return new RecordsBean[size];
            }
        };

        public String getVoucherStatusName() {
            switch (voucherStatus) {
                case 1:
                    return "未审核";
                case 2:
                    return "已审核";
                default:
                    return "";
            }
        }

        public String getVoucherTypeName() {
            switch (voucherType) {
                case 1:
                    return "验货入库";
                case 3:
                    return "入库冲销";
                case 4:
                    return "入库退货";
                case 13:
                    return "直发单";
                case 18:
                    return "采购验货";
                case 19:
                    return "采购退货";
                case 22:
                    return "直发冲销";
                case 23:
                    return "直发退货";
                case 24:
                    return "赠品入库";
                case 30:
                    return "代仓验收入库单";
                case 31:
                    return "代仓入库冲销单";
                case 32:
                    return "代仓入库退货单";
                case 27:
                    return "司机补货单";
                case 28:
                    return "库存差异调整";
                default:
                    return "";
            }
        }

        public String getWarehouseName() {
            return TextUtils.isEmpty(demandName) ? houseName : demandName;
        }

        public String getDemandName() {
            return demandName;
        }

        public void setDemandName(String demandName) {
            this.demandName = demandName;
        }

        public int getCheckAccountSupplier() {
            return checkAccountSupplier;
        }

        public void setCheckAccountSupplier(int checkAccountSupplier) {
            this.checkAccountSupplier = checkAccountSupplier;
        }

        public int getSettlementStatus() {
            return settlementStatus;
        }

        public void setSettlementStatus(int settlementStatus) {
            this.settlementStatus = settlementStatus;
        }

        public String getVoucherID() {
            return voucherID;
        }

        public void setVoucherID(String voucherID) {
            this.voucherID = voucherID;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public String getVoucherNo() {
            return voucherNo;
        }

        public void setVoucherNo(String voucherNo) {
            this.voucherNo = voucherNo;
        }

        public String getVoucherDate() {
            return voucherDate;
        }

        public void setVoucherDate(String voucherDate) {
            this.voucherDate = voucherDate;
        }

        public int getVoucherType() {
            return voucherType;
        }

        public void setVoucherType(int voucherType) {
            this.voucherType = voucherType;
        }

        public int getVoucherStatus() {
            return voucherStatus;
        }

        public void setVoucherStatus(int voucherStatus) {
            this.voucherStatus = voucherStatus;
        }

        public String getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(String auditTime) {
            this.auditTime = auditTime;
        }

        public String getVoucherRemark() {
            return voucherRemark;
        }

        public void setVoucherRemark(String voucherRemark) {
            this.voucherRemark = voucherRemark;
        }

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public RecordsBean() {
        }

    }
}
