package com.hll_sc_app.widget.order;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderStatus;
import com.hll_sc_app.app.order.trace.OrderTraceActivity;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.trace.OrderTraceBean;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;

import java.util.List;

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
    @BindView(R.id.odh_trace_label)
    TextView mTraceLabel;
    @BindView(R.id.odh_trace_desc)
    TextView mTraceDesc;
    @BindView(R.id.odh_trace_group)
    Group mTraceGroup;
    @BindView(R.id.odh_status_time)
    TextView mCancelTime;
    private OrderResp mOrderResp;
    private List<OrderTraceBean> mTraceBeans;

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
        mOrderResp = data;
        handleOrderStatus(data.getSubBillStatus(), data.getCanceler(), data.getActionBy(), data.getCancelReason(),data.getButtonList());
        //取消订单增加取消时间的显示
        mCancelTime.setVisibility(data.getSubBillStatus() == 7 ? VISIBLE : GONE);
        mCancelTime.setText(DateUtil.getReadableTime(data.getActionTime()));
        mShopLogo.setImageURL(data.getImgUrl());
        String name = (TextUtils.isEmpty(data.getStallName()) ? "" : (data.getStallName() + " - ")) + data.getShopName();
        mShopName.setText(name);
        mGroupName.setText(data.getPurchaserName());
        mOrderer.setText(String.format("订货人：%s", data.getSubBillCreateBy()));
        mOrdererDial.setTag(data.getOrdererMobile());
        mOrdererDial.setText(handlePhoneNum(data.getOrdererMobile()));
        mConsignee.setText(String.format("收货人：%s", data.getReceiverName()));
        mConsigneeDial.setTag(data.getReceiverMobile());
        mConsigneeDial.setText(handlePhoneNum(data.getReceiverMobile()));
        if (data.getDeliverType() == 2) mSelfLiftTag.setVisibility(VISIBLE);
        handleTimeAddress(data.getDeliverType() == 2,
                data.getTargetExecuteDate(),
                data.getTargetAddress());
        handleLabel(data.getWareHourseName(), data.getShipperType() > 0);
    }

    public void setData(List<OrderTraceBean> list) {
        mTraceBeans = list;
        if (CommonUtils.isEmpty(list)) {
            mTraceGroup.setVisibility(GONE);
        } else {
            mTraceGroup.setVisibility(VISIBLE);
            OrderTraceBean bean = list.get(0);
            if (UserConfig.crm()) {
                mTraceLabel.setText(bean.getOpTypeName());
                mTraceDesc.setText(String.format("%s %s", bean.getTitle(), DateUtil.getReadableTime(bean.getOpTime())));
            } else {
                mTraceLabel.setText(bean.getSupplyTitle());
                mTraceDesc.setText(DateUtil.getReadableTime(bean.getOpTime()));
            }
        }
        mTraceGroup.getParent().requestLayout();
    }

    public void setData(TransferBean data) {
        mStatusIcon.setImageResource(OrderStatus.PENDING_TRANSFER.getIcon());
        mStatusLabel.setText(data.getStatus() == 1 ? OrderStatus.PENDING_TRANSFER.getLabel() : "下单失败");
        mStatusDesc.setText(data.getStatus() == 1
                ? data.getHomologous() == 1
                ? OrderStatus.PENDING_TRANSFER.getDesc(1, null, null,null)
                : "该订单含有未关联的第三方品项，请先关联后再进行商城下单操作"
                : "订单存在商品问题或门店合作问题导致下单失败");
        mShopLogo.setImageURL("");
        mShopName.setText(data.getAllotName());
        mGroupName.setText(data.getGroupName());
        mOrderer.setText(String.format("订货人：%s", data.getBillCreateBy()));
        mOrdererDial.setTag(data.getOrdererMobile());
        mOrdererDial.setText(handlePhoneNum(data.getOrdererMobile()));
        mConsignee.setText(String.format("收货人：%s", data.getReceiverName()));
        mConsigneeDial.setTag(data.getReceiverMobile());
        mConsigneeDial.setText(handlePhoneNum(data.getReceiverMobile()));
        handleTimeAddress(false,
                data.getTargetExecuteDate(),
                data.getReceiverAddress());
        mLabel.setText("商品清单（请以商品实际价格为准）");
    }

    private CharSequence handlePhoneNum(String number) {
        String source = PhoneUtil.formatPhoneNum(number);
        if (TextUtils.isEmpty(source)) return "暂未提供";
        return source;
    }

    private void handleLabel(String wareHouseName, boolean wareHouse) {
        if (!wareHouse)
            mLabel.setText("商品清单");
        else {
            mLabel.setTextColor(ContextCompat.getColor(getContext(), R.color.color_666666));
            mLabel.setText(String.format("商品仓库：%s", wareHouseName));
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

    private void handleOrderStatus(int billStatus, int canceler, String actionBy, String cancelReason,List<Integer> buttonList) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.getStatus() == billStatus) {
                mStatusIcon.setImageResource(value.getIcon());
                mStatusLabel.setText(value.getLabel());
                mStatusDesc.setText(value.getDesc(canceler, actionBy, cancelReason,buttonList));
                break;
            }
        }
    }

    @OnClick({R.id.odh_orderer_dial, R.id.odh_consignee_dial, R.id.odh_trace_btn})
    public void onViewClicked(View view) {
        if (view.getTag() == null || view.getTag().toString().length() == 0) return;
        switch (view.getId()) {
            case R.id.odh_orderer_dial:
            case R.id.odh_consignee_dial:
                UIUtils.callPhone(view.getTag().toString());
                break;
        }
    }

    @OnClick(R.id.odh_trace_btn)
    public void orderTrace() {
        if (mOrderResp == null || CommonUtils.isEmpty(mTraceBeans)) return;
        OrderTraceActivity.start(mOrderResp, mTraceBeans);
    }
}
