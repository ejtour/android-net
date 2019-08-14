package com.hll_sc_app.app.report.customerLack;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.report.customerLack.CustomerLackSummary;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author chukun
 * @since 2019/8/14
 */

public class CustomerLackSummaryAdapter extends BaseQuickAdapter<CustomerLackSummary, BaseViewHolder> {
    CustomerLackSummaryAdapter() {
        super(R.layout.item_report_lack_summary_lack);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerLackSummary item) {
        helper.setText(R.id.rog_shop_name, item.getShopName())
                .setText(R.id.rog_group_name, item.getPurchaserName())
                .setText(R.id.rog_lack_product_num, item.getDeliveryLackKindNum())
                .setText(R.id.rog_total_lack_num, item.getDeliveryLackNum())
                .setText(R.id.rog_lack_amount, String.format("¥%s", CommonUtils.formatMoney(Double.parseDouble(item.getDeliveryLackAmount()))));
    }
}
