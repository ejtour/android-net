package com.hll_sc_app.app.order.trace;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.trace.OrderTraceBean;
import com.hll_sc_app.utils.ColorStr;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/9
 */

public class OrderTraceAdapter extends BaseQuickAdapter<OrderTraceBean, BaseViewHolder> {
    private int mColor222;
    private int mColor666;
    private int mColor999;

    OrderTraceAdapter(List<OrderTraceBean> list) {
        super(R.layout.item_order_trace, list);
        mColor222 = Color.parseColor(ColorStr.COLOR_222222);
        mColor666 = Color.parseColor(ColorStr.COLOR_666666);
        mColor999 = Color.parseColor(ColorStr.COLOR_999999);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderTraceBean item) {
        int position = helper.getAdapterPosition();
        helper.setVisible(R.id.iot_receive_tag, position == 0)
                .setText(R.id.iot_label, processText(item.getLabel(), item.getDesc()))
                .setVisible(R.id.iot_current_tag, position == 1)
                .setVisible(R.id.iot_earlier_tag, position > 1)
                .setGone(R.id.iot_time, position > 0)
                .setText(R.id.iot_time, item.getTime());
    }

    private SpannableString processText(String label, String desc) {
        StringBuilder builder = new StringBuilder(label);
        if (!TextUtils.isEmpty(desc))
            builder.append("  ").append(desc);
        SpannableString ss = new SpannableString(builder);
        if (TextUtils.isEmpty(desc)) {
            ss.setSpan(new ForegroundColorSpan(mColor666), 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            ss.setSpan(new ForegroundColorSpan(mColor222), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(mColor999), label.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(0.83f), label.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }
}
