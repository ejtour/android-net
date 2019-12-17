package com.hll_sc_app.app.report.lack.customer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.report.lack.CustomerLackBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

public class CustomerLackAdapter extends BaseQuickAdapter<CustomerLackBean, BaseViewHolder> {
    CustomerLackAdapter() {
        super(R.layout.item_report_lack_summary_lack);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerLackBean item) {
        helper.setText(R.id.rog_shop_name, item.getShopName())
                .setText(R.id.rog_group_name, item.getPurchaserName())
                .setText(R.id.rog_lack_product_num, CommonUtils.formatNum(item.getDeliveryLackKindNum()))
                .setText(R.id.rog_total_lack_num, CommonUtils.formatNum(item.getDeliveryLackNum()))
                .setText(R.id.rog_lack_amount, String.format("¥%s", CommonUtils.formatMoney(item.getDeliveryLackAmount())));
    }
}
