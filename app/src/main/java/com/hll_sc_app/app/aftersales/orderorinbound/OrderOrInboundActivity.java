package com.hll_sc_app.app.aftersales.orderorinbound;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/5/18
 */
@Route(path = RouterConfig.AFTER_SALES_ORDER_OR_INBOUND)
public class OrderOrInboundActivity extends BaseActivity {

    public static void start(String erpBillNo, String voucherNo) {
        RouterUtil.goToActivity(RouterConfig.AFTER_SALES_ORDER_OR_INBOUND, erpBillNo, voucherNo);
    }

    @Autowired(name = "object0")
    String mErpBillNo;
    @Autowired(name = "object1")
    String mVoucherNo;
    @BindView(R.id.ooi_order_no_label)
    TextView mOrderNoLabel;
    @BindView(R.id.ooi_order_no)
    TextView mOrderNo;
    @BindView(R.id.ooi_inbound_no_label)
    TextView mInboundNoLabel;
    @BindView(R.id.ooi_inbound_no)
    TextView mInboundNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sales_order_or_inbound);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(mErpBillNo)) {
            mOrderNo.setVisibility(View.GONE);
            mOrderNoLabel.setVisibility(View.GONE);
        } else {
            mOrderNo.setVisibility(View.VISIBLE);
            mOrderNoLabel.setVisibility(View.VISIBLE);
            mOrderNo.setText(mErpBillNo);
        }
        if (TextUtils.isEmpty(mVoucherNo)) {
            mInboundNo.setVisibility(View.GONE);
            mInboundNoLabel.setVisibility(View.GONE);
        } else {
            mInboundNo.setVisibility(View.VISIBLE);
            mInboundNoLabel.setVisibility(View.VISIBLE);
            mInboundNo.setText(mVoucherNo);
        }
    }
}
