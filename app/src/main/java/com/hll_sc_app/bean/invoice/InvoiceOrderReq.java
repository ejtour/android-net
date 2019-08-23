package com.hll_sc_app.bean.invoice;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/23
 */

public class InvoiceOrderReq {
    private String groupID;
    private int pageNum;
    private int pageSize = 20;
    private String salesmanID;
    private List<String> shopIDList;
    private int statstFlag = 1;
    private String subBillDateStart;
    private String subBillDateEnd;

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

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }

    public List<String> getShopIDList() {
        return shopIDList;
    }

    public void setShopIDList(List<String> shopIDList) {
        this.shopIDList = shopIDList;
    }

    public int getStatstFlag() {
        return statstFlag;
    }

    public void setStatstFlag(int statstFlag) {
        this.statstFlag = statstFlag;
    }

    public String getSubBillDateStart() {
        return subBillDateStart;
    }

    public void setSubBillDateStart(String subBillDateStart) {
        this.subBillDateStart = subBillDateStart;
    }

    public String getSubBillDateEnd() {
        return subBillDateEnd;
    }

    public void setSubBillDateEnd(String subBillDateEnd) {
        this.subBillDateEnd = subBillDateEnd;
    }
}
