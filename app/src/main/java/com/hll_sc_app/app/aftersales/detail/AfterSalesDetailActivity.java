package com.hll_sc_app.app.aftersales.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.common.AfterSalesHelper;
import com.hll_sc_app.app.aftersales.goodsoperation.GoodsOperationActivity;
import com.hll_sc_app.app.goods.relevance.goods.select.GoodsRelevanceSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.bean.aftersales.GenerateCompainResp;
import com.hll_sc_app.widget.RemarkDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.aftersales.AfterSalesActionBar;
import com.hll_sc_app.widget.aftersales.AfterSalesAuditDialog;
import com.hll_sc_app.widget.aftersales.AfterSalesDetailFooter;
import com.hll_sc_app.widget.aftersales.AfterSalesDetailHeader;
import com.hll_sc_app.widget.aftersales.ModifyUnitPriceDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */
@Route(path = RouterConfig.AFTER_SALES_DETAIL)
public class AfterSalesDetailActivity extends BaseLoadActivity implements IAfterSalesDetailContract.IAfterSalesDetailView {
    public static final int REQ_CODE = 0x533;
    @BindView(R.id.asd_header)
    TitleBar mHeaderBar;
    @BindView(R.id.asd_list)
    RecyclerView listView;
    @BindView(R.id.asd_action_bar)
    AfterSalesActionBar mActionBar;
    @Autowired(name = "object0")
    String mId;
    private AfterSalesDetailHeader mHeaderView;
    private AfterSalesDetailFooter mFooterView;
    private AfterSalesDetailAdapter mAdapter;
    private Unbinder unbinder;
    private IAfterSalesDetailContract.IAfterSalesDetailPresenter present;
    private AfterSalesBean mBean;
    /*
     * 是否修改了订单状态
     */
    private boolean hasChanged;

