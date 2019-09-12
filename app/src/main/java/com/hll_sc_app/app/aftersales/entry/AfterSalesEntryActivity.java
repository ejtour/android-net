package com.hll_sc_app.app.aftersales.entry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.apply.AfterSalesApplyActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.bean.aftersales.AfterSalesVerifyResp;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ThumbnailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/10
 */

@Route(path = RouterConfig.AFTER_SALES_ENTRY)
public class AfterSalesEntryActivity extends BaseLoadActivity implements IAfterSalesEntryContract.IAfterSalesEntryView {

    public static void start(OrderResp resp) {
        RouterUtil.goToActivity(RouterConfig.AFTER_SALES_ENTRY, resp);
    }

    @BindView(R.id.ase_shop_name)
    TextView mShopName;
    @BindView(R.id.ase_order_no)
    TextView mOrderNo;
    @BindView(R.id.ase_money)
    TextView mMoney;
    @BindView(R.id.ase_thumbnail)
    ThumbnailView mThumbnail;
    @BindView(R.id.ase_order_date)
    TextView mOrderDate;
    @BindView(R.id.ase_inspection_date)
    TextView mInspectionDate;
    @BindView(R.id.ase_return_goods)
    TextView mReturnGoods;
    @BindView(R.id.ase_return_deposit)
    TextView mReturnDeposit;
    @Autowired(name = "parcelable")
    OrderResp mOrderResp;
    private IAfterSalesEntryContract.IAfterSalesEntryPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sales_entry);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = AfterSalesEntryPresenter.newInstance(mOrderResp.getSubBillID());
        mPresenter.register(this);
    }

    private void initView() {
        mReturnGoods.setText(processText("退货退款\n已收到货，需要退回货物。未支付的订单只能退货", 4));
        mReturnDeposit.setText(processText("退押金\n退回押金商品", 3));
        mShopName.setText(mOrderResp.getSupplyShopName());
        mOrderNo.setText(String.format("订单号：%s", mOrderResp.getSubBillNo()));
        mMoney.setText(processMoney(mOrderResp.getTotalAmount()));
        mThumbnail.setOrderDetailListData(mOrderResp.getBillDetailList());
        mOrderDate.setText(String.format("订货日期：%s", DateUtil.getReadableTime(mOrderResp.getSubBillCreateTime())));
        mInspectionDate.setText(String.format("验货日期：%s", DateUtil.getReadableTime(mOrderResp.getSignTime())));
    }

    private SpannableString processMoney(double money) {
        String source = String.format("实付款：¥%s", CommonUtils.formatMoney(money));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_ed5655)), 4, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private SpannableString processText(String source, int endPosition) {
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.3f), 0, endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_222222)), 0, endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @OnClick({R.id.ase_return_goods, R.id.ase_return_deposit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ase_return_goods:
                mPresenter.verify(3);
                break;
            case R.id.ase_return_deposit:
                mPresenter.verify(4);
                break;
        }
    }

    @Override
    public void handleVerifyResult(AfterSalesVerifyResp resp) {
        if (!resp.isCanRefund()) {
            showToast(resp.getTips());
            return;
        }
        AfterSalesApplyActivity.start(AfterSalesApplyParam.afterSalesFromOrder(mOrderResp, resp.getSubBillType()));
    }
}
