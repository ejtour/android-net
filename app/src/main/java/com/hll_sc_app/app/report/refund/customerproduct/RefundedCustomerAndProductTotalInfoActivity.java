package com.hll_sc_app.app.report.refund.customerproduct;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.refund.WaitRefundTotalResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退货客户明细统计
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_REFUNDED_CUSTOMER_PRODUCT_TOTAL)
public class RefundedCustomerAndProductTotalInfoActivity extends BaseLoadActivity implements RefundedCustomerAndProductTotalInfoContract.IRefundedCustomerAndProductTotalInfoView {


    @BindView(R.id.refunded_num)
    TextView refundedNum;
    @BindView(R.id.refunded_customer)
    TextView refundedCustomer;
    @BindView(R.id.wait_refund_amount)
    TextView waitRefundAmount;
    @BindView(R.id.txt_cash_amount)
    TextView txtCashAmount;
    @BindView(R.id.txt_online_amount)
    TextView txtOnlineAmount;
    @BindView(R.id.txt_bank_card_amount)
    TextView txtBankCardAmount;
    @BindView(R.id.txt_account_amount)
    TextView txtAccountAmount;
    @BindView(R.id.txt_main_title_block)
    RelativeLayout mRlMainTitleBlock;
    private RefundedCustomerAndProductTotalInfoPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_refunded_customer_product_total);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initStatusBar();
        mPresenter = RefundedCustomerAndProductTotalInfoPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initStatusBar() {
        StatusBarCompat.setTranslucent(getWindow(), true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mRlMainTitleBlock.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.img_back, R.id.txt_refunded_customer_btn, R.id.txt_refunded_product_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_refunded_customer_btn:
                RouterUtil.goToActivity(RouterConfig.REPORT_REFUNDED_CUSTOMER_DETAIL);
                break;
            case R.id.txt_refunded_product_btn:
                RouterUtil.goToActivity(RouterConfig.REPORT_REFUNDED_PRODUCT_DETAIL);
                break;
            default:
                break;
        }
    }

    /**
     * 显示待退明细数据
     *
     * @param refundTotalResp
     */
    @Override
    public void showRefundedCustomerAndProductTotalInfo(WaitRefundTotalResp refundTotalResp) {
        refundedNum.setText(refundTotalResp.getRefundBillNum() + "");
        refundedCustomer.setText(refundTotalResp.getRefundGroupCustomerNum() + "/" + refundTotalResp.getRefundShopCustomerNum());
        waitRefundAmount.setText(CommonUtils.formatMoney(Double.parseDouble(refundTotalResp.getTotalRefundAmount())));
        txtCashAmount.setText(CommonUtils.formatMoney(Double.parseDouble(refundTotalResp.getCashAmount())));
        txtOnlineAmount.setText(CommonUtils.formatMoney(Double.parseDouble(refundTotalResp.getOnLineAmount())));
        txtBankCardAmount.setText(CommonUtils.formatMoney(Double.parseDouble(refundTotalResp.getBankCardAmount())));
        txtAccountAmount.setText(CommonUtils.formatMoney(Double.parseDouble(refundTotalResp.getAccountAmount())));

    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

}
