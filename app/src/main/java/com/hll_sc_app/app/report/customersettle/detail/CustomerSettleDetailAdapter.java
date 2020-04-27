package com.hll_sc_app.app.report.customersettle.detail;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/26
 */

class CustomerSettleDetailAdapter extends BaseQuickAdapter<CustomReceiveListResp.RecordsBean, BaseViewHolder> {
    public CustomerSettleDetailAdapter() {
        super(R.layout.item_report_customer_settle_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomReceiveListResp.RecordsBean item) {
        helper.setText(R.id.csd_no, item.getVoucherNo())
                .setText(R.id.csd_label, item.getVoucherTypeName())
                .setText(R.id.csd_amount, String.format("¥%s", CommonUtils.formatMoney(item.getTotalPrice())))
                .setText(R.id.csd_date, DateUtil.getReadableTime(item.getVoucherDate(), Constants.SLASH_YYYY_MM_DD))
                .setText(R.id.csd_status, getStatus(item.getSettlementStatus(), item.getCheckAccountSupplier()));
    }

    private SpannableString getStatus(int settleStatus, int reconciliationStatus) {
        int firstColor = 0, secondColor = 0, thirdColor = 0;
        StringBuilder sb = new StringBuilder();
        if (settleStatus == 0) {
            sb.append("未结算");
            firstColor = Color.parseColor(ColorStr.COLOR_F5A623);
        } else if (settleStatus == 1) {
            sb.append("部分结算");
            firstColor = Color.parseColor(ColorStr.COLOR_5695D2);
        } else if (settleStatus == 2) {
            sb.append("已结算");
            firstColor = Color.parseColor(ColorStr.COLOR_666666);
        }

        secondColor = settleStatus == 2 && reconciliationStatus == 1 ?
                Color.parseColor(ColorStr.COLOR_666666) :
                Color.parseColor(ColorStr.COLOR_DDDDDD);

        sb.append(" / ");

        if (reconciliationStatus == 0) {
            sb.append("未对账");
            thirdColor = Color.parseColor(ColorStr.COLOR_F5A623);
        } else if (reconciliationStatus == 1) {
            sb.append("已对账");
            thirdColor = Color.parseColor(ColorStr.COLOR_666666);
        }

        SpannableString ss = new SpannableString(sb);
        ss.setSpan(new ForegroundColorSpan(firstColor), 0, sb.indexOf("/"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(secondColor), sb.indexOf("/"), sb.indexOf("/") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(thirdColor), sb.indexOf("/") + 1, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
