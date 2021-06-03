package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.app.order.place.confirm.modify.PlaceOrderModifyActivity;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.order.place.OrderCommitReq;
import com.hll_sc_app.bean.order.place.SettlementInfoResp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.opc_tab)
    RadioGroup mRadioGroup;
    @BindView(R.id.opc_tab_1)
    RadioButton mTab1;
    @BindView(R.id.opc_tab_2)
    RadioButton mTab2;
    @BindView(R.id.opc_edit)
    ImageView mEditBtn;
    private SettlementInfoResp mResp;

    public PlaceOrderConfirmHeader(Context context) {
        this(context, null);
    }

    public PlaceOrderConfirmHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlaceOrderConfirmHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundResource(R.drawable.bg_white_radius_8_solid);
        setPadding(0, 0, 0, UIUtils.dip2px(20));
        View view = View.inflate(context, R.layout.view_order_place_confirm_header, this);
        ButterKnife.bind(this, view);
        if (!UserConfig.isCrmPlus()) {
            mRadioGroup.setVisibility(GONE);
            mEditBtn.setVisibility(GONE);
        } else {
            mTab1.setChecked(true);
            mPurchaserShop.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            mReceiver.setVisibility(GONE);
        }
    }

    public void setData(SettlementInfoResp resp) {
        String receiver = String.format("%s\t%s", resp.getReceiverName(), PhoneUtil.formatPhoneNum(resp.getReceiverMobile()));
        mPurchaserShop.setText(UserConfig.isCrmPlus() ? receiver : resp.getPurchaserShopName());
        mReceiver.setText(receiver);
        mAddress.setText(resp.getReceiverAddress());
        mResp = resp;
    }

    public void inflateReq(OrderCommitReq req) {
        if (UserConfig.isCrmPlus()) {
            req.setDeliverType(mTab1.isChecked() ? "3" : "2");
            req.setReceiverAddress(mResp.getReceiverAddress());
            req.setReceiverName(mResp.getReceiverName());
            req.setReceiverMobile(mResp.getReceiverMobile());
        }
    }

    @OnClick(R.id.opc_edit)
    void changedAddress() {
        PlaceOrderModifyActivity.start((Activity) getContext());
    }
}
