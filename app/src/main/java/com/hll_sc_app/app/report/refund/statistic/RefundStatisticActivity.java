package com.hll_sc_app.app.report.refund.statistic;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.report.refund.BaseRefundActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

@Route(path = RouterConfig.REPORT_REFUND_STATISTIC)
public class RefundStatisticActivity extends BaseRefundActivity {
    @Override
    protected void initView() {
        mTitleBar.setHeaderTitle("退货统计");
        mFirstLabel.setText("查看退货统计明细");
        mSecondBtn.setVisibility(View.GONE);
    }

    @Override
    protected void toFirst() {
        RouterUtil.goToActivity(RouterConfig.REPORT_REFUND_STATISTIC_DETAILS);
    }

    @Override
    protected void toSecond() {
        // no-op
    }

    @Override
    public int getFlag() {
        return 2;
    }
}
