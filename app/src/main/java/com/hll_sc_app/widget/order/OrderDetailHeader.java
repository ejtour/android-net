package com.hll_sc_app.widget.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderStatus;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.utils.ColorStr;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */

public class OrderDetailHeader extends ConstraintLayout {
    @BindView(R.id.odh_status_label)
    TextView mStatusLabel;
    @BindView(R.id.odh_status_desc)
    TextView mStatusDesc;
    @BindView(R.id.odh_status_icon)
    ImageView mStatusIcon;
    @BindView(R.id.odh_shop_logo)
    GlideImageView mShopLogo;
    @BindView(R.id.odh_shop_name)
    TextView mShopName;
    @BindView(R.id.odh_group_name)
    TextView mGroupName;
    @BindView(R.id.odh_orderer)
    TextView mOrderer;
    @BindView(R.id.odh_orderer_dial)
    TextView mOrdererDial;
    @BindView(R.id.odh_consignee)
    TextView mConsignee;
    @BindView(R.id.odh_consignee_dial)
    TextView mConsigneeDial;
    @BindView(R.id.odh_time_address)
    TextView mTimeAddress;
    @BindView(R.id.odh_label)
    TextView mLabel;
    @BindView(R.id.odh_self_lift_tag)
    TextView mSelfLiftTag;

    public OrderDetailHeader(Context context) {
        this(context, null);
    }

    public OrderDetailHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderDetailHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_order_detail_header, this);
        ButterKnife.bind(this, view);
    }

    public void setData(OrderResp data) {
        handleOrderStatus(data.getSubBillStatus(), data.getCanceler(), data.getActionBy(), data.getCancelReason());
        mShopLogo.setImageURL(data.getImgUrl());
        mShopName.setText(data.getShopName());
        mGroupName.setText(data.getGroupName());
        mOrderer.setText(String.format("订货人：%s", data.getSubBillCreateBy()));
        mOrdererDial.setTag(data.getOrdererMobile());
        mOrdererDial.setText(handlePhoneNum(data.getOrdererMobile()));
        mConsignee.setText(String.format("收货人：%s", data.getReceiverName()));
        mConsigneeDial.setTag(data.getReceiverMobile());
        mConsigneeDial.setText(handlePhoneNum(data.getReceiverMobile()));
        if (data.getDeliverType() == 2) {
            mSelfLiftTag.setVisibility(VISIBLE);
        }
        handleTimeAddress(data.getDeliverType() == 2,
                data.getTargetExecuteDate(),
                data.getTargetAddress());
        handleLabel(data.getSubBillStatus(), data.getWareHourseName());
    }

    private CharSequence handlePhoneNum(String number) {
        String source = PhoneUtil.formatPhoneNum(number);
        if (TextUtils.isEmpty(source)) {
            return "暂未提供";
        }
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // no-op
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor(ColorStr.COLOR_5695D2)); // 下划线
            }
        }, 0, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private void handleLabel(int subBillStatus, String wareHouseName) {
        if (subBillStatus == 0) {
            mLabel.setText("商品清单（请以商品实际价格为准）");
        } else {
            if (TextUtils.isEmpty(wareHouseName))
                mLabel.setText("商品清单");
            else {
                mLabel.setTextColor(ContextCompat.getColor(getContext(), R.color.color_666666));
                mLabel.setText(String.format("商品仓库：%s", wareHouseName));
            }
        }
    }

    private void handleTimeAddress(boolean isLift, String time, String address) {
        String source = String.format(isLift ? "采购商将于 %s 至 %s 上门提货，请及时备货！" : "请于 %s 送达 %s", time, address);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_666666)),
                source.indexOf(time), source.indexOf(time) + time.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_666666)),
                source.indexOf(address), source.indexOf(address) + address.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTimeAddress.setText(ss);
    }

    private void handleOrderStatus(int billStatus, int canceler, String actionBy, String cancelReason) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.getStatus() == billStatus) {
                mStatusIcon.setImageResource(value.getIcon());
                mStatusLabel.setText(value.getLabel());
                mStatusDesc.setText(value.getDesc(canceler, actionBy, cancelReason));
                break;
            }
        }
    }

    @OnClick({R.id.odh_orderer_dial, R.id.odh_consignee_dial})
    public void onViewClicked(View view) {
        if (view.getTag() == null || view.getTag().toString().length() == 0) return;
        switch (view.getId()) {
            case R.id.odh_orderer_dial:
            case R.id.odh_consignee_dial:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri info = Uri.parse("tel:" + view.getTag());
                intent.setData(info);
                getContext().startActivity(intent);
                break;
        }
    }
}
