package com.hll_sc_app.bean.report.warehouse;

public class WareHouseShipperReq {

    private String groupID;
    private int actionType;
    private String name;
    private Integer status;
    private int isSizeLimit;

    public int getIsSizeLimit() {
        return isSizeLimit;
    }

    public void setIsSizeLimit(int isSizeLimit) {
        this.isSizeLimit = isSizeLimit;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
