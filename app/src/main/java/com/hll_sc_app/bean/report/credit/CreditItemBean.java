package com.hll_sc_app.bean.report.credit;

import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

public class CreditItemBean {
    private String payAmount;
    private String receiveAmount;
    private String unPayAmount;

    CreditItemBean(double payAmount, double receiveAmount, double unPayAmount) {
        this.payAmount = CommonUtils.formatMoney(payAmount);
        this.receiveAmount = CommonUtils.formatMoney(receiveAmount);
        this.unPayAmount = CommonUtils.formatMoney(unPayAmount);
    }

    public String getPayAmount() {
        return payAmount;
    }

    public String getReceiveAmount() {
        return receiveAmount;
    }

    public String getUnPayAmount() {
        return unPayAmount;
    }
}
