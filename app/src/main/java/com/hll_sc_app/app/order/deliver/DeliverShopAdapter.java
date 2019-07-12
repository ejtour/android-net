package com.hll_sc_app.app.order.deliver;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.deliver.DeliverShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */

public class DeliverShopAdapter extends BaseQuickAdapter<DeliverShopResp, BaseViewHolder> {
    DeliverShopAdapter() {
        super(R.layout.item_order_deliver_shop);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeliverShopResp item) {
        helper.setText(R.id.ods_name, item.getShopName())
                .setText(R.id.ods_num, String.format("x %s %s", CommonUtils.formatNum(item.getProductNum()), item.getSaleUnitName()));
    }
}
