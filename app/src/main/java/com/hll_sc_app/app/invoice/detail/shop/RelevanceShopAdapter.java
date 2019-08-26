package com.hll_sc_app.app.invoice.detail.shop;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.invoice.InvoiceShopBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/23
 */

public class RelevanceShopAdapter extends BaseQuickAdapter<InvoiceShopBean, BaseViewHolder> {
    public RelevanceShopAdapter() {
        super(R.layout.item_invoice_relevance_shop);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceShopBean item) {
        helper.setText(R.id.irs_shop_name, item.getShopName())
                .setText(R.id.irs_bill_num, String.format("订单数：%s", item.getBillCount()));
    }
}
