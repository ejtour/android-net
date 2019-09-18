package com.hll_sc_app.app.aftersales.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */
@IntDef({AfterSalesType.ORDER_REJECT, AfterSalesType.RETURN_GOODS, AfterSalesType.RETURN_DEPOSIT})
@Retention(RetentionPolicy.SOURCE)
public @interface AfterSalesType {
    int ORDER_REJECT = 0;
    int RETURN_GOODS = 3;
    int RETURN_DEPOSIT = 4;
}
