package com.hll_sc_app.app.invoice.input;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.invoice.InvoiceHistoryBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/9
 */

public class InvoiceHistoryAdapter extends BaseQuickAdapter<InvoiceHistoryBean, BaseViewHolder> {

    private Pattern mPattern;

    InvoiceHistoryAdapter() {
        super(R.layout.item_invoice_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceHistoryBean item) {
        helper.setText(R.id.iih_title, processItem(item.getInvoiceTitle()))
                .setText(R.id.iih_identifier, item.getTaxpayerNum());
    }

    /**
     * 将匹配到的文本高亮显示
     */
    private SpannableString processItem(String name) {
        SpannableString spannableString = SpannableString.valueOf(name);
        if (mPattern != null) {
            Matcher matcher = mPattern.matcher(name);
            while (matcher.find()) {
                spannableString.setSpan(
                        new ForegroundColorSpan(Color.parseColor(ColorStr.COLOR_ED5655)),
                        matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }

    public void setNewData(@Nullable List<InvoiceHistoryBean> data, String regex) {
        if (CommonUtils.isEmpty(data)) getRecyclerView().setVisibility(View.GONE);
        else {
            getRecyclerView().setVisibility(View.VISIBLE);
            mPattern = Pattern.compile(Pattern.quote(regex));
            super.setNewData(data);
        }
    }
}
