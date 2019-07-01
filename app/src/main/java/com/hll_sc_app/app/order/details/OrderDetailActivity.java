package com.hll_sc_app.app.order.details;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.deliver.modify.ModifyDeliverInfoActivity;
import com.hll_sc_app.app.order.inspection.OrderInspectionActivity;
import com.hll_sc_app.app.order.reject.OrderRejectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.RemarkDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.order.OrderActionBar;
import com.hll_sc_app.widget.order.OrderDetailFooter;
import com.hll_sc_app.widget.order.OrderDetailHeader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */
@Route(path = RouterConfig.ORDER_DETAIL)
public class OrderDetailActivity extends BaseLoadActivity implements IOrderDetailContract.IOrderDetailView {

    private OrderResp mOrderResp;

    public static void start(String billID) {
        RouterUtil.goToActivity(RouterConfig.ORDER_DETAIL, billID);
    }

    @BindView(R.id.aod_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aod_list_view)
    RecyclerView mListView;
    @BindView(R.id.aod_bottom_bar)
    OrderActionBar mActionBar;
    @Autowired(name = "object0", required = true)
    String mBillID;
    @BindDimen(R.dimen.bottom_bar_height)
    int mBottomBarHeight;
    private IOrderDetailContract.IOrderDetailPresenter mPresenter;
    private OrderDetailHeader mDetailHeader;
    private OrderDetailFooter mDetailFooter;
    private boolean mHasChanged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = OrderDetailPresenter.newInstance(mBillID);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        OrderDetailAdapter adapter = new OrderDetailAdapter();
        mDetailHeader = new OrderDetailHeader(this);
        adapter.addHeaderView(mDetailHeader);
        mDetailFooter = new OrderDetailFooter(this);
        adapter.addFooterView(mDetailFooter);
        mListView.setAdapter(adapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
    }

    @Override
    public void updateOrderData(OrderResp resp) {
        mOrderResp = resp;
        mDetailHeader.setData(resp);
        mDetailFooter.setData(resp);
        if (CommonUtils.isEmpty(resp.getButtonList())) {
            mActionBar.setVisibility(View.GONE);
            ((ViewGroup.MarginLayoutParams) mListView.getLayoutParams()).bottomMargin = 0;
        } else {
            mActionBar.setVisibility(View.VISIBLE);
            ((ViewGroup.MarginLayoutParams) mListView.getLayoutParams()).bottomMargin = mBottomBarHeight;
            mActionBar.setData(resp.getButtonList(), mOrderResp.getDiffPrice());
        }
        ((OrderDetailAdapter) mListView.getAdapter()).setNewData(resp.getBillDetailList(),
                resp.getSubbillCategory() == 2);
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) {
            EventBus.getDefault().post(new OrderEvent(OrderEvent.UPDATE_ITEM, mOrderResp));
        }
        super.onBackPressed();
    }

    @Override
    public void handleStatusChanged() {
        mHasChanged = true;
        mPresenter.start();
    }

    @OnClick({R.id.oab_cancel, R.id.oab_modify,
            R.id.oab_receive, R.id.oab_deliver,
            R.id.oab_settle, R.id.oab_reject, R.id.oab_inspection})
    public void onActionClick(View view) {
        switch (view.getId()) {
            case R.id.oab_cancel:
                showCancelDialog();
                break;
            case R.id.oab_receive:
                mPresenter.orderReceive();
                break;
            case R.id.oab_deliver:
                mPresenter.orderDeliver();
                break;
            case R.id.oab_modify:
                ModifyDeliverInfoActivity.start(this, new ArrayList<>(mOrderResp.getBillDetailList()), mBillID);
                break;
            case R.id.oab_settle:
                showToast(((TextView) view).getText() + "待添加");
                break;
            case R.id.oab_reject:
                OrderRejectActivity.start(mOrderResp);
                break;
            case R.id.oab_inspection:
                OrderInspectionActivity.start(this, mOrderResp);
                break;
        }
    }

    private void showCancelDialog() {
        RemarkDialog.newBuilder(this)
                .setHint("可简要输入放弃原因...(非必填)")
                .setMaxLength(200)
                .setButtons("容我再想想", "确认放弃", (dialog, positive, content) -> {
                    dialog.dismiss();
                    if (positive) mPresenter.orderCancel(content);
                })
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == ModifyDeliverInfoActivity.REQ_KEY
                || requestCode == OrderInspectionActivity.REQ_CODE)) {
            if (requestCode == ModifyDeliverInfoActivity.REQ_KEY) {
                showToast("成功修改发货信息");
            }
            handleStatusChanged();
        }
    }
}
