package com.hll_sc_app.app.order.settle;

import android.app.Activity;
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
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.settle.CashierResp;
import com.hll_sc_app.bean.order.settle.PayWayBean;
import com.hll_sc_app.bean.order.settle.PayWaysReq;
import com.hll_sc_app.bean.order.settle.PayWaysResp;
import com.hll_sc_app.bean.order.settle.SettlementParam;
import com.hll_sc_app.bean.order.settle.SettlementResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.order.QRCodeDialog;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;

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
    public static void start(Activity context, double totalPrice, String subBillID, int payType,List<PayWaysReq.GroupList> groupLists) {
        SettlementParam param = new SettlementParam();
        param.setTotalPrice(totalPrice);
        param.setSubBillID(subBillID);
        param.setPayType(payType);
        param.setGroupLists(groupLists);
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
     * 是否成功
     */
    private boolean mIsSuccess;

    private QRCodeDialog mQRCodeDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_order_settlement);
        ButterKnife.bind(this);
        mTotalPrice = mSettlementParam.getTotalPrice();
        int payType = mSettlementParam.getPayType();
        String subBillID = mSettlementParam.getSubBillID();
        mPresenter = OrderSettlementPresenter.newInstance(subBillID);
        mPresenter.register(this);
        mPresenter.getPayWays(payType, mSettlementParam.getGroupLists());
        initView();
    }

    @Override
    protected void initSystemBar() {
        StatusBarUtil.setTranslucent(this);
    }

    private void initView() {
        mTotalAmount.setText(String.format("¥ %s", CommonUtils.formatMoney(mTotalPrice)));
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
        mQRCodeDialog = QRCodeDialog.create(this,
                resp.getTotalAmount(),
                resp.getCashierUrl(),
                TextUtils.equals(PayWaysResp.PAY_TYPE_WECHAT_OFFLINE, mSelect.getId()) ||
                        TextUtils.equals(PayWaysResp.PAY_TYPE_WECHAT_ONLINE, mSelect.getId()) ? "微信" : "支付宝");
        mQRCodeDialog.setOnDismissListener(dialog -> dealResult(mIsSuccess));
        mQRCodeDialog.show();
        mPresenter.queryPayResult(mPayOrderNo);
    }

    @Override
    public void handlePayStatus(SettlementResp resp) {
        if (mQRCodeDialog == null || !mQRCodeDialog.isShowing()) return;
        switch (resp.getStatus()) {
            case SettlementResp.STATUS_ING:
                Observable.timer(1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                        .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
                        .subscribe(aLong -> mPresenter.queryPayResult(mPayOrderNo));
                break;
            case SettlementResp.STATUS_SUCCESS:
                mIsSuccess = true;
                mQRCodeDialog.showPaySuccess();
                break;
            case SettlementResp.STATUS_FAILURE:
                showToast("支付失败");
                break;
        }
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
        }
    }

    /**
     * 验货支付-103049 刷卡、现金
     */
    private void inspectionPay(String paymentWay) {
        mRootView.setVisibility(View.GONE);
        TipsDialog.newBuilder(this)
                .setCancelable(false)
                .setTitle("确认使用这种收款方式？")
                .setButton((dialog, item) -> {
                    if (item == 0) {
                        mRootView.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    } else mPresenter.inspectionPay(paymentWay);
                }, "取消", "确定")
                .create().show();
        getWindow().setDimAmount(0.4f); // 修复黑屏
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
                    .setText(R.id.ios_pay_method, item.getPayMethodName());
        }
    }
}
