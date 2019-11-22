package com.hll_sc_app.app.crm.order.list;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/6
 */

public class CrmOrderListAdapter extends BaseQuickAdapter<OrderResp, BaseViewHolder> {
     CrmOrderListAdapter() {
        super(R.layout.item_crm_order_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderResp item) {
        String name = (TextUtils.isEmpty(item.getStallName()) ? "" : (item.getStallName() + " - ")) + item.getPurchaserName();
        GlideImageView imageView = helper.setText(R.id.col_name, name)
                .setText(R.id.col_status, getStatus(item.getSubBillStatus()))
                .setText(R.id.col_money, processMoney(item.getTotalAmount()))
                .setText(R.id.col_settlement_status, item.getSettlementStatus() == 2 ? "（已结算）" : "（未结算）")
                .setText(R.id.col_order_no, "订单号：" + item.getSubBillNo())
                .setGone(R.id.col_deliver_status, !TextUtils.isEmpty(item.getDistributionStatus()))
                .setGone(R.id.col_after_sales_status, !TextUtils.isEmpty(item.getCurRefundStatus()))
                .setText(R.id.col_deliver_status, item.getDistributionStatus())
                .setText(R.id.col_after_sales_status, item.getCurRefundStatus())
                .setText(R.id.col_extra_info, OrderHelper.handleExtraInfo(item)).getView(R.id.col_logo);
        imageView.setImageURL(item.getImgUrl());
    }

    private SpannableString processMoney(double money) {
        String source = "¥" + CommonUtils.formatMoney(money);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.3f), 1, source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private String getStatus(int status) {
        for (OrderType value : OrderType.values()) {
            if (value.getType() == status) {
                return value.getLabel();
            }
        }
        if (status == 8) return "已拒收";
        return "";
    }
}
