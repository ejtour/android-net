package com.hll_sc_app.app.invoice.select.order;

import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

public class SelectOrderActivity {
    /**
     * @param shopID 门店id
     */
    public static void start(String shopID) {
        RouterUtil.goToActivity(RouterConfig.INVOICE_SELECT_ORDER, shopID);
    }
}
