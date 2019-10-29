package com.hll_sc_app.bean.report.customreceivequery;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.app.report.customreceivequery.FilterParams;

import java.util.ArrayList;
import java.util.List;

/***
 * 客户收货查询
 * 列表请求响应
 */
public class CustomReceiveListResp {

    private PageInfoBean pageInfo;
    private List<RecordsBean> records;

    public static List<FilterParams.TypeBean> getTypeList() {
        List<FilterParams.TypeBean> typeBeans = new ArrayList<>();
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

    public static String getStatusName(int index) {
        switch (index) {
            case 1:
                return "未审核";
            case 2:
                return "已审核";
            default:
                return "";
        }
    }

    public static String getTypeName(int index) {
        switch (index) {
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

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class PageInfoBean {

        private int pageNo;
        private int pageSize;
        private int total;
        private int pages;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.voucherID);
            dest.writeString(this.groupID);
            dest.writeString(this.voucherNo);
            dest.writeString(this.voucherDate);
            dest.writeInt(this.voucherType);
            dest.writeInt(this.voucherStatus);
            dest.writeString(this.auditTime);
            dest.writeString(this.voucherRemark);
            dest.writeString(this.houseName);
            dest.writeString(this.supplierName);
            dest.writeDouble(this.totalPrice);
            dest.writeString(this.createBy);
            dest.writeString(this.createTime);
        }

        public RecordsBean() {
        }

        protected RecordsBean(Parcel in) {
            this.voucherID = in.readString();
            this.groupID = in.readString();
            this.voucherNo = in.readString();
            this.voucherDate = in.readString();
            this.voucherType = in.readInt();
            this.voucherStatus = in.readInt();
            this.auditTime = in.readString();
            this.voucherRemark = in.readString();
            this.houseName = in.readString();
            this.supplierName = in.readString();
            this.totalPrice = in.readDouble();
            this.createBy = in.readString();
            this.createTime = in.readString();
        }

        public static final Parcelable.Creator<RecordsBean> CREATOR = new Parcelable.Creator<RecordsBean>() {
            @Override
            public RecordsBean createFromParcel(Parcel source) {
                return new RecordsBean(source);
            }

            @Override
            public RecordsBean[] newArray(int size) {
                return new RecordsBean[size];
            }
        };
    }
}
