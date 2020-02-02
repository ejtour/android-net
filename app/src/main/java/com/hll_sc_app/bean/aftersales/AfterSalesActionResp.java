package com.hll_sc_app.bean.aftersales;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/17
 */

public class AfterSalesActionResp {
    private List<BillRefundVoBean> billRefundVoList;

    public List<BillRefundVoBean> getBillRefundVoList() {
        return billRefundVoList;
    }

    public void setBillRefundVoList(List<BillRefundVoBean> billRefundVoList) {
        this.billRefundVoList = billRefundVoList;
    }

    public static class BillRefundVoBean{
        private String refundInfo;

        public String getRefundInfo() {
            return refundInfo;
        }

        public void setRefundInfo(String refundInfo) {
            this.refundInfo = refundInfo;
        }
    }
}
