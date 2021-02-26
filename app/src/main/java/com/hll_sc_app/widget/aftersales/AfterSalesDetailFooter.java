package com.hll_sc_app.widget.aftersales;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.common.AfterSalesHelper;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.app.order.details.OrderDetailActivity;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/10
 */

public class AfterSalesDetailFooter extends ConstraintLayout {
    @BindView(R.id.sdf_refund_type)
    TextView mRefundType;
    @BindView(R.id.sdf_refund_id)
    TextView mRefundId;
    @BindView(R.id.sdf_create_time)
    TextView mCreateTime;
    @BindView(R.id.sdf_type)
    TextView mType;
    @BindView(R.id.sdf_refund_reason_label)
    TextView mRefundReasonLabel;
    @BindView(R.id.sdf_refund_reason)
    TextView mRefundReason;
    @BindView(R.id.sdf_refund_amount)
    TextView mRefundAmount;
    @BindView(R.id.sdf_diff_price)
    TextView mDiffPrice;
    @BindView(R.id.sdf_diff_price_group)
    Group mDiffPriceGroup;
    @BindView(R.id.sdf_view_related_bill)
    TextView mViewRelatedBill;
    @BindView(R.id.sdf_related_bill_number)
    TextView mRelatedBillNumber;
    @BindView(R.id.sdf_related_bill_amount)
    TextView mRelatedBillAmount;
    @BindView(R.id.sdf_pay_method)
    TextView mPayMethod;
    @BindView(R.id.sdf_sale_name)
    TextView mSaleName;
    @BindView(R.id.sdf_sale_phone)
    TextView mSalePhone;

    public AfterSalesDetailFooter(Context context) {
        this(context, null);
    }

    public AfterSalesDetailFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AfterSalesDetailFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_after_sales_detail_footer, this, true);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, view);
        setBackgroundColor(Color.WHITE);
        setPadding(0, 0, 0, UIUtils.dip2px(10));
    }

    public void setData(AfterSalesBean data) {
        // 退款类型
        mRefundType.setText(data.getBillSource() == 1 ? "快速退款" : "自由退款");
        // 售后类型
        mType.setText(AfterSalesHelper.getRefundTypeDesc(data.getRefundBillType()));

        // 售后原因
        String reason = data.getRefundReasonDesc();
        if (!TextUtils.isEmpty(reason)) {
            mRefundReasonLabel.setText(String.format("%s原因：", AfterSalesHelper.getReasonPrefix(data.getRefundBillType())));
            mRefundReason.setText(reason);
        }

        // 退款金额
        mRefundAmount.setText(String.format("¥%s", CommonUtils.formatMoney(data.getTotalAmount())));

        // 差价金额
        if (data.getRefundBillType() == 2) {
            mDiffPriceGroup.setVisibility(View.VISIBLE);
            mDiffPrice.setText(String.format("¥%s", CommonUtils.formatMoney(data.getPriceDifferences())));
        }

        // 申请时间
        mCreateTime.setText(DateUtil.getReadableTime(String.valueOf(data.getRefundBillCreateTime())));

        // 原单信息
        mRelatedBillNumber.setText(data.getSubBillNo());
        String subBillID = data.getSubBillID();
        if (!TextUtils.isEmpty(subBillID) && !"0".equals(subBillID)) {
            mViewRelatedBill.setTag(subBillID);
            mViewRelatedBill.setVisibility(VISIBLE);
        } else mViewRelatedBill.setVisibility(GONE);
        mRelatedBillAmount.setText(String.format("¥%s", CommonUtils.formatMoney(data.getSubBillInspectionAmount())));

        // 支付方式
        String payType = OrderHelper.getPayType(data.getPayType()), paymentWay = OrderHelper.getPaymentWay(data.getPaymentWay());
        if (!TextUtils.isEmpty(payType)) {
            mPayMethod.setText(paymentWay.length() > 0 ? payType + "（" + paymentWay + "）" : payType);
        }

        // 退款编号
        mRefundId.setText(data.getRefundBillNo());

        // 销售员信息
        if (data.getSaleInfoVo() != null) {
            mSaleName.setText(data.getSaleInfoVo().getSalesmanName());
            mSalePhone.setText(PhoneUtil.formatPhoneNum(data.getSaleInfoVo().getSalesmanPhone()));
        }
        requestLayout();
    }

    @OnClick(R.id.sdf_view_related_bill)
    public void onViewClicked(View view) {
        if (view.getTag() == null) return;
        OrderDetailActivity.start(view.getTag().toString());
    }
}
