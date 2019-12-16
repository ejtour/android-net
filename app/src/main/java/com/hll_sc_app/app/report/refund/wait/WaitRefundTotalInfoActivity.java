package com.hll_sc_app.app.report.refund.wait;

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
 * 待退统计
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_WAIT_REFUND_TOTAL)
public class WaitRefundTotalInfoActivity extends BaseLoadActivity implements WaitRefundTotalInfoContract.IRefundTotalInfoView {


    @BindView(R.id.wait_refund_num)
    TextView waitRefundNum;
    @BindView(R.id.wait_refund_customer)
    TextView waitRefundCustomer;
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
    RelativeLayout mRlTitleBlock;
    private WaitRefundTotalInfoPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_wait_refund_total);
        StatusBarCompat.setTranslucent(getWindow(), true);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        showStatusBar();
        mPresenter = WaitRefundTotalInfoPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mRlTitleBlock.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.img_back, R.id.txt_wait_customer_btn, R.id.txt_wait_product_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_wait_customer_btn:
                RouterUtil.goToActivity(RouterConfig.REPORT_WAIT_REFUND_CUSTOMER_DETAIL);
                break;
            case R.id.txt_wait_product_btn:
                RouterUtil.goToActivity(RouterConfig.REPORT_WAIT_REFUND_PRODUCT_DETAIL);
                break;
            default:
                break;
        }
    }

    /**
     * 显示待退明细数据
     * @param refundTotalResp
     */
    @Override
    public void showWaitRefundTotalInfo(WaitRefundTotalResp refundTotalResp) {
        waitRefundNum.setText(refundTotalResp.getRefundBillNum()+"");
        waitRefundCustomer.setText(refundTotalResp.getRefundGroupCustomerNum()+"/"+refundTotalResp.getRefundShopCustomerNum());
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
