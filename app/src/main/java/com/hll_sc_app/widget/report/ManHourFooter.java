package com.hll_sc_app.widget.report;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/29
 */

public class ManHourFooter extends LinearLayout {
    private TextView mAdd;
    private TextView mTip;

    public ManHourFooter(Context context) {
        this(context, null);
    }

    public ManHourFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ManHourFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        int _10DP = UIUtils.dip2px(10);
        setPadding(_10DP, _10DP, _10DP, _10DP);
        setGravity(Gravity.END);

        mTip = new TextView(context);
        mTip.setTextSize(10);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTip.setTextColor(ContextCompat.getColor(context, R.color.color_aeaeae));
        addView(mTip, lp);

        mAdd = new TextView(context);
        mAdd.setTextSize(13);
        mAdd.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = _10DP;
        addView(mAdd, lp);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mAdd.setOnClickListener(l);
    }

    public void setText(String tip, String button) {
        mTip.setText(String.format("(%s)", tip));
        mAdd.setText(button);
    }

    public void setAddable(boolean addable) {
        mAdd.setVisibility(addable ? VISIBLE : GONE);
    }
}
