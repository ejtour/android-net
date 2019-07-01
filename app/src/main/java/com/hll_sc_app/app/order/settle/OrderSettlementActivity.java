package com.hll_sc_app.app.order.settle;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.settle.CashierResp;
import com.hll_sc_app.bean.order.settle.PayWayBean;
import com.hll_sc_app.bean.order.settle.PayWaysResp;
import com.hll_sc_app.bean.order.settle.SettlementParam;
import com.hll_sc_app.widget.order.QRCodeDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/1
 */
@Route(path = RouterConfig.ORDER_SETTLEMENT)
public class OrderSettlementActivity extends BaseLoadActivity implements IOrderSettlementContract.IOrderSettlementView {

    /**
     * @param totalPrice 订单待支付金额
     * @param subBillID  订单 id
     * @param payType    支付类型 1.在线支付 2.货到付款
     */
    public static void start(Activity context, double totalPrice, String subBillID, int payType) {
        SettlementParam param = new SettlementParam();
        param.setTotalPrice(totalPrice);
        param.setSubBillID(subBillID);
        param.setPayType(payType);
        RouterUtil.goToActivity(RouterConfig.ORDER_SETTLEMENT, context, REQ_CODE, param);
    }

    public static final int REQ_CODE = 0x1101;
    @BindView(R.id.aos_amount)
    TextView mTotalAmount;
    @BindView(R.id.aos_list_view)
    RecyclerView mListView;
    @BindView(R.id.aos_root_view)
    View mRootView;
    @Autowired(name = "parcelable", required = true)
    SettlementParam mSettlementParam;
    /**
     * 当前选中的支付方式
     */
    private PayWayBean mSelect;
    /**
     * 预支付订单号
     */
    private String mPayOrderNo;
    private ListItemAdapter mAdapter;
    private OrderSettlementPresenter mPresenter;
    /**
     * 支付总金额
     */
    private double mTotalPrice;
    /**
     * 1.在线支付2.货到付款
     */
    private int mPayType;
    /**
     * 要支付的订单号
     */
    private String mSubBillID;
    /**
     * 是否成功
     */
    private boolean mIsSuccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_order_settlement);
        StatusBarCompat.setLightStatusBar(getWindow(), true);
        StatusBarCompat.setTranslucent(getWindow(), true);
        ButterKnife.bind(this);
        mTotalPrice = mSettlementParam.getTotalPrice();
        mPayType = mSettlementParam.getPayType();
        mSubBillID = mSettlementParam.getSubBillID();
        mPresenter = OrderSettlementPresenter.newInstance(mSubBillID);
        mPresenter.register(this);
        mPresenter.getPayWays(mPayType);
        initView();
    }

    private void initView() {
        mTotalAmount.setText(String.format("¥ %s", mTotalPrice));
        mAdapter = new ListItemAdapter(null);
        mAdapter.setOnItemClickListener(new PayWayItemClickListener());
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        closeActivity();
    }

    @Override
    public void closeActivity() {
        // 支付失败通知调用方
        dealResult(false);
    }

    private void dealResult(boolean success) {
        if (success) {
            showToast("支付成功");
            setResult(RESULT_OK);
        }
        finish();
    }

    @Override
    public void showPayWays(List<PayWayBean> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public void settleSuccess() {
        // 支付成功通知调用方
        dealResult(true);
    }

    @Override
    public void showQRCode(CashierResp resp) {
        mPayOrderNo = resp.getPayOrderNo();
        QRCodeDialog qrCodeDialog = QRCodeDialog.create(this,
                resp.getTotalAmount(),
                resp.getCashierUrl(),
                TextUtils.equals(PayWaysResp.PAY_TYPE_WECHAT_OFFLINE, mSelect.getId()) ||
                        TextUtils.equals(PayWaysResp.PAY_TYPE_WECHAT_ONLINE, mSelect.getId()) ? "微信" : "支付宝");
        qrCodeDialog.setOnDismissListener(dialog -> dealResult(mIsSuccess));
        qrCodeDialog.show();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @OnClick({R.id.aos_close, R.id.aos_top_bg})
    public void close(View view) {
        closeActivity();
    }

    private class PayWayItemClickListener implements BaseQuickAdapter.OnItemClickListener {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            PayWayBean item = (PayWayBean) adapter.getItem(position);
            if (item == null) {
                return;
            }
            if (TextUtils.equals(PayWaysResp.PAY_TYPE_WECHAT_OFFLINE, item.getId()) || TextUtils.equals(PayWaysResp.PAY_TYPE_WECHAT_ONLINE, item.getId())) {
                callCashier(CashierResp.PAY_TYPE_WECHAT_QR);
            } else if (TextUtils.equals(PayWaysResp.PAY_TYPE_ALIPAY_OFFLINE, item.getId()) || TextUtils.equals(PayWaysResp.PAY_TYPE_ALIPAY_ONLINE, item.getId())) {
                callCashier(CashierResp.PAY_TYPE_ALIPAY_QR);
            } else if (TextUtils.equals(PayWaysResp.PAY_TYPE_SWIPE, item.getId())) {
                inspectionPay(CashierResp.PAY_TYPE_SWIPE);
            } else if (TextUtils.equals(PayWaysResp.PAY_TYPE_CASH, item.getId())) {
                inspectionPay(CashierResp.PAY_TYPE_CASH);
            } else {
                showToast("暂时不支持此种支付方式");
            }
            mSelect = item;
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 验货支付-103049 刷卡、现金
     */
    private void inspectionPay(String paymentWay) {
        mRootView.setVisibility(View.GONE);
        new AlertDialog.Builder(this)
                .setPositiveButton("确定", (dialog, which) -> mPresenter.inspectionPay(paymentWay))
                .setNegativeButton("取消", (dialog, which) -> mRootView.setVisibility(View.VISIBLE))
                .setTitle("确认使用这种收款方式")
                .show();
    }

    /**
     * 调起收银台
     *
     * @param payType 支付类型
     */
    private void callCashier(String payType) {
        mRootView.setVisibility(View.GONE);
        mPresenter.getCashier(payType);
    }

    private class ListItemAdapter extends BaseQuickAdapter<PayWayBean, BaseViewHolder> {
        ListItemAdapter(@Nullable List<PayWayBean> data) {
            super(R.layout.item_order_settlement, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PayWayBean item) {
            ((GlideImageView) helper
                    .getView(R.id.ios_pay_icon))
                    .setImageURL(item.getImgPath());
            helper
                    .setText(R.id.ios_pay_method, item.getPayMethodName())
                    .getView(R.id.ios_pay_method).setSelected(item == mSelect);
        }
    }
}
