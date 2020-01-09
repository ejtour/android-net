package com.hll_sc_app.app.order.summary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.summary.search.OrderSummarySearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.order.summary.OrderSummaryWrapper;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.StickyItemDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */
@Route(path = RouterConfig.ORDER_SUMMARY)
public class OrderSummaryActivity extends BaseLoadActivity implements BaseQuickAdapter.OnItemChildClickListener, IOrderSummaryContract.IOrderSummaryView {
    @BindView(R.id.aos_search_view)
    SearchView mSearchView;
    @BindView(R.id.aos_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aos_list_view)
    RecyclerView mListView;
    @BindView(R.id.aos_empty_view)
    EmptyView mEmptyView;
    @BindView(R.id.aos_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private StickyItemDecoration mStickyItemDecoration;
    private OrderSummaryAdapter mAdapter;
    private IOrderSummaryContract.IOrderSummaryPresenter mPresenter;
    private String mSearchId;
    private int mSearchType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_order_summary);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mSearchView.setSearchTextLeft();
        mSearchView.setTextColorWhite();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                OrderSummarySearchActivity.start(OrderSummaryActivity.this,
                        searchContent, String.valueOf(getSearchType()));
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mSearchId = "";
                }
                mPresenter.start();
            }
        });
        mTitleBar.setRightBtnVisible(false);
        mStickyItemDecoration = new StickyItemDecoration();
        mListView.addItemDecoration(mStickyItemDecoration);
        mAdapter = new OrderSummaryAdapter();
        mAdapter.setOnItemChildClickListener(this);
        mListView.setAdapter(mAdapter);
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

    private void initData() {
        mPresenter = OrderSummaryPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            mSearchType = data.getIntExtra("index", 0);
            mSearchId = data.getStringExtra("value");
            mSearchView.showSearchContent(!TextUtils.isEmpty(name), name);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        LogUtil.d("vixb-ddd", "position = " + position + " tag = " + view.getTag(R.id.base_tag_1));
    }

    @Override
    public int getSearchType() {
        return mSearchType;
    }

    @Override
    public String getSearchId() {
        return mSearchId;
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void setData(List<OrderSummaryWrapper> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mStickyItemDecoration.notifyChanged();
            if (CommonUtils.isEmpty(list)) {
                mEmptyView.reset();
                mEmptyView.setTips("没有订单汇总数据哦");
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 10);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET && CommonUtils.isEmpty(mAdapter.getData())) {
            mEmptyView.setNetError();
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
