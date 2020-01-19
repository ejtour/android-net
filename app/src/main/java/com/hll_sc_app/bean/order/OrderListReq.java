package com.hll_sc_app.bean.order;

import java.util.List;

public class OrderListReq {
    private String billSource;
    private String groupID;
    private String pageNum;
    private String pageSize;
    private String purchaserIDs;
    private String shopID;
    private String subBillCreateTimeStart;
    private String subBillCreateTimeEnd;
    private List<Integer> subBillStatusList;

    public String getBillSource() {
        return billSource;
    }

    public void setBillSource(String billSource) {
        this.billSource = billSource;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPurchaserIDs() {
        return purchaserIDs;
    }

    public void setPurchaserIDs(String purchaserIDs) {
        this.purchaserIDs = purchaserIDs;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getSubBillCreateTimeStart() {
        return subBillCreateTimeStart;
    }

    public void setSubBillCreateTimeStart(String subBillCreateTimeStart) {
        this.subBillCreateTimeStart = subBillCreateTimeStart;
    }

    public String getSubBillCreateTimeEnd() {
        return subBillCreateTimeEnd;
    }

    public void setSubBillCreateTimeEnd(String subBillCreateTimeEnd) {
        this.subBillCreateTimeEnd = subBillCreateTimeEnd;
    }

    public List<Integer> getSubBillStatusList() {
        return subBillStatusList;
    }

    public void setSubBillStatusList(List<Integer> subBillStatusList) {
        this.subBillStatusList = subBillStatusList;
    }
}
