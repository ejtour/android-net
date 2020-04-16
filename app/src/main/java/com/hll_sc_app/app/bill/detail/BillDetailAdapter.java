package com.hll_sc_app.app.bill.detail;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.bill.BillDetailsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

public class BillDetailAdapter extends BaseQuickAdapter<BillDetailsBean, BaseViewHolder> {
    BillDetailAdapter() {
        super(R.layout.item_bill_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillDetailsBean item) {
        boolean isRefund = !TextUtils.isEmpty(item.getRefundBillNo());
        if (isRefund) {
            helper.setText(R.id.ibd_order_no, String.format("退款编号：%s", item.getRefundBillNo()))
                    .setText(R.id.ibd_order_date, String.format("退款日期：%s",
                            DateUtil.getReadableTime(item.getRefundDate(), Constants.SLASH_YYYY_MM_DD)))
                    .setText(R.id.ibd_amount, String.format("-¥%s", CommonUtils.formatMoney(Math.abs(item.getRefundTotalAmount()))));
        } else {
            helper.setText(R.id.ibd_order_no, String.format("订单编号：%s", item.getSubBillNo()))
                    .setText(R.id.ibd_order_date, String.format("订单日期：%s",
                            DateUtil.getReadableTime(item.getSubBillDate(), Constants.SLASH_YYYY_MM_DD)))
                    .setText(R.id.ibd_amount, String.format("¥%s", CommonUtils.formatMoney(item.getBillTotalAmount())));
        }
        helper.setGone(R.id.ibd_divider, mData.indexOf(item) < mData.size() - 1);
    }
}
