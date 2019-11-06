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
import com.hll_sc_app.citymall.util.CommonUtils;
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
    @BindView(R.id.bdh_group_name)
    TextView mGroupName;
    @BindView(R.id.bdh_bill_cycle)
    TextView mBillCycle;
    @BindView(R.id.bdh_status)
    TextView mStatus;
    @BindView(R.id.bdh_bill_date)
    TextView mBillDate;
    @BindView(R.id.bdh_total_amount)
    TextView mTotalAmount;
    @BindView(R.id.bdh_income)
    TextView mIncome;
    @BindView(R.id.bdh_refund)
    TextView mRefund;
    @BindView(R.id.bdh_bill_type)
    TextView mTxtBillType;
    @BindView(R.id.bdh_bill_receive)
    TextView mTxtBillReceive;

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
        else builder.append("部分结算");
        mIcon.setImageURL(data.getGroupLogoUrl());
        mShopName.setText(data.getShopName());
        mGroupName.setText(data.getPurchaserName());
        mStatus.setText(builder);
        mBillDate.setText(String.format("账单日：%s", convertDate(data.getBillCreateTime())));
        mBillCycle.setText(String.format("账单周期：%s - %s", convertDate(data.getStartPaymentDay()), convertDate(data.getEndPaymentDay())));
        mTotalAmount.setText(String.format("¥%s", CommonUtils.formatMoney(data.getTotalAmount())));
        mIncome.setText(String.format("¥%s", CommonUtils.formatMoney(data.getTotalIncomeAmount())));
        mRefund.setText(String.format("¥%s", CommonUtils.formatMoney(data.getTotalRefundAmount())));

        // 代仓类型
        if (data.getBillStatementFlag() == 1) {
            mTxtBillType.setVisibility(VISIBLE);
            mTxtBillType.setText("账单类型：代仓对账单");
            mTxtBillReceive.setText(String.format("收款方：%s", data.getPayee() == 0 ? "代仓代收款" : "货主收款"));
        } else {
            mTxtBillType.setVisibility(GONE);
            mTxtBillReceive.setVisibility(GONE);
        }
    }

    private String convertDate(String time) {
        return DateUtil.getReadableTime(time, Constants.SLASH_YYYY_MM_DD);
    }
}
