package com.hll_sc_app.bean.order.summary;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public class OrderSummaryWrapper extends SectionEntity<SummaryShopBean> {
    private SummaryPurchaserBean purchaser;

    public OrderSummaryWrapper(boolean isHeader, SummaryPurchaserBean bean) {
        super(isHeader, null);
        purchaser = bean;
    }

    public OrderSummaryWrapper(SummaryShopBean summaryShopBean) {
        super(summaryShopBean);
    }

    public SummaryPurchaserBean getPurchaser() {
        return purchaser;
    }
}
