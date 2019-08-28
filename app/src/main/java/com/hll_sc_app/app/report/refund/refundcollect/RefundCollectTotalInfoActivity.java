package com.hll_sc_app.app.report.refund.refundcollect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.refund.WaitRefundTotalResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退货统计
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_REFUNDED_COLLECT)
public class RefundCollectTotalInfoActivity extends BaseLoadActivity implements RefundCollectTotalInfoContract.IRefundTotalInfoView {


    @BindView(R.id.refunded_num)
    TextView refundedNum;
    @BindView(R.id.refunded_customer)
    TextView refundedCustomer;
    @BindView(R.id.refunded_amount)
    TextView refundedAmount;
    @BindView(R.id.txt_cash_amount)
    TextView txtCashAmount;
    @BindView(R.id.txt_online_amount)
    TextView txtOnlineAmount;
    @BindView(R.id.txt_bank_card_amount)
    TextView txtBankCardAmount;
    @BindView(R.id.txt_account_amount)
    TextView txtAccountAmount;
    private RefundCollectTotalInfoPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_refunded_aggregation);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = RefundCollectTotalInfoPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.img_back, R.id.txt_refunded_detail_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_refunded_detail_btn:
                RouterUtil.goToActivity(RouterConfig.REPORT_REFUNDED_COLLECT_DETAIL);
                break;
            default:
                break;
        }
    }

    /**
     * 显示退货金额数据
     * @param refundTotalResp
     */
    @Override
    public void showRefundedTotalInfo(WaitRefundTotalResp refundTotalResp) {
        refundedNum.setText(refundTotalResp.getRefundBillNum() + "");
        refundedCustomer.setText(refundTotalResp.getRefundGroupCustomerNum() + "/" + refundTotalResp.getRefundShopCustomerNum());
        refundedAmount.setText(CommonUtils.formatMoney(Double.parseDouble(refundTotalResp.getTotalRefundAmount())));
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
