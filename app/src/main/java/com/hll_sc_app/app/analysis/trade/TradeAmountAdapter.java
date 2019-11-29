package com.hll_sc_app.app.analysis.trade;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class TradeAmountAdapter extends BaseQuickAdapter<AnalysisBean, BaseViewHolder> {
    private int mTimeType;

    TradeAmountAdapter() {
        super(R.layout.item_trade_amount);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnalysisBean item) {
        helper.setText(R.id.ita_date, item.getDateRange(mTimeType))
                .setText(R.id.ita_growth, item.getAmountRate())
                .setTextColor(R.id.ita_growth, Color.parseColor(item.getAmountRate().startsWith("-") ? ColorStr.COLOR_5CDBD3 : ColorStr.COLOR_FF6562))
                .setText(R.id.ita_amount, processText(item.getValidTradeAmount()));
    }

    public void setNewData(@Nullable List<AnalysisBean> data, int timeType) {
        mTimeType = timeType;
        super.setNewData(data);
    }

    private SpannableString processText(double money) {
        String source = "¥" + CommonUtils.formatMoney(money);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(0.62f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(0.62f), source.indexOf("."), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
