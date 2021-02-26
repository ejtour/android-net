package com.hll_sc_app.widget.daily;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.ColorStr;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public class CrmDailyHeader extends ConstraintLayout {
    @BindView(R.id.cdh_date_first)
    TextView mDateFirst;
    @BindView(R.id.cdh_date_last)
    TextView mDateLast;
    @BindView(R.id.cdh_btn)
    TextView mBtn;
    private OnClickListener mListener;

    public CrmDailyHeader(Context context) {
        this(context, null);
    }

    public CrmDailyHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CrmDailyHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundResource(R.drawable.base_bg_white_radius_5_solid);
        View view = View.inflate(context, R.layout.view_crm_daily_header, this);
        ButterKnife.bind(this, view);
        String source = "今天还没写日报噢...快来写日报吧";
        SpannableString spannableString = new SpannableString(source);
        spannableString.setSpan(new MyClickSpan(), source.indexOf("来") + 1, source.indexOf("吧"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBtn.setText(spannableString);
    }

    public void refreshDate() {
        Date date = new Date();
        mDateFirst.setText(CalendarUtils.format(date, "yyyy/MM\nEEEE"));
        mDateLast.setText(CalendarUtils.format(date, "dd"));
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mListener = l;
    }

    private class MyClickSpan extends ClickableSpan {
        @Override
        public void onClick(@NonNull View widget) {
            mListener.onClick(widget);
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.parseColor(ColorStr.COLOR_5695D2));
            ds.setUnderlineText(true);
        }
    }
}
