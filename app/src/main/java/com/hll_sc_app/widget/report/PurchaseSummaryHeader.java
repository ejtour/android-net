package com.hll_sc_app.widget.report;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.report.purchase.PurchaseSummaryResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/27
 */

public class PurchaseSummaryHeader extends ConstraintLayout {
    @BindView(R.id.psh_amount)
    TextView mAmount;
    @BindView(R.id.psh_people_num)
    TextView mPeopleNum;
    @BindView(R.id.psh_people_effect)
    TextView mPeopleEffect;
    @BindView(R.id.psh_car_num)
    TextView mCarNum;
    @BindView(R.id.psh_logistics_fee)
    TextView mLogisticsFee;

    public PurchaseSummaryHeader(Context context) {
        this(context, null);
    }

    public PurchaseSummaryHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PurchaseSummaryHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_report_purchase_summary_header, this);
        ButterKnife.bind(this, view);
        setPadding(0, 0, 0, UIUtils.dip2px(20));
        setBackgroundResource(R.drawable.base_bg_white_radius_5_solid);
    }

    public void setData(PurchaseSummaryResp data) {
        mAmount.setText(CommonUtils.formatMoney(data.getTotalPurchaseAmount()));
        mPeopleNum.setText(String.valueOf(data.getTotalPurchaserNum()));
        mPeopleEffect.setText(CommonUtils.formatNumber(data.getTotalPurchaserEfficiency()));
        mCarNum.setText(String.valueOf(data.getTotalCarNums()));
        mLogisticsFee.setText(CommonUtils.formatMoney(data.getTotalLogisticsCost()));
    }
}
