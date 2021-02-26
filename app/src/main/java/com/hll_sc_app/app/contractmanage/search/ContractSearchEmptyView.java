package com.hll_sc_app.app.contractmanage.search;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.hll_sc_app.R;
import com.hll_sc_app.impl.IStringListener;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.ViewCollections;

public class ContractSearchEmptyView extends ConstraintLayout {
    @BindViews({R.id.ose_filter_1, R.id.ose_filter_2, R.id.ose_filter_3})
    List<TextView> mButtons;
    private int mCurIndex = -1;
    private IStringListener mListener;

    public ContractSearchEmptyView(Context context) {
        this(context, null);
    }

    public ContractSearchEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContractSearchEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_contract_search_empty, this);
        ButterKnife.bind(this, view);
        setBackgroundColor(Color.WHITE);
    }

    @OnClick({R.id.ose_filter_1, R.id.ose_filter_2, R.id.ose_filter_3})
    public void onViewClicked(View v) {
        ViewCollections.run(mButtons, (view, index) -> {
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
