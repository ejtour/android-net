package com.hll_sc_app.app.goods.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
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
 * 首页商品管理列表Fragment
 *
 * @author 朱英松
 * @date 2018/6/11
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_LIST)
public class GoodsListFragment extends BaseLazyFragment implements GoodsListFragmentContract.IGoodsListView {
    public static final String ACTION_TYPE = "actionType";
    public static final String ACTION_TITLE = "actionTitle";
    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private GoodsListAdapter mAdapter;
    private GoodsListFragmentPresenter mPresenter;
    private String mActionType;
    private String mProductStatus;
    private String mName;
    private EmptyView mEmptyView;
    private EmptyView mNetEmptyView;
    private String mEmptyTips;

    public static GoodsListFragment newInstance(String actionType, String actionTitle) {
        Bundle args = new Bundle();
        args.putString(ACTION_TYPE, actionType);
        args.putString(ACTION_TITLE, actionTitle);
        GoodsListFragment fragment = new GoodsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = GoodsListFragmentPresenter.newInstance();
        mPresenter.register(this);
        Bundle args = getArguments();
        if (args != null) {
            mActionType = args.getString(ACTION_TYPE);
            mEmptyTips = "您还没有" + args.getString(ACTION_TITLE) + "噢";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_goods_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreGoodsList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryGoodsList(false);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.addItemDecoration(new SimpleDecoration(0xFFEEEEEE, UIUtils.dip2px(1)));
        mAdapter = new GoodsListAdapter(null);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            GoodsBean bean = (GoodsBean) adapter.getItem(position);
            if (bean != null) {
                showSpecWindow(bean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(requireContext()));
        mEmptyView = EmptyView.newBuilder(requireActivity()).setTips(mEmptyTips).create();
        mNetEmptyView = EmptyView.newBuilder(requireActivity()).setOnClickListener(() -> {
            setForceLoad(true);
            lazyLoad();
        }).create();
        mNetEmptyView.setNetError();
    }

    private void showSpecWindow(GoodsBean bean) {
        if (TextUtils.equals(bean.getProductStatus(), GoodsBean.PRODUCT_STATUS_DISABLE)) {
            showToast("该商品已被禁用");
            SwipeItemLayout.closeAllItems(mRecyclerView);
            return;
        }
        SpecStatusWindow window = new SpecStatusWindow(requireActivity(), bean);
        window.setListener(list -> {
            // 上下架
            if (!CommonUtils.isEmpty(list)) {
                mPresenter.updateSpecStatus(list);
            }
        });
        window.showAtLocation(requireActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            mAdapter.setEmptyView(mNetEmptyView);
        }
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getProductStatus() {
        return mProductStatus;
    }

    @Override
    public String getActionType() {
        return mActionType;
    }

    @Override
    public void showList(List<GoodsBean> list, boolean append) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(list.size() == GoodsListReq.PAGE_SIZE);
    }

    public void refreshFragment(String productStatus, String name) {
        this.mName = name;
        this.mProductStatus = productStatus;
        setForceLoad(true);
        if (isFragmentVisible()) {
            lazyLoad();
        }
    }
}