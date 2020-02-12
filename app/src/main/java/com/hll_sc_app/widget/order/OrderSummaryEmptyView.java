package com.hll_sc_app.widget.order;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.impl.IStringListener;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/19
 */

public class OrderSummaryEmptyView extends ConstraintLayout {
    @BindViews({R.id.ose_group, R.id.ose_shipper})
    List<TextView> mButtons;
    private int mCurIndex = 1;
    private IStringListener mListener;

    public OrderSummaryEmptyView(Context context) {
        this(context, null);
    }

    public OrderSummaryEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderSummaryEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_order_summary_empty, this);
        ButterKnife.bind(this, view);
        setBackgroundColor(Color.WHITE);
    }

    @OnClick({R.id.ose_group, R.id.ose_shipper})
    public void onViewClicked(View v) {
        ButterKnife.apply(mButtons, (view, index) -> {
            view.setBackgroundResource(0);
            view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.color_666666));
        });
        v.setBackgroundResource(R.drawable.bg_tip_blue_solid);
        ((TextView) v).setTextColor(Color.WHITE);
        if (mCurIndex != mButtons.indexOf(v)) {
            mCurIndex = mButtons.indexOf(v);
            if (mListener != null) {
                mListener.callback(v.getTag().toString());
            }
        }
    }

    public void setStringListener(IStringListener listener) {
        mListener = listener;
    }

    public int getCurIndex() {
        return mCurIndex;
    }

    public void setCurIndex(int curIndex) {
        mButtons.get(curIndex).performClick();
    }
}
