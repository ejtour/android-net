package com.hll_sc_app.app.invoice.common;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

public class InvoiceHelper {

    public static String getReturnType(int type) {
        switch (type) {
            case 1:
                return "现金";
            case 2:
                return "银行转账";
            case 3:
                return "支票";
            default:
                return "其他";
        }
    }
}
