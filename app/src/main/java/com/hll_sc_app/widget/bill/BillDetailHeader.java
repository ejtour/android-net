package com.hll_sc_app.widget.bill;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.bean.bill.BillStatus;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

public class BillDetailHeader extends ConstraintLayout {
    @BindView(R.id.bdh_icon)
    GlideImageView mIcon;
    @BindView(R.id.bdh_shop_name)
    TextView mShopName;
    @BindView(R.id.bdh_warehouse_tag)
    TextView mWarehouseTag;
    @BindView(R.id.bdh_group_name)
    TextView mGroupName;
    @BindView(R.id.bdh_bill_cycle)
    TextView mBillCycle;
    @BindView(R.id.bdh_status)
    TextView mStatus;
    @BindView(R.id.bdh_bill_date)
    TextView mBillDate;
    @BindView(R.id.bdh_settlement_date)
    TextView mSettlementDate;
    @BindView(R.id.bdh_total_amount)
    TextView mTotalAmount;
    @BindView(R.id.bdh_income)
    TextView mIncome;
    @BindView(R.id.bdh_refund)
    TextView mRefund;

    public BillDetailHeader(Context context) {
        this(context, null);
    }

    public BillDetailHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BillDetailHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_bill_detail_header, this);
        ButterKnife.bind(this, view);
        setBackgroundColor(Color.WHITE);
        setPadding(0, 0, 0, UIUtils.dip2px(10));
    }

    public void setData(BillBean data) {
        StringBuilder builder = new StringBuilder();
        if (data.getIsConfirm() == 1) builder.append("未确认/");
        else if (data.getIsConfirm() == 2) builder.append("已确认/");
        if (data.getSettlementStatus() == BillStatus.NOT_SETTLE) builder.append("未结算");
        else if (data.getSettlementStatus() == BillStatus.SETTLED) builder.append("已结算");
        mIcon.setImageURL(data.getGroupLogoUrl());
        mShopName.setText(data.getShopName());
        mGroupName.setText(data.getGroupName());
        mStatus.setText(builder);
        mWarehouseTag.setVisibility(data.getBillStatementFlag() == 1 ? VISIBLE : GONE);
        mBillDate.setText(String.format("账期日：%s", convertDate(data.getBillCreateTime())));
        mSettlementDate.setText(String.format("结算日：%s", convertDate(data.getPaymentSettleDay())));
        mBillCycle.setText(String.format("账单周期：%s - %s", convertDate(data.getStartPaymentDay()), convertDate(data.getEndPaymentDay())));
        mTotalAmount.setText(String.format("¥%s", data.getTotalAmount()));
        mIncome.setText(String.format("¥%s", data.getTotalIncomeAmount()));
        mRefund.setText(String.format("¥%s", data.getTotalRefundAmount()));
    }

    private String convertDate(String time) {
        return DateUtil.getReadableTime(time, Constants.SLASH_YYYY_MM_DD);
    }
}
