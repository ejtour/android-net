package com.hll_sc_app.app.order.trace;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.order.trace.OrderTraceBean;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.DateUtil;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/9
 */

public class OrderTraceAdapter extends BaseQuickAdapter<OrderTraceBean, BaseViewHolder> {
    private final int mColor222;
    private final int mColor666;
    private final int mColor999;
    private final boolean mIsCrm;
    private final int m5dp;

    OrderTraceAdapter(List<OrderTraceBean> list) {
        super(R.layout.item_order_trace, list);
        mColor222 = Color.parseColor(ColorStr.COLOR_222222);
        mColor666 = Color.parseColor(ColorStr.COLOR_666666);
        mColor999 = Color.parseColor(ColorStr.COLOR_999999);
        mIsCrm = UserConfig.crm();
        m5dp = UIUtils.dip2px(5);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderTraceBean item) {
        int position = mData.indexOf(item);
        boolean isFirst = position == 0;
        boolean isLast = position == mData.size() - 1;
        helper.itemView.setBackgroundResource(
                isLast ? R.drawable.bg_white_radius_half_5_solid :
                        isFirst ? R.drawable.bg_white_radius_top_half_5_solid :
                                android.R.color.white);
        helper.itemView.setPadding(m5dp * 3, isFirst ? m5dp * 4 : 0, m5dp * 3, 0);
        helper.getView(R.id.iot_time).setPadding(0, 0, 0, m5dp * (isLast ? 4 : 6));
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) helper.getView(R.id.iot_div).getLayoutParams();
        params.bottomToBottom = isLast ? R.id.iot_earlier_tag : R.id.iot_time;
        helper.setVisible(R.id.iot_receive_tag, isFirst)
                .setText(R.id.iot_label, mIsCrm ? processText(item.getOpTypeName(), item.getTitle()) :
                        processText(item.getSupplyTitle(), position))
                .setVisible(R.id.iot_current_tag, position == 1)
                .setVisible(R.id.iot_earlier_tag, position > 1)
                .setText(R.id.iot_extra, item.getExtra())
                .setGone(R.id.iot_extra, !TextUtils.isEmpty(item.getExtra()))
                .setText(R.id.iot_time, DateUtil.getReadableTime(item.getOpTime()));
    }

    private SpannableString processText(String label, int position) {
        SpannableString ss = new SpannableString(label);
        if (position == 0) {
            ss.setSpan(new ForegroundColorSpan(mColor666), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            ss.setSpan(new ForegroundColorSpan(mColor222), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(1.083f), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
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
