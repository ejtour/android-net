package com.hll_sc_app.widget.order;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.bean.order.place.SettlementInfoResp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/10
 */

public class PlaceOrderConfirmHeader extends ConstraintLayout {
    @BindView(R.id.opc_purchaser_shop)
    TextView mPurchaserShop;
    @BindView(R.id.opc_receiver)
    TextView mReceiver;
    @BindView(R.id.opc_address)
    TextView mAddress;

    public PlaceOrderConfirmHeader(Context context) {
        this(context, null);
    }

    public PlaceOrderConfirmHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlaceOrderConfirmHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundColor(Color.WHITE);
        View view = View.inflate(context, R.layout.view_order_place_confirm_header, this);
        ButterKnife.bind(this, view);
    }

    public void setData(SettlementInfoResp resp) {
        mPurchaserShop.setText(resp.getPurchaserShopName());
        mReceiver.setText(String.format("收货人：%s / %s", resp.getReceiverName(), PhoneUtil.formatPhoneNum(resp.getReceiverMobile())));
        mAddress.setText(String.format("地址：%s", resp.getReceiverAddress()));
    }
}
