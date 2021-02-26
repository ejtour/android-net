package com.hll_sc_app.app.goodsdemand.entry;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.GoodsDemandPresenter;
import com.hll_sc_app.app.goodsdemand.IGoodsDemandContract;
import com.hll_sc_app.app.goodsdemand.add.GoodsDemandAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

@Route(path = RouterConfig.GOODS_DEMAND_ENTRY)
public class GoodsDemandEntryActivity extends BaseLoadActivity implements IGoodsDemandContract.IGoodsDemandView {
    @BindView(R.id.gde_list_view)
    RecyclerView mListView;
    @BindView(R.id.gde_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private IGoodsDemandContract.IGoodsDemandPresenter mPresenter;
    private GoodsDemandEntryAdapter mAdapter;
    private EmptyView mEmptyView;
    private boolean mNewIntent;

    public static void start() {
        RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_ENTRY);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_demand_entry);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = GoodsDemandPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mAdapter = new GoodsDemandEntryAdapter();
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

    @OnClick(R.id.gde_add)
    public void add() {
        List<GoodsDemandBean> list = mAdapter.getData();
        if (CommonUtils.isEmpty(list)) {
            RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_ADD);
        } else {
            GoodsDemandBean goodsDemandBean = list.get(0);
            GoodsDemandAddActivity.start(goodsDemandBean.getPurchaserName(), goodsDemandBean.getPurchaserID());
        }
    }

    @Override
    public void handleData(List<GoodsDemandBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
            if (CommonUtils.isEmpty(list))
                RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_ADD);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        initEmptyView();
        mEmptyView.setNetError();
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mNewIntent = true;
        mPresenter.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mNewIntent) {
            mNewIntent = false;
        } else if (mAdapter.getData().size() == 0) {
            finish();
        }
    }
}