    /**
     * 跳转售后详情
     *
     * @param activity 上下文
     * @param id       售后订单 id
     */
    public static void start(Activity activity, String id) {
        Object[] array = {id};
        RouterUtil.goToActivity(RouterConfig.AFTER_SALES_DETAIL, activity, REQ_CODE, array);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sales_detail);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initView() {
        mAdapter = new AfterSalesDetailAdapter(null);
        listView.addItemDecoration(new SimpleDecoration(Color.WHITE, UIUtils.dip2px(5)));
        listView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            AfterSalesDetailsBean item = (AfterSalesDetailsBean) adapter.getItem(position);
            if (item == null) return;
            if (view.getId() == R.id.asd_change_price)
                new ModifyUnitPriceDialog(this)
                        .setProductName(item.getProductName())
                        .setProductSpec(item.getProductSpec())
                        .setRawPrice(item.getProductPrice())
                        .setModifyCallback(price -> modifyPrice(price, item.getId()))
                        .show();
        });

        mAdapter.setCallback(bean ->
                RouterUtil.goToActivity(RouterConfig.GOODS_RELEVANCE_LIST_SELECT,
                        this, GoodsRelevanceSelectActivity.REQ_CODE,
                        bean.convertToTransferDetail(mBean.getErpShopID())));

        // 头部状态栏
        mHeaderView = new AfterSalesDetailHeader(this);
        mAdapter.addHeaderView(mHeaderView);

        // 列表底部
        mFooterView = new AfterSalesDetailFooter(this);
        mAdapter.addFooterView(mFooterView);
    }

    private void initData() {
        present = AfterSalesDetailPresenter.newInstance(mId);
        present.register(this);
        present.start();
    }

    private void modifyPrice(String price, String detailsID) {
        present.modifyPrice(price, detailsID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (requestCode == GoodsRelevanceSelectActivity.REQ_CODE)
                delayLoad();
            else if (requestCode == GoodsOperationActivity.REQ_CODE)
                handleStatusChange();
    }

    @Override
    public void onBackPressed() {
        if (hasChanged) { // 如果有订单状态更改
            Intent intent = new Intent();
            intent.putExtra("parcelable", mBean);
            setResult(Activity.RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    private void delayLoad() {
        showLoading();
        listView.postDelayed(() -> {
            hideLoading();
            handleStatusChange();
        }, 1000);
    }

    @Override
    public void handleStatusChange() {
        hasChanged = true;
        present.start();
    }

    @Override
    public void showDetail(AfterSalesBean data) {
        mBean = data;
        updateData();
    }

    /**
     * 更新数据
     */
    private void updateData() {
        if (mBean == null) return;
        // 标题
        mHeaderBar.setHeaderTitle(AfterSalesHelper.getRefundInfoPrefix(mBean.getRefundBillType()) + "详情");
        mAdapter.setRefundBillType(mBean.getRefundBillType());
        mAdapter.setCanModify(mBean.canModify());
        mAdapter.setNewData(mBean.getDetailList());
        // 更新头部信息
        mHeaderView.setData(mBean);
        mFooterView.setData(mBean);

        // 底部操作栏
        mActionBar.setData(mBean.getButtonList());
    }

    @Override
    public void genereteComplainSuccess(GenerateCompainResp resp) {
        showToast("生成投诉单成功");
    }

    @OnClick({R.id.after_sales_actions_reject,
            R.id.after_sales_actions_driver_cancel,
            R.id.after_sales_actions_customer_service,
            R.id.after_sales_actions_finance,
            R.id.after_sales_actions_driver,
            R.id.after_sales_actions_warehouse,
            R.id.after_sales_actions_complain})
    public void onActionClick(View view) {
        if (mBean == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.after_sales_actions_reject:
                actionReject();
                break;
            case R.id.after_sales_actions_driver_cancel:
                actionCancel();
                break;
            case R.id.after_sales_actions_driver:
            case R.id.after_sales_actions_warehouse:
                actionGoodsOperation();
                break;
            case R.id.after_sales_actions_customer_service:
                actionCustomerService();
                break;
            case R.id.after_sales_actions_finance:
                actionFinance();
                break;
            case R.id.after_sales_actions_complain:
                SuccessDialog.newBuilder(this)
                        .setMessageTitle("确定生成投诉单么")
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessage(String.format("您确定将退货单%s\n生成投诉单么？", mBean.getRefundBillNo()))
                        .setButton((d, i) -> {
                            d.dismiss();
                            if (i == 1) {
                                present.genereteComplain(mBean);
                            }
                        }, "我再看看", "确定生成")
                        .create()
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    public void actionReject() {
        RemarkDialog.newBuilder(this)
                .setHint("可输入驳回理由，选填")
                .setMaxLength(200)
                .setButtons("容我再想想", "确认驳回", (dialog, positive, content) -> {
                    dialog.dismiss();
                    if (positive) rejectReq(content);
                })
                .create()
                .show();
    }

    @Override
    public void actionCancel() {
        RemarkDialog.newBuilder(this)
                .setHint("请填写取消说明")
                .setMaxLength(200)
                .setButtons("容我再想想", "确认取消", (dialog, positive, content) -> {
                    dialog.dismiss();
                    if (positive) rejectReq(content);
                })
                .create()
                .show();
    }

    @Override
    public void actionGoodsOperation() {
        GoodsOperationActivity.start(this, mBean);
    }

    @Override
    public void actionCustomerService() {
        if (mBean.canModify())
            AfterSalesAuditDialog.create(this)
                    .setCallback((payType, remark) ->
                            present.doAction(1, payType,
                                    mBean.getRefundBillStatus(), mBean.getRefundBillType(),
                                    remark))
                    .show();
        else RemarkDialog.newBuilder(this)
                .setHint("请输入审核备注（最多200字）")
                .setMaxLength(200)
                .setButtons("容我再想想", "确认通过", (dialog, positive, content) -> {
                    dialog.dismiss();
                    if (positive)
                        present.doAction(1, null,
                                mBean.getRefundBillStatus(), mBean.getRefundBillType(),
                                content);
                })
                .create()
                .show();
    }

    @Override
    public void actionFinance() {
        present.doAction(4, null,
                mBean.getRefundBillStatus(),
                mBean.getRefundBillType(),
                null);
    }

    private void rejectReq(String reason) {
        present.doAction(5, null,
                mBean.getRefundBillStatus(),
                mBean.getRefundBillType(),
                reason);
    }
}
