package com.hll_sc_app.widget.aftersales;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.common.AfterSalesHelper;
import com.hll_sc_app.app.aftersales.negotiationhistory.NegotiationHistoryActivity;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ThumbnailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public class AfterSalesDetailHeader extends ConstraintLayout {
    @BindView(R.id.sdh_status_info)
    TextView statusInfo;
    @BindView(R.id.sdh_status_desc)
    TextView statusDesc;
    @BindView(R.id.sdh_refund_total_amount_txt)
    TextView refundAmount;
    @BindView(R.id.sdh_refund_remark)
    TextView mRefundRemark;
    @BindView(R.id.sdh_voucher_container)
    ThumbnailView mVouchers;
    @BindView(R.id.sdh_refund_extra_group)
    Group mRefundExtraGroup;
    @BindView(R.id.sdh_total_amount_group)
    Group mAmountGroup;
    @BindView(R.id.sdh_exchange_group)
    Group mExchangeGroup;
    @BindView(R.id.sdh_operation_info)
    TextView mOperationInfo;
    private AfterSalesBean mRecordsBean;

    public AfterSalesDetailHeader(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_after_sales_detail_header, this, true);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, view);
        setBackgroundColor(Color.WHITE);
    }

    public void setData(AfterSalesBean data) {
        mRecordsBean = data;
        StringBuilder statusBuilder = new StringBuilder(AfterSalesHelper.getRefundStatusDesc(data.getRefundBillStatus()));
        if (data.getRefundBillStatus() == 5)
            statusBuilder.insert(0, AfterSalesHelper.getRefundInfoPrefix(data.getRefundBillType()));
        else if (data.getRefundBillStatus() == 7)
            statusBuilder.insert(0, AfterSalesHelper.getCancelRoleDes(data.getCancelRole()));
        // 订单状态
        statusInfo.setText(statusBuilder.toString());
        // 动作时间
        String desc = CalendarUtils.getFormatYyyyMmDdHhMm(String.valueOf(data.getActionTime()));
        if (data.getRefundBillStatus() == 7) {
            // 取消原因
            String cancelDesc = TextUtils.isEmpty(data.getCancelReason()) ? "" : "取消原因：" + data.getCancelReason();
            if (!TextUtils.isEmpty(cancelDesc)) desc += "\n" + cancelDesc;
        } else if (data.getRefundBillStatus() == 6) {
            // 拒绝原因
            String refuseDesc = TextUtils.isEmpty(data.getRefuseReason()) ? "" : "拒绝原因：" + data.getRefuseReason();
            if (!TextUtils.isEmpty(refuseDesc)) desc += "\n" + refuseDesc;
        }
        statusDesc.setText(desc);
        // 退款说明和凭证
        mRefundExtraGroup.setVisibility(TextUtils.isEmpty(data.getRefundExplain().trim()) && TextUtils.isEmpty(data.getRefundVoucher().trim()) ? GONE : VISIBLE);
        // 退款说明
        if (TextUtils.isEmpty(data.getRefundExplain().trim())) {
            mRefundRemark.setVisibility(GONE);
        } else {
            mRefundRemark.setVisibility(VISIBLE);
            mRefundRemark.setText(data.getRefundExplain());
        }
        // 退款凭证
        if (TextUtils.isEmpty(data.getRefundVoucher().trim())) {
            mVouchers.setVisibility(GONE);
        } else {
            mVouchers.setVisibility(VISIBLE);
            String[] array = data.getRefundVoucher().split(",");
            mVouchers.setData(array);
        }
        if (data.getRefundBillType() == 5) { // 换货时
            mAmountGroup.setVisibility(GONE);
            mExchangeGroup.setVisibility(VISIBLE);
        } else {
            // 金额
            refundAmount.setText("¥" + CommonUtils.formatMoney(data.getTotalAmount()));
            mAmountGroup.setVisibility(VISIBLE);
            mExchangeGroup.setVisibility(GONE);
        }
        mOperationInfo.setText(String.format("%s信息", AfterSalesHelper.getRefundInfoPrefix(data.getRefundBillType())));
    }

    @OnClick({R.id.sdh_negotiation_history, R.id.sdh_view_exchange_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sdh_negotiation_history:
                NegotiationHistoryActivity.start(mRecordsBean);
                break;
            case R.id.sdh_view_exchange_order:
                break;
        }
    }
}



