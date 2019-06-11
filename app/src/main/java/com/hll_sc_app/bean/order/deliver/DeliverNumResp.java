package com.hll_sc_app.bean.order.deliver;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */

public class DeliverNumResp {
    private List<DeliverType> deliverTypes;
    private int pendDeliveryNum;

    public List<DeliverType> getDeliverTypes() {
        return deliverTypes;
    }

    public void setDeliverTypes(List<DeliverType> deliverTypes) {
        this.deliverTypes = deliverTypes;
    }

    public int getPendDeliveryNum() {
        return pendDeliveryNum;
    }

    public void setPendDeliveryNum(int pendDeliveryNum) {
        this.pendDeliveryNum = pendDeliveryNum;
    }

    public static class DeliverType {
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
