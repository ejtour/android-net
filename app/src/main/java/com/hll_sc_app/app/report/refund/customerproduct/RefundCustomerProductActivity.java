package com.hll_sc_app.app.report.refund.customerproduct;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.report.refund.BaseRefundActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

@Route(path = RouterConfig.REPORT_REFUND_CUSTOMER_PRODUCT)
public class RefundCustomerProductActivity extends BaseRefundActivity {

    @Override
    protected void initView() {
        // no-op
    }

    @Override
    protected void toFirst() {
        RouterUtil.goToActivity(RouterConfig.REPORT_REFUNDED_CUSTOMER_DETAIL);
    }

    @Override
    protected void toSecond() {
        RouterUtil.goToActivity(RouterConfig.REPORT_REFUNDED_PRODUCT_DETAIL);
    }

    @Override
    public int getFlag() {
        return 3;
    }
}
