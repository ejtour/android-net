package com.hll_sc_app.bean.order.deliver;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class ExpressResp {
    private List<ExpressBean> deliveryCompanyList;

    public List<ExpressBean> getDeliveryCompanyList() {
        return deliveryCompanyList;
    }

    public void setDeliveryCompanyList(List<ExpressBean> deliveryCompanyList) {
        this.deliveryCompanyList = deliveryCompanyList;
    }

    public static class ExpressBean {
        private String deliveryCompanyName;
        private String groupID;
        private String id;

        public String getDeliveryCompanyName() {
            return deliveryCompanyName;
        }

        public void setDeliveryCompanyName(String deliveryCompanyName) {
            this.deliveryCompanyName = deliveryCompanyName;
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
    }
}
