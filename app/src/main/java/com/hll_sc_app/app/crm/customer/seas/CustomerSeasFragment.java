package com.hll_sc_app.app.crm.customer.seas;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.BaseCustomerActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/25
 */

public class CustomerSeasFragment extends BaseLazyFragment implements ICustomerSeasContract.ICustomerSeasView {
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshView;
    Unbinder unbinder;
    private CustomerSeasAdapter mAdapter;
    private ICustomerSeasContract.ICustomerSeasPresenter mPresenter;
    private EmptyView mEmptyView;

    public static CustomerSeasFragment newInstance() {
        return new CustomerSeasFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = CustomerSeasPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_simple_refresh_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mAdapter = new CustomerSeasAdapter();
        mListView.setAdapter(mAdapter);
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
    }

    void reload() {
        setForceLoad(true);
        lazyLoad();
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(List<PurchaserShopBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("还没有数据哦~");
            }
            mAdapter.setNewData(list);
        }
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(requireActivity())
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    @Override
    public String getSearchWords() {
        return ((BaseCustomerActivity) requireActivity()).getSearchWords();
    }
}
