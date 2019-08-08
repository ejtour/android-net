package com.hll_sc_app.app.bill.list;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/7
 */

public class BillListAdapter extends BaseQuickAdapter<BillBean, BaseViewHolder> {
    private static final String[] WEEK_ARRAY = {"每周日", "每周一", "每周二", "每周三", "每周四", "每周五", "每周六"};

    BillListAdapter() {
        super(R.layout.item_bill_list);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.ibl_confirm)
                .addOnClickListener(R.id.ibl_view_detail);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, BillBean item) {
        StringBuilder builder = new StringBuilder();
        if (item.getIsConfirm() == 1) builder.append("未确认/");
        else if (item.getIsConfirm() == 2) builder.append("已确认/");
        if (item.getSettlementStatus() == 1) builder.append("未结算");
        else if (item.getSettlementStatus() == 2) builder.append("已结算");
        String status = builder.toString();

        builder.delete(0, status.length());
        String flag = item.getAccountDayFlag();
        String payFlag = flag.substring(0, 1);
        int payDate = Integer.parseInt(flag.substring(2));
        if ("1".equals(payFlag)) builder.append("周结,").append(WEEK_ARRAY[payDate]);
        else if ("2".equals(payFlag)) builder.append("月结,每月").append(payDate).append("号");

        ((GlideImageView) helper.setText(R.id.ibl_shop_name, item.getShopName())
                .setText(R.id.ibl_group_name, item.getGroupName())
                .setText(R.id.ibl_time, CalendarUtils.getDateFormatString(item.getBillCreateTime(),
                        Constants.UNSIGNED_YYYY_MM_DD, "yy/MM/dd"))
                .setText(R.id.ibl_status, status)
                .setText(R.id.ibl_bill_date, builder)
                .setText(R.id.ibl_bill_num, CommonUtils.formatNumber(item.getBillNum()))
                .setText(R.id.ibl_bill_amount, String.format("¥%s", CommonUtils.formatMoney(item.getTotalAmount())))
                .setGone(R.id.ibl_confirm, item.getSettlementStatus() == 1)
                .setGone(R.id.ibl_check_box, item.getSettlementStatus() == 1)
                .getView(R.id.ibl_icon)).setImageURL(item.getGroupLogoUrl());
    }
}
