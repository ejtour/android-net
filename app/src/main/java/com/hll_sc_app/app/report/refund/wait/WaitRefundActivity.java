package com.hll_sc_app.app.report.refund.wait;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.report.refund.BaseRefundActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

@Route(path = RouterConfig.REPORT_WAIT_REFUND)
public class WaitRefundActivity extends BaseRefundActivity {
    @Override
    protected void initView() {
        mTitleBar.setHeaderTitle("待退货统计");
        mRefundTitle.setText("待退统计");
        mRefundNumLabel.setText("待退货单(笔)");
        mCustomerNumLabel.setText("待退客户数");
        mTotalAmountLabel.setText("待退金额（元）");
        mFirstLabel.setText("查看待退客户统计明细");
        mSecondLabel.setText("查看待退商品统计明细");
    }

    @Override
    protected void toFirst() {
        RouterUtil.goToActivity(RouterConfig.REPORT_WAIT_REFUND_CUSTOMER);
    }

    @Override
    protected void toSecond() {
        RouterUtil.goToActivity(RouterConfig.REPORT_WAIT_REFUND_PRODUCT_DETAIL);
    }

    @Override
    public int getFlag() {
        return 1;
    }
}
