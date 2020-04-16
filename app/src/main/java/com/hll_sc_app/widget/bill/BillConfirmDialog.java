package com.hll_sc_app.widget.bill;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/9
 */

public class BillConfirmDialog extends BaseDialog {
    @BindView(R.id.dbc_group_name)
    TextView mGroupName;
    @BindView(R.id.dbc_shop_name)
    TextView mShopName;
    @BindView(R.id.dbc_period)
    TextView mPeriod;
    @BindView(R.id.dbc_amount)
    TextView mAmount;
    @BindView(R.id.dbc_confirm)
    TextView mConfirm;

    public BillConfirmDialog(@NonNull Activity context, View.OnClickListener listener) {
        super(context);
        mConfirm.setOnClickListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(100);
            getWindow().setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = View.inflate(getContext(), R.layout.dialog_bill_confirm, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public BillConfirmDialog withData(BillBean billBean) {
        mGroupName.setText(billBean.getPurchaserName());
        mShopName.setText(billBean.getShopName());
        mPeriod.setText(String.format("%s - %s", DateUtil.getReadableTime(billBean.getStartPaymentDay(), Constants.SLASH_YYYY_MM_DD),
                DateUtil.getReadableTime(billBean.getEndPaymentDay(), Constants.SLASH_YYYY_MM_DD)));
        mAmount.setText(String.format("¥%s", CommonUtils.formatMoney(billBean.getTotalAmount())));
        return this;
    }

    @OnClick(R.id.dbc_cancel)
    @Override
    public void dismiss() {
        super.dismiss();
    }
}
