package com.hll_sc_app.app.aftersales.audit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.detail.AfterSalesDetailActivity;
import com.hll_sc_app.app.aftersales.goodsoperation.GoodsOperationActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.event.AfterSalesEvent;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.RemarkDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.aftersales.AfterSalesAuditDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AuditFragment extends BaseLazyFragment implements IAuditFragmentContract.IAuditFragmentView {
    @BindView(R.id.asa_list_view)
    RecyclerView mListView;
    @BindView(R.id.asa_refresh_view)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.asa_bottom_bar_stub)
    ViewStub mBottomBarStub;
    private View mBottomBarRoot;
    private TextView mConfirm;
    Unbinder unbinder;
    private AuditAdapter mAdapter;
    private IAuditFragmentContract.IAuditFragmentPresenter mPresenter;
    /**
     * 订单类型
     */
    private Integer mBillType;
    private AfterSalesBean mCurBean;
    private EmptyView mEmptyView;
    private ImageView mSelectAll;

    public static AuditFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        AuditFragment fragment = new AuditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = AuditFragmentPresenter.newInstance();
        mPresenter.register(this);
        if (getArguments() != null) {
            int type = getArguments().getInt("type");
            mBillType = type == 0 ? null : type;
        }
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_after_sales_audit, container, false);
        unbinder = ButterKnife.bind(this, view);
        mAdapter = new AuditAdapter();
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mListView.setAdapter(mAdapter);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        setListener();
        return view;
    }

    private void setListener() {
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurBean = mAdapter.getData().get(position);
            if (mCurBean == null) {
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
                case R.id.asa_thumbnail_wrapper:
                    actionViewDetails();
                    break;
                case R.id.asa_check:
                    mCurBean.setSelected(!mCurBean.isSelected());
                    adapter.notifyItemChanged(position);
                    updateBottomBar();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    private void updateBottomBar() {
        if (mBillType != null && ((mBillType == 1 && getAuditParam().getSourceType() == 2) || mBillType == 4)) {
            if (mBottomBarRoot == null) {
                mBottomBarRoot = mBottomBarStub.inflate();
                mConfirm = mBottomBarRoot.findViewById(R.id.abb_confirm);
                mConfirm.setOnClickListener(this::confirm);
                mSelectAll = mBottomBarRoot.findViewById(R.id.abb_select_all);
                mSelectAll.setOnClickListener(this::selectAll);
            }
            int size = mAdapter.getData().size();
            if (size > 0) {
                mAdapter.setCheckable(true);
                mBottomBarRoot.setVisibility(View.VISIBLE);
                int count = mAdapter.getSelectedCount();
                mConfirm.setText(String.format("批量同意(%s)", count));
                mSelectAll.setSelected(count == size);
                mConfirm.setEnabled(count > 0);
            } else mBottomBarRoot.setVisibility(View.GONE);
        } else {
            mAdapter.setCheckable(false);
            if (mBottomBarRoot != null) mBottomBarRoot.setVisibility(View.GONE);
        }
    }

    private void confirm(View view) {
        List<String> ids = new ArrayList<>();
        for (AfterSalesBean bean : mAdapter.getData()) {
            if (bean.isSelected()) ids.add(bean.getId());
        }
        mPresenter.doAction(mBillType,
                TextUtils.join(",", ids),
                mCurBean.getRefundBillStatus(),
                mCurBean.getRefundBillType(),
                null, null);
        mCurBean = null;
    }

    private void selectAll(View view) {
        for (AfterSalesBean resp : mAdapter.getData()) {
            resp.setSelected(!view.isSelected());
        }
        mAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        dispose();
        super.onDestroyView();
    }

    private void dispose() {
        mAdapter = null;
        mBottomBarRoot = null;
        mSelectAll = null;
        mConfirm = null;
        mEmptyView = null;
        unbinder.unbind();
    }

    @Override
    public AuditParam getAuditParam() {
        return ((AuditActivity) requireActivity()).getAuditParam();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshView.closeHeaderOrFooter();
    }

    @Override
    public Integer getBillStatus() {
        return mBillType;
    }

    @Override
    public void showList(List<AfterSalesBean> recordsBeans, boolean isMore) {
        if (isMore) mAdapter.addData(recordsBeans);
        else {
            if (CommonUtils.isEmpty(recordsBeans)) {// 设置空布局
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTipsTitle("搜索不到相关退货审核单");
            }
            mAdapter.setNewData(recordsBeans);
            updateBottomBar();
        }
    }

    @Override
    public void actionSuccess() {
        if (mCurBean != null) mPresenter.requestDetails(mCurBean.getId());
        else EventBus.getDefault().post(new AfterSalesEvent(AfterSalesEvent.REMOVE_SELECTED));
    }

    @Override
    public void updateItem(AfterSalesBean bean) {
        if (getActivity() instanceof AuditActivity)
            ((AuditActivity) getActivity()).refreshCurrentData(bean);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(requireActivity(), email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(requireActivity(), msg);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(requireActivity(), this::exportOrder);
    }

    void exportOrder(String email) {
        mPresenter.exportOrder(email);
    }

    private void replaceDetails(AfterSalesBean bean) {
        if (mBillType == null || mBillType == bean.getRefundBillStatus()) // 如果参数属于当前列表
            mAdapter.replaceData(mCurBean, bean);
        else if (mAdapter.getData().size() > 1)
            mAdapter.removeData(mCurBean);
        else showList(null, false);
    }

    @Subscribe
    public void handleAfterSalesEvent(AfterSalesEvent event) {
        switch (event.getMessage()) {
            case AfterSalesEvent.REFRESH_LIST:
                setForceLoad(true);
                lazyLoad(); // 懒加载
                break;
            case AfterSalesEvent.RELOAD_ITEM:
                if (isFragmentVisible() && mCurBean != null)
                    actionSuccess();
                break;
            case AfterSalesEvent.UPDATE_ITEM:
                if (!isFragmentVisible())  // 不可见时更新标记位
                    setForceLoad(true);
                else  // 刷新可见页面的数据
                    replaceDetails((AfterSalesBean) event.getData());
                break;
            case AfterSalesEvent.REMOVE_SELECTED:
                if (!isFragmentVisible()) setForceLoad(true);
                else removeSelected();
                break;
            case AfterSalesEvent.EXPORT_ORDER:
                if (isFragmentVisible()) exportOrder(null);
                break;
        }
    }

    private void removeSelected() {
        List<AfterSalesBean> list = new ArrayList<>();
        for (AfterSalesBean bean : mAdapter.getData()) {
            if (!bean.isSelected()) list.add(bean);
        }
        showList(list, false);
    }

    @Override
    public void actionReject() {
        RemarkDialog.newBuilder(requireActivity())
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
        RemarkDialog.newBuilder(requireActivity())
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
        GoodsOperationActivity.start(requireActivity(), mCurBean);
    }

    @Override
    public void actionCustomerService() {
        AfterSalesAuditDialog.create(getActivity())
                .canModify(mCurBean.canModify())
                .setCallback((payType, remark) ->
                        mPresenter.doAction(1, mCurBean.getId(),
                                mCurBean.getRefundBillStatus(), mCurBean.getRefundBillType(), payType,
                                remark))
                .show();
    }

    private void rejectReq(String reason) {
        mPresenter.doAction(5,
                mCurBean.getId(),
                mCurBean.getRefundBillStatus(),
                mCurBean.getRefundBillType(), null,
                reason);
    }

    @Override
    public void actionFinance() {
        mPresenter.doAction(4,
                mCurBean.getId(),
                mCurBean.getRefundBillStatus(),
                mCurBean.getRefundBillType(),
                null, null);
    }

    @Override
    public void actionViewDetails() {
        AfterSalesDetailActivity.start(requireActivity(), mCurBean);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    /**
     * 初始化空布局
     */
    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(requireActivity()).setOnClickListener(() -> {
                setForceLoad(true);
                lazyLoad();
            }).create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
