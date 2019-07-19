package com.hll_sc_app.app.order.transfer.details;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.relevance.goods.GoodsRelevanceListActivity;
import com.hll_sc_app.app.order.transfer.inventory.InventoryCheckActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.order.transfer.InventoryBean;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.widget.RemarkDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.order.OrderDetailHeader;
import com.hll_sc_app.widget.order.TransferDetailFooter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */
@Route(path = RouterConfig.ORDER_TRANSFER_DETAIL)
public class TransferDetailActivity extends BaseLoadActivity implements ITransferDetailContract.ITransferDetailView {
    public static final int REQ_KEY = 0x752;
    private TransferBean mBean;

    public static void start(String id) {
        RouterUtil.goToActivity(RouterConfig.ORDER_TRANSFER_DETAIL, id);
    }

    @BindView(R.id.atd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.atd_list_view)
    RecyclerView mListView;
    @BindView(R.id.atd_action)
    TextView mAction;
    @BindView(R.id.atd_failure_tip)
    TextView mFailureTip;
    @BindView(R.id.atd_failure_group)
    Group mFailureGroup;
    @Autowired(name = "object0", required = true)
    String mID;
    private ITransferDetailContract.ITransferDetailPresenter mPresenter;
    private OrderDetailHeader mDetailHeader;
    private TransferDetailFooter mDetailFooter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = TransferDetailPresenter.newInstance(mID);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        TransferDetailAdapter adapter = new TransferDetailAdapter();
        mDetailHeader = new OrderDetailHeader(this);
        adapter.addHeaderView(mDetailHeader);
        mDetailFooter = new TransferDetailFooter(this);
        adapter.addFooterView(mDetailFooter);
        mListView.setAdapter(adapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
    }

    @Override
    public void updateOrderData(TransferBean bean) {
        mBean = bean;
        mDetailHeader.setData(bean);
        mDetailFooter.setData(bean);
        if (bean.getStatus() == 3) {
            mFailureGroup.setVisibility(View.VISIBLE);
            mFailureTip.setText(bean.getFailReason());
        }
        mAction.setText(bean.getHomologous() == 0 ? "关联商品" : "商城下单");
        ((TransferDetailAdapter) mListView.getAdapter()).setNewData(bean.getDetailList(),
                bean.getShipperType() == 1);
    }

    @Override
    public void inventoryShortage(ArrayList<InventoryBean> list) {
        InventoryCheckActivity.start(this, list);
    }

    @Override
    public void handleStatusChanged() {
        EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
        finish();
    }

    private void showCancelDialog() {
        if (mBean == null) return;
        RemarkDialog.newBuilder(this)
                .setHint("可简要输入放弃原因...(非必填)")
                .setMaxLength(200)
                .setButtons("容我再想想", "确认放弃", (dialog, positive, content) -> {
                    dialog.dismiss();
                    if (positive) mPresenter.orderCancel(content, mBean.getBillSource());
                })
                .create().show();
    }

    @Override
    public void onBackPressed() {
        if (mBean != null)
            EventBus.getDefault().post(new OrderEvent(OrderEvent.UPDATE_TRANSFER_ITEM, mBean));
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (requestCode == InventoryCheckActivity.REQ_KEY)
                mPresenter.start();
            else if (requestCode == GoodsRelevanceListActivity.REQ_KEY && data != null)
                updateOrderData(data.getParcelableExtra(GoodsRelevanceListActivity.TRANSFER_KEY));
    }

    @OnClick({R.id.atd_action, R.id.atd_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.atd_action:
                if (((TextView) view).getText().equals("商城下单"))
                    mPresenter.mallOrder();
                else
                    GoodsRelevanceListActivity.start(this, mBean);
                break;
            case R.id.atd_cancel:
                showCancelDialog();
                break;
        }
    }
}
