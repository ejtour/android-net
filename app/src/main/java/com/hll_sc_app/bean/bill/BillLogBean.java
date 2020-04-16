package com.hll_sc_app.bean.bill;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/9
 */

public class BillLogBean {
    private String createBy;
    private String createTime;
    private String groupID;
    private String operateLog;
    private String operateName;
    private int operateType;
    private int originator;
    private String purchaserID;
    private String shopID;
    private String showLog;
    private String statementID;

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

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getOperateLog() {
        return operateLog;
    }

    public void setOperateLog(String operateLog) {
        this.operateLog = operateLog;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public int getOriginator() {
        return originator;
    }

    public void setOriginator(int originator) {
        this.originator = originator;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShowLog() {
        return showLog;
    }

    public void setShowLog(String showLog) {
        this.showLog = showLog;
    }

    public String getStatementID() {
        return statementID;
    }

    public void setStatementID(String statementID) {
        this.statementID = statementID;
    }
}
