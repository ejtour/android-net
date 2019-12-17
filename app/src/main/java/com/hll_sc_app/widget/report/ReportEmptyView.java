package com.hll_sc_app.widget.report;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/6
 */

public class ReportEmptyView extends LinearLayout {
    @BindView(R.id.vre_tip)
    TextView mTip;

    public ReportEmptyView(Context context) {
        this(context, null);
    }

    public ReportEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReportEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        View view = View.inflate(context, R.layout.view_report_empty, this);
        ButterKnife.bind(this, view);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ReportEmptyView);
        mTip.setText(array.getString(R.styleable.ReportEmptyView_rev_tip));
        array.recycle();
    }

    public void setTip(CharSequence s){
        mTip.setText(s);
    }
}
