package com.hll_sc_app.app.order.inspection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.settle.OrderSettlementActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.bean.order.inspection.OrderInspectionReq;
import com.hll_sc_app.bean.order.inspection.OrderInspectionResp;
import com.hll_sc_app.bean.order.settle.PayWaysReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

@Route(path = RouterConfig.ORDER_INSPECTION)
public class OrderInspectionActivity extends BaseLoadActivity implements IOrderInspectionContract.IOrderInspectionView {
    public static final int REQ_CODE = 0x233;
    private Integer mInspectionMode;

    public static void start(Activity context, OrderResp resp) {
        RouterUtil.goToActivity(RouterConfig.ORDER_INSPECTION, context, REQ_CODE, resp);
    }

    @Autowired(name = "parcelable", required = true)
    OrderResp mResp;
    @BindView(R.id.oac_confirm)
    TextView mConfirm;
    @BindView(R.id.oac_list_view)
    RecyclerView mListView;
    @BindView(R.id.oac_title_bar)
    TitleBar mTitleBar;
    /**
     * 是否有状态修改
     */
    private boolean hasChanged;

    private OrderInspectionAdapter mAdapter;
    private IOrderInspectionContract.IOrderInspectionPresenter mPresenter;
    private OrderInspectionResp mInspectionResp;
    private OrderInspectionReq mReq = new OrderInspectionReq();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_inspection);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.setSubBillID(mResp.getSubBillID());
        mPresenter = OrderInspectionPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mAdapter = new OrderInspectionAdapter(mResp.getBillDetailList());
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(90), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
    }

    @Override
    public void confirmSuccess(OrderInspectionResp result) {
        mInspectionResp = result;
        hasChanged = true;
        mConfirm.setText("立即收款");
        showToast("修改验货数量成功");
        if (result.getPayType() == 1) {
            goToPayment(mResp);
        } else {
            if (result.getPayType() == 3 && result.getTotalAmount() > 0) {
                String source = "客户已支付的金额小于最终验货金额\n需补差价¥" + result.getTotalAmount();
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("该订单需要补差价")
                        .setMessage(processMsg(source))
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                goToPayment(mResp);
                            }
                        }, "我再看看", "收取差价").create().show();
                return;
            }
            onBackPressed();
        }
    }

    @Override
    public void gotInspectionMode(Integer result) {
        mInspectionMode = result;
    }

    @Override
    public void onBackPressed() {
        if (hasChanged) { // 如果有状态修改，进行回调
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @OnClick(R.id.oac_confirm)
    public void confirm(TextView view) {
        if ("立即收款".equals(view.getText().toString())) {
            goToPayment(mResp);
        } else if ("确认收货".equals(view.getText().toString())) {
            if (isModifyNumber()) {
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("确认要修改验货数量么")
                        .setMessage("您已经修改了部分商品的验货数量\n确认修改将按照验货数量计算订单金额")
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                sendReq();
                            }
                        }, "取消修改", "确认提交").create().show();
            } else if (mInspectionMode == null) {
                mPresenter.start();
            } else if (mInspectionMode == 2) {
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("请确认验货数量是否无误")
                        .setMessage("请您确认当前验货数量是否无误\n然后再确认验货")
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                sendReq();
                            }
                        }, "我再看看", "确认无误").create().show();
            } else sendReq();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OrderSettlementActivity.REQ_CODE)
            onBackPressed();
    }

    /**
     * 发送请求
     */
    private void sendReq() {
        mPresenter.confirmOrder(mReq);
    }

    /**
     * 判断是否有数量修改
     */
    private boolean isModifyNumber() {
        boolean isModify = false;
        List<OrderInspectionReq.OrderInspectionItem> confirmItemList = mAdapter.getReqList();
        mReq.setList(confirmItemList);
        for (int i = 0; i < mResp.getBillDetailList().size(); i++) {
            OrderDetailBean detailBean = mResp.getBillDetailList().get(i);
            OrderInspectionReq.OrderInspectionItem confirmItem = confirmItemList.get(i);
            // 如果验货数量与实际数量不符
            if (detailBean.getAdjustmentNum() != confirmItem.getInspectionNum()) isModify = true;
            List<OrderDepositBean> depositList = detailBean.getDepositList();
            if (!CommonUtils.isEmpty(depositList) && !isModify) {
                for (OrderDepositBean bean : depositList) {
                    // 如果押金产品数与验货数不同
                    if (bean.getProductNum() != bean.getRawProductNum()) {
                        isModify = true;
                        break;
                    }
                }
            }
        }
        return isModify;
    }

    private SpannableString processMsg(String source) {
        SpannableString ss = SpannableString.valueOf(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_ed5655)), source.indexOf("¥"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(1.5f), source.indexOf("¥"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 跳转支付
     */
    private void goToPayment(OrderResp data) {
        PayWaysReq.GroupList groupList = new PayWaysReq.GroupList();
        groupList.setAgencyID(data.getAgencyID());
        groupList.setGroupID(data.getGroupID());
        groupList.setPayee(String.valueOf(data.getPayee()));
        groupList.setPurchaserID(data.getPurchaserID());
        groupList.setShipperType(String.valueOf(data.getShipperType()));

        OrderSettlementActivity.start(this,
                mInspectionResp.getTotalAmount(),
                data.getSubBillID(), data.getPayType() == 1 ? 2 : 1,
                Collections.singletonList(groupList));
    }
}
