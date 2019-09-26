package com.hll_sc_app.app.order.place.commit;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.bean.order.place.OrderCommitBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/26
 */

public class PlaceOrderCommitAdapter extends BaseQuickAdapter<OrderCommitBean.SubBillBean, BaseViewHolder> {
    PlaceOrderCommitAdapter() {
        super(R.layout.item_order_place_commit);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCommitBean.SubBillBean item) {
        helper.setText(R.id.opc_pay_method, OrderHelper.getPayType(item.getPayType()))
                .setGone(R.id.opc_self_lift_tag, item.getDeliverType() == 2)
                .setText(R.id.opc_order_no, item.getSubBillNo())
                .setText(R.id.opc_amount, String.format("¥%s", CommonUtils.formatMoney(item.getTotalAmount())))
                .setText(R.id.opc_shop_name, item.getSupplyShopName());
    }
}
