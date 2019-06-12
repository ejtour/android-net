package com.hll_sc_app.widget.order;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */

public class OrderDetailFooter extends ConstraintLayout {
    @BindView(R.id.odf_remark)
    TextView mRemark;
    @BindView(R.id.odf_deliver_amount_label)
    TextView mDeliverAmountLabel;
    @BindView(R.id.odf_order_amount)
    TextView mOrderAmount;
    @BindView(R.id.odf_deliver_amount)
    TextView mDeliverAmount;
    @BindView(R.id.odf_sign_amount)
    TextView mSignAmount;
    @BindView(R.id.odf_freight)
    TextView mFreight;
    @BindView(R.id.odf_deposit_amount)
    TextView mDepositAmount;
    @BindView(R.id.odf_amount)
    TextView mAmount;
    @BindView(R.id.odf_order_no)
    TextView mOrderNo;
    @BindView(R.id.odf_supply_chain_no)
    TextView mSupplyChainNo;
    @BindView(R.id.odf_order_time)
    TextView mOrderTime;
    @BindView(R.id.odf_pay_method)
    TextView mPayMethod;
    @BindView(R.id.odf_copy_order_no)
    TextView mCopyOrderNo;
    @BindView(R.id.odf_copy_supply_chain_no)
    TextView mCopySupplyChainNo;
    @BindView(R.id.odf_driver_name)
    TextView mDriverName;
    @BindView(R.id.odf_plate_no)
    TextView mPlateNo;
    @BindView(R.id.odf_driver_contact)
    TextView mDriverContact;
    @BindView(R.id.odf_dial_driver)
    TextView mDialDriver;
    @BindView(R.id.odf_shop_discount)
    TextView mShopDiscount;
    @BindView(R.id.odf_coupon)
    TextView mCoupon;
    @BindView(R.id.odf_order_source)
    TextView mOrderSource;
    @BindView(R.id.odf_order_type)
    TextView mOrderType;
    @BindView(R.id.odf_deposit_amount_group)
    Group mDepositAmountGroup;
    @BindView(R.id.odf_shop_discount_group)
    Group mShopDiscountGroup;
    @BindView(R.id.odf_coupon_group)
    Group mCouponGroup;
    @BindView(R.id.odf_supply_chain_no_group)
    Group mSupplyChainNoGroup;
    @BindView(R.id.odf_order_source_group)
    Group mOrderSourceGroup;
    @BindView(R.id.odf_order_type_group)
    Group mOrderTypeGroup;
    @BindView(R.id.odf_driver_group)
    Group mDriverGroup;

    public OrderDetailFooter(Context context) {
        this(context, null);
    }

    public OrderDetailFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderDetailFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_order_detail_footer, this);
        ButterKnife.bind(this, view);
    }

    public void setData(OrderResp data) {
        mRemark.setText(data.getSubBillRemark());
        mOrderAmount.setText(handleItemAmount(data.getOrderTotalAmount()));
        mDeliverAmountLabel.setText(data.getSubBillStatus() == 2 ? "预发货总价" : "发货总价");
        mDeliverAmount.setText(data.getSubBillStatus() < 2 || data.getSubBillStatus() == 7 ?
                null : handleItemAmount(data.getAdjustmentTotalAmount()));
        mSignAmount.setText(data.getSubBillStatus() == 4 || data.getSubBillStatus() == 6 || data.getSubBillStatus() == 8 ?
                handleItemAmount(data.getInspectionTotalAmount()) : null);
        if (data.getInspectionDepositTotalAmount() > 0) {
            mDepositAmountGroup.setVisibility(VISIBLE);
            mDepositAmount.setText(handleItemAmount(data.getInspectionDepositTotalAmount()));
        }
        if (data.getInspectionDiscountSubAmount() > 0) {
            mShopDiscountGroup.setVisibility(VISIBLE);
            mShopDiscount.setText(handleItemAmount(data.getInspectionDiscountSubAmount()));
        }
        if (data.getInspectionCouponSubAmount() > 0) {
            mCouponGroup.setVisibility(VISIBLE);
            mCoupon.setText(handleItemAmount(data.getInspectionCouponSubAmount()));
        }
        mAmount.setText(handleAmount(data.getIsExchange() == 1 ? 0 : data.getTotalAmount(), 10f / 16));

        mOrderNo.setText(data.getSubBillNo());
        mCopyOrderNo.setTag(data.getSubBillNo());
        mSupplyChainNoGroup.setVisibility(VISIBLE);
        mSupplyChainNo.setText(data.getSupplyBillNo());
        mOrderTime.setText(CalendarUtils.getFormatYyyyMmDdHhMm(data.getSubBillCreateTime()));
        String payType = OrderHelper.getPayType(data.getPayType()), paymentWay = OrderHelper.getPaymentWay(data.getPaymentWay());
        if (payType.length() != 0)
            mPayMethod.setText(paymentWay.length() > 0 ?
                    payType + "（" + paymentWay + "）" : payType);
        else mPayMethod.setText(null);
        handleOrderType(data);
        if (data.getBillSource() == 1 || data.getBillSource() == 2) {
            mOrderSourceGroup.setVisibility(VISIBLE);
            mOrderSource.setText(data.getBillSource() == 1 ? "商城订单" : "哗啦啦供应链订单");
        }
        handleDeliveryInfo(data);
    }

    private void handleDeliveryInfo(OrderResp data) {
        if (TextUtils.isEmpty(data.getDriverId())) {
            return;
        }
        mDriverGroup.setVisibility(VISIBLE);
        mDriverName.setText(data.getDriverName());
        mPlateNo.setText(data.getPlateNumber());
        mDriverContact.setText(PhoneUtil.formatPhoneNum(data.getMobilePhone()));
        mDialDriver.setTag(data.getMobilePhone());
    }

    private void handleOrderType(OrderResp data) {
        StringBuilder sb = new StringBuilder();
        if (data.getSubbillCategory() == 2) sb.append("代仓订单");
        if (data.getIsSupplement() == 1) sb.append("/补单订单");
        if (data.getIsExchange() == 1) sb.append("/换货订单");
        if (data.getNextDayDelivery() == 1) sb.append("/隔日配送订单");
        if (!TextUtils.isEmpty(sb.toString())) {
            mOrderTypeGroup.setVisibility(View.VISIBLE);
            if (sb.toString().startsWith("/")) sb.deleteCharAt(0);
            mOrderType.setText(sb.toString());
        }
    }

    private CharSequence handleItemAmount(double money) {
        return handleAmount(money, 10f / 12);
    }

    private CharSequence handleAmount(double money, float proportion) {
        String source = String.format("¥%s", CommonUtils.formatMoney(money));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(proportion), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(proportion), source.indexOf("."), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @OnClick({R.id.odf_copy_order_no, R.id.odf_copy_supply_chain_no, R.id.odf_dial_driver})
    public void onViewClicked(View view) {
        if (view.getTag() == null) return;
        switch (view.getId()) {
            case R.id.odf_copy_order_no:
            case R.id.odf_copy_supply_chain_no:
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                if (cm == null) {
                    return;
                }
                ClipData clipData = ClipData.newPlainText("编号", view.getTag().toString());
                cm.setPrimaryClip(clipData);
                ToastUtils.showShort(getContext(), "复制成功");
                break;
            case R.id.odf_dial_driver:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri info = Uri.parse("tel:" + view.getTag());
                intent.setData(info);
                getContext().startActivity(intent);
                break;
        }
    }
}
