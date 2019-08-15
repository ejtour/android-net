package com.hll_sc_app.app.invoice.entry;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.invoice.detail.InvoiceDetailActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.event.InvoiceEvent;
import com.hll_sc_app.bean.invoice.InvoiceBean;
import com.hll_sc_app.bean.invoice.InvoiceParam;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

public class InvoiceFragment extends BaseLazyFragment implements IInvoiceContract.IInvoiceView {
    private static final String INVOICE_STATUS = "invoice_status";
    @BindView(R.id.fi_date)
    TextView mDate;
    @BindView(R.id.fi_filter_group)
    Group mFilterGroup;
    @BindView(R.id.fi_list_view)
    RecyclerView mListView;
    @BindView(R.id.fi_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private InvoiceAdapter mAdapter;
    private Unbinder unbinder;
    @Autowired(name = "object")
    int mInvoiceStatus;
    private InvoiceParam mParam;
    private EmptyView mEmptyView;
    private IInvoiceContract.IInvoicePresenter mPresenter;

    public static InvoiceFragment newInstance(InvoiceParam param, int invoiceStatus) {
        Bundle args = new Bundle();
        args.putInt("object", invoiceStatus);
        InvoiceFragment fragment = new InvoiceFragment();
        fragment.setArguments(args);
        fragment.mParam = param;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        mPresenter = InvoicePresenter.newInstance(mInvoiceStatus, mParam);
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        if (mInvoiceStatus > 1 && TextUtils.isEmpty(UserConfig.getSalesmanID())) {
            mFilterGroup.setVisibility(View.VISIBLE);
            updateDate();
        } else mFilterGroup.setVisibility(View.GONE);
        mAdapter = new InvoiceAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            InvoiceBean item = mAdapter.getItem(position);
            if (item == null) return;
            InvoiceDetailActivity.start(requireActivity(), item.getId());
        });
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
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
        super.onDestroyView();
        mAdapter = null;
        mEmptyView = null;
        unbinder.unbind();
    }

    @OnClick(R.id.fi_filter_btn)
    public void selectDate() {
        ((InvoiceEntryActivity) requireActivity()).filterDate();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleInvoiceEvent(InvoiceEvent event) {
        switch (event.getMessage()) {
            case InvoiceEvent.RELOAD_LIST:
                updateDate();
                setForceLoad(true);
                lazyLoad();
                break;
            case InvoiceEvent.EXPORT:
                if (isFragmentVisible()) mPresenter.export(null);
                break;
        }
    }

    private void updateDate() {
        if (mFilterGroup.getVisibility() == View.VISIBLE) {
            mDate.setText(String.format("%s-%s",
                    CalendarUtils.format(mParam.getStartTime(), Constants.SLASH_YYYY_MM_DD),
                    CalendarUtils.format(mParam.getEndTime(), Constants.SLASH_YYYY_MM_DD)));
        }
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setListData(List<InvoiceBean> list, boolean isMore) {
        if (isMore) mAdapter.addData(list);
        else {
            mAdapter.setNewData(list);
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("暂无发票列表");
            }
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(requireActivity()).setOnClickListener(this::initData).create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(requireActivity(), mPresenter::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(requireActivity(), email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(requireActivity(), msg);
    }
}
