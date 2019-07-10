package com.hll_sc_app.app.aftersales.audit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.detail.AfterSalesDetailActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.event.AfterSalesEvent;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.RemarkDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.aftersales.AfterSalesAuditWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AuditFragment extends BaseLazyFragment implements IAuditFragmentContract.IAuditFragmentView {
    @BindView(R.id.srl_list)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mRefreshView;
    Unbinder unbinder;
    private AuditAdapter mAdapter;
    private IAuditFragmentContract.IAuditFragmentPresenter mPresenter;
    /**
     * 订单类型
     */
    private Integer mBillType;
    private AfterSalesBean mCurBean;

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
        View view = inflater.inflate(R.layout.layout_simple_refresh_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mAdapter = new AuditAdapter();
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mListView.setAdapter(mAdapter);
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
                    actionDriver();
                    break;
                case R.id.after_sales_actions_warehouse:
                    actionWarehouse();
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
                default:
                    break;
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        mAdapter = null;
        mListView.setAdapter(null);
        super.onDestroyView();
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
            if (CommonUtils.isEmpty(recordsBeans) && mAdapter.getEmptyView() == null)  // 设置空布局
                mAdapter.setEmptyView(EmptyView.newBuilder(getActivity()).setTipsTitle("搜索不到相关退货审核单").create());
            mAdapter.setNewData(recordsBeans);
        }
    }

    @Override
    public void actionSuccess() {
        mPresenter.requestDetails(mCurBean.getId());
    }

    @Override
    public void updateItem(AfterSalesBean bean) {
        if (getActivity() instanceof AuditActivity) {
            ((AuditActivity) getActivity()).refreshCurrentData(bean);
        }
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
        else mAdapter.removeData(mCurBean);
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
                break;
            case AfterSalesEvent.EXPORT_ORDER:
                if (isFragmentVisible()) exportOrder(null);
                break;
        }
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
    public void actionDriver() {
        showToast("司机提货待添加");
    }

    @Override
    public void actionWarehouse() {
        showToast("仓库收货待添加");
    }

    @Override
    public void actionCustomerService() {
        AfterSalesAuditWindow.create(getActivity())
                .canModify(mCurBean.canModify())
                .setCallback((payType, remark) ->
                        mPresenter.doAction(1, mCurBean.getId(),
                                mCurBean.getRefundBillStatus(), mCurBean.getRefundBillType(), payType,
                                remark))
                .showAtLocation(getView(), Gravity.CENTER, 0, 0);
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
}
