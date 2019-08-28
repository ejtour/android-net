package com.hll_sc_app.app.report.purchase;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.report.purchase.PurchaseBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/27
 */

public class PurchaseSummaryAdapter extends BaseQuickAdapter<PurchaseBean, BaseViewHolder> {
    private int mNumColor;

    PurchaseSummaryAdapter() {
        super(R.layout.item_report_purchase_summary);
        mNumColor = Color.parseColor(ColorStr.COLOR_222222);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.rps_modify_logistics)
                .addOnClickListener(R.id.rps_modify_amount);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaseBean item) {
        boolean isToday = CalendarUtils.toLocalDate(new Date()).equals(item.getDate());
        helper.setText(R.id.rps_amount, processText(1.6f, "\n采购金额(元)", CommonUtils.formatMoney(item.getPurchaseAmount())))
                .setText(R.id.rps_people_num, processText(1.3f, "\n采购人数", String.valueOf(item.getPurchaserNum())))
                .setText(R.id.rps_people_effect, processText(1.3f, "\n采购人效", CommonUtils.formatNumber(item.getPurchaserEfficiency())))
                .setText(R.id.rps_car_num, processText(1.3f, "\n采购车辆数", String.valueOf(item.getCarNums())))
                .setText(R.id.rps_logistics_fee, processText(1.3f, "\n采购物流费用(元)", CommonUtils.formatMoney(item.getLogisticsCost())))
                .setText(R.id.rps_time, DateUtil.getReadableTime(item.getDate(), Constants.SLASH_YYYY_MM_DD))
                .setGone(R.id.rps_modify_group, isToday);
        helper.itemView.setPadding(0, 0, 0, isToday ? UIUtils.dip2px(10) : 0);
    }

    private SpannableString processText(float proportion, String postfix, String num) {
        String source = num + postfix;
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(proportion), 0, num.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mNumColor), 0, num.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
