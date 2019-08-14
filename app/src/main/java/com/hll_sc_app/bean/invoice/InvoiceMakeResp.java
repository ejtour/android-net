package com.hll_sc_app.bean.invoice;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/14
 */

public class InvoiceMakeResp {

    private String id;
    private List<InvoiceMakeBean> errorBillList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<InvoiceMakeBean> getErrorBillList() {
        return errorBillList;
    }

    public void setErrorBillList(List<InvoiceMakeBean> errorBillList) {
        this.errorBillList = errorBillList;
    }

    public static class InvoiceMakeBean {
        private String reason;
        private String subBillID;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getSubBillID() {
            return subBillID;
        }

        public void setSubBillID(String subBillID) {
            this.subBillID = subBillID;
        }
    }
}
