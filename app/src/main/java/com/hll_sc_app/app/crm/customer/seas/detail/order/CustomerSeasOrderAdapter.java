package com.hll_sc_app.app.crm.customer.seas.detail.order;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderStatus;
import com.hll_sc_app.app.order.details.OrderDetailActivity;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public class CustomerSeasOrderAdapter extends BaseQuickAdapter<OrderResp, BaseViewHolder> {
    CustomerSeasOrderAdapter() {
        super(R.layout.item_crm_customer_seas_order);
        setOnItemClickListener((adapter, view, position) -> {
            OrderResp item = getItem(position);
            if (item != null) OrderDetailActivity.start(item.getSubBillID());
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderResp item) {
        helper.setText(R.id.cso_no, item.getSubBillNo())
                .setText(R.id.cso_status, OrderStatus.getOrderStatus(item.getSubBillStatus()))
                .setText(R.id.cso_num, String.valueOf(item.getProductNum()))
                .setText(R.id.cso_time, DateUtil.getReadableTime(item.getActionTime(), CalendarUtils.FORMAT_YYYY_MM_DD_HH_MM))
                .setText(R.id.cso_amount, String.format("%s(%s结算)",
                        CommonUtils.formatMoney(item.getTotalAmount()), item.getSettlementStatus() == 2 ? "已" : "未"));
    }
}
