package com.hll_sc_app.app.report.orderGoods;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class OrderGoodsAdapter extends BaseQuickAdapter<OrderGoodsBean, BaseViewHolder> {
    OrderGoodsAdapter() {
        super(R.layout.item_order_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderGoodsBean item) {
        helper.setText(R.id.iog_shop_name, item.getShopName())
                .setText(R.id.iog_group_name, item.getPurchaserName())
                .setText(R.id.iog_product_num, CommonUtils.formatNum(item.getSkuNum()))
                .setText(R.id.iog_inspection_num, CommonUtils.formatNum(item.getInspectionNum()))
                .setText(R.id.iog_amount, String.format("¥%s", CommonUtils.formatMoney(item.getInspectionAmount())));
    }
}
