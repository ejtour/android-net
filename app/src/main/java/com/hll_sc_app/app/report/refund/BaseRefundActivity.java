package com.hll_sc_app.app.report.refund;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.bean.report.refund.RefundResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

public abstract class BaseRefundActivity extends BaseLoadActivity implements IRefundContract.IRefundCustomerProductView {

    @BindView(R.id.arr_title_bar)
    protected TitleBar mTitleBar;
    @BindView(R.id.arr_refund_title)
    protected TextView mRefundTitle;
    @BindView(R.id.arr_refund_num)
    TextView mRefundNum;
    @BindView(R.id.arr_refund_num_label)
    protected TextView mRefundNumLabel;
    @BindView(R.id.arr_customer_num)
    TextView mCustomerNum;
    @BindView(R.id.arr_customer_num_label)
    protected TextView mCustomerNumLabel;
    @BindView(R.id.arr_total_amount)
    TextView mTotalAmount;
    @BindView(R.id.arr_total_amount_label)
    protected TextView mTotalAmountLabel;
    @BindView(R.id.arr_first_label)
    protected TextView mFirstLabel;
    @BindView(R.id.arr_second_label)
    protected TextView mSecondLabel;
    @BindView(R.id.arr_second_btn)
    protected FrameLayout mSecondBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_refund);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        StatusBarUtil.fitSystemWindowsWithPaddingTop(mTitleBar);
        RefundPresenter presenter = RefundPresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }

    protected abstract void initView();

    @Override
    protected void initSystemBar() {
        StatusBarUtil.setTranslucent(this);
    }

    @Override
    public void setData(RefundResp resp) {
        mRefundNum.setText(CommonUtils.formatNum(resp.getRefundBillNum()));
        mCustomerNum.setText(String.format("%s/%s", CommonUtils.formatNum(resp.getRefundGroupCustomerNum()), CommonUtils.formatNum(resp.getRefundShopCustomerNum())));
        mTotalAmount.setText(CommonUtils.formatMoney(resp.getTotalRefundAmount()));
    }

    protected abstract void toFirst();

    protected abstract void toSecond();

    @OnClick(R.id.arr_first_btn)
    public void clickFirst() {
        toFirst();
    }

    @OnClick(R.id.arr_second_btn)
    public void clickSecond() {
        toSecond();
    }
}
