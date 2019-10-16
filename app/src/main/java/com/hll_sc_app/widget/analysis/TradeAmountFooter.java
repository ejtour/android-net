package com.hll_sc_app.widget.analysis;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/15
 */

public class TradeAmountFooter extends ConstraintLayout {
    @BindView(R.id.taf_chart)
    CombinedChart mChart;
    @BindView(R.id.taf_tip_1)
    TextView mTip1;
    @BindView(R.id.taf_tip_2)
    TextView mTip2;
    @BindView(R.id.taf_tip_3)
    TextView mTip3;
    @BindView(R.id.taf_tip_4)
    TextView mTip4;
    @BindView(R.id.taf_tip_5)
    TextView mTip5;

    public TradeAmountFooter(Context context) {
        this(context, null);
    }

    public TradeAmountFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TradeAmountFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View v = View.inflate(context, R.layout.view_trade_amount_footer, this);
        ButterKnife.bind(this, v);
        setPadding(0, UIUtils.dip2px(20), 0, UIUtils.dip2px(20));
    }

    public void setData(CharSequence... s) {
        mTip1.setText(s[0]);
        mTip2.setText(s[1]);
        mTip3.setText(s[2]);
        mTip4.setText(s[3]);
        mTip5.setText(s[4]);
    }
}
