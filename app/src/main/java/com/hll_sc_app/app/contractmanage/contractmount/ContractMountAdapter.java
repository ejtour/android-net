package com.hll_sc_app.app.contractmanage.contractmount;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

import static com.hll_sc_app.app.order.common.OrderHelper.getPaymentWay;

public class ContractMountAdapter extends BaseQuickAdapter<OrderResp, BaseViewHolder> {
    public ContractMountAdapter(@Nullable List<OrderResp> data) {
        super(R.layout.list_item_contract_mount,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderResp item) {
        helper.setText(R.id.txt_shop_name,item.getShopName())
                .setText(R.id.txt_money, "¥ "+CommonUtils.formatMoney(item.getTotalAmount()))
                .setText(R.id.txt_order_no,"订单号："+item.getSubBillNo())
                .setText(R.id.txt_status,item.getTranSubBillStatus())
                .setText(R.id.txt_pay_type,String.format("%s (%s) -%s",item.getTranPayType(),getPaymentWay(item.getPaymentWay()),item.getTranSettlementStatus()))
                .setText(R.id.txt_order_time, CalendarUtils.getDateFormatString(item.getSubBillCreateTime(),"yyyyMMddHHmmss","yyyy/MM/dd HH:mm"));



    }
}
