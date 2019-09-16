package com.hll_sc_app.bean.aftersales;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/12
 */

public class AfterSalesReasonBean {
    private int refundBillType;
    private List<ReasonBean> refundReasons;

    public int getRefundBillType() {
        return refundBillType;
    }

    public void setRefundBillType(int refundBillType) {
        this.refundBillType = refundBillType;
    }

    public List<ReasonBean> getRefundReasons() {
        return refundReasons;
    }

    public void setRefundReasons(List<ReasonBean> refundReasons) {
        this.refundReasons = refundReasons;
    }

    public static class ReasonBean {
        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
