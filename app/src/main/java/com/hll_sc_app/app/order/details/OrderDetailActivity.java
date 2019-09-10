package com.hll_sc_app.app.order.details;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.deliver.modify.ModifyDeliverInfoActivity;
import com.hll_sc_app.app.order.inspection.OrderInspectionActivity;
import com.hll_sc_app.app.order.reject.OrderRejectActivity;
import com.hll_sc_app.app.order.settle.OrderSettlementActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.trace.OrderTraceBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.RemarkDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.order.OrderActionBar;
import com.hll_sc_app.widget.order.OrderDetailFooter;
import com.hll_sc_app.widget.order.OrderDetailHeader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */
@Route(path = RouterConfig.ORDER_DETAIL)
public class OrderDetailActivity extends BaseLoadActivity implements IOrderDetailContract.IOrderDetailView, BaseQuickAdapter.OnItemClickListener {

    private OrderResp mOrderResp;
    private String mLabel;

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
    private ContextOptionsWindow mOptionsWindow;

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
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
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
        if (resp.getSubBillStatus() == 2 || resp.getSubBillStatus() == 3) {
            mTitleBar.setRightBtnVisible(true);
            mTitleBar.setRightBtnClick(this::showOptionsWindow);
        } else {
            mTitleBar.setRightBtnVisible(false);
            mTitleBar.setRightBtnClick(null);
        }
        if (CommonUtils.isEmpty(resp.getButtonList())) {
            mActionBar.setVisibility(View.GONE);
            ((ViewGroup.MarginLayoutParams) mListView.getLayoutParams()).bottomMargin = 0;
        } else {
            mActionBar.setVisibility(View.VISIBLE);
            ((ViewGroup.MarginLayoutParams) mListView.getLayoutParams()).bottomMargin = mBottomBarHeight;
            mActionBar.setData(resp.getButtonList(), mOrderResp.getDiffPrice());
        }
        ((OrderDetailAdapter) mListView.getAdapter()).setNewData(resp.getBillDetailList(), resp.getShipperType()>0);
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        if (mOrderResp.getSubBillStatus() == 2)
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ASSEMBLY));
        list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_OUT));
        mOptionsWindow.refreshList(list)
                .showAsDropDownFix(view, Gravity.END);
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

    @Override
    public void updateOrderTraceLog(List<OrderTraceBean> list) {
        mDetailHeader.setData(list);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, email -> {
            export(mLabel, email);
        });
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }

    @OnClick(R.id.aod_bottom_bar)
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
                OrderSettlementActivity.start(this,
                        mOrderResp.getFee(),
                        mOrderResp.getSubBillID(),
                        mOrderResp.getPayType() == 1 ? 2 : 1);
                break;
            case R.id.oab_reject:
                OrderRejectActivity.start(mOrderResp);
                break;
            case R.id.oab_inspection:
                OrderInspectionActivity.start(this, mOrderResp);
                break;
            case R.id.oab_refund:
                showToast("申请退换货待添加");
                break;
            case R.id.oab_refund_detail:
                showToast("查看退款进度待添加");
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
                || requestCode == OrderInspectionActivity.REQ_CODE || requestCode == OrderSettlementActivity.REQ_CODE)) {
            if (requestCode == ModifyDeliverInfoActivity.REQ_KEY) {
                showToast("成功修改发货信息");
            }
            handleStatusChanged();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mOptionsWindow.dismiss();
        OptionsBean item = (OptionsBean) adapter.getItem(position);
        if (item == null) return;
        mLabel = item.getLabel();
        export(mLabel, null);
    }

    private void export(String label, String email) {
        switch (label) {
            case OptionType.OPTION_EXPORT_ASSEMBLY:
                mPresenter.exportAssemblyOrder(mOrderResp.getSubBillID(), email);
                break;
            case OptionType.OPTION_EXPORT_OUT:
                mPresenter.exportDeliveryOrder(mOrderResp.getSubBillID(), email);
                break;
        }
    }
}
