package com.hll_sc_app.bean.staff;

import java.util.List;

/**
 * 合作门店批量掉落公海
 */
public class DropStaffEmployeeReq {
    private String groupID;
    private String salesmanID;
    private List<String> shopIDs;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }

    public List<String> getShopIDs() {
        return shopIDs;
    }

    public void setShopIDs(List<String> shopIDs) {
        this.shopIDs = shopIDs;
    }
}
