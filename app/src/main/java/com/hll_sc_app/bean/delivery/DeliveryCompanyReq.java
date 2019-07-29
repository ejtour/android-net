package com.hll_sc_app.bean.delivery;

import java.util.List;

/**
 * 配送公司
 *
 * @author zhuyingsong
 * @date 2019-07-27
 */
public class DeliveryCompanyReq {
    private String groupID;
    private List<String> deliveryCompanyIDs;
    private List<String> deliveryCompanyName;

    public List<String> getDeliveryCompanyName() {
        return deliveryCompanyName;
    }

    public void setDeliveryCompanyName(List<String> deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public List<String> getDeliveryCompanyIDs() {
        return deliveryCompanyIDs;
    }

    public void setDeliveryCompanyIDs(List<String> deliveryCompanyIDs) {
        this.deliveryCompanyIDs = deliveryCompanyIDs;
    }
}
