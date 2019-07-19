package com.hll_sc_app.bean.cooperation;

/**
 * 配送方式
 *
 * @author zhuyingsong
 * @date 2019-07-18
 */
public class DeliveryBean {
    /**
     * 配送方式 1-自有物流配送 2-上门自提 3-第三方配送公司 多个逗号分隔
     */
    private String deliveryWay;
    /**
     * 集团ID
     */
    private String groupID;

    public String getDeliveryWay() {
        return deliveryWay;
    }

    public void setDeliveryWay(String deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
