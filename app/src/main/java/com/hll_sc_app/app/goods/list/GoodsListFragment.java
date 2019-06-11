package com.hll_sc_app.app.goods.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.widget.SimpleHorizontalDecoration;
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
    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private GoodsListAdapter mAdapter;
    private GoodsListFragmentPresenter mPresenter;
    private String mActionType;
    private String mProductStatus;

    public static GoodsListFragment newInstance(String actionType) {
        Bundle args = new Bundle();
        args.putString(ACTION_TYPE, actionType);
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
        SimpleHorizontalDecoration decoration = new SimpleHorizontalDecoration(0xFFEEEEEE, UIUtils.dip2px(1));
        decoration.setLineMargin(UIUtils.dip2px(80), 0, 0, 0);
        mRecyclerView.addItemDecoration(decoration);
        mAdapter = new GoodsListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public String getName() {
        return null;
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
        mRefreshLayout.setEnableLoadMore(list.size() == GoodsListReq.PAGE_SIZE);
    }

    public void refreshFragment(String productStatus) {
        this.mProductStatus = productStatus;
        setForceLoad(true);
        if (isFragmentVisible()) {
            lazyLoad();
        }
    }
}