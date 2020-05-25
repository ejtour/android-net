package com.hll_sc_app.app.stockmanage.depot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.app.stockmanage.depot.detail.DepotDetailActivity;
import com.hll_sc_app.app.stockmanage.depot.edit.DepotEditActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.stockmanage.DepotResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 仓库管理
 */
@Route(path = RouterConfig.ACTIVITY_DEPOT)
public class DepotActivity extends BaseLoadActivity implements IDepotContract.IDepotView {
    private static final int REQUEST_CODE_TO_EDIT = 100;
    @BindView(R.id.ad_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.ad_search_view)
    SearchView mSearchView;
    @BindView(R.id.ad_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ad_list_view)
    RecyclerView mListView;
    private DepotResp mCurDepot;
    private Unbinder unbinder;
    private DepotAdapter mAdapter;
    private IDepotContract.IDepotPresenter mPresenter;
    private boolean mEnableDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_depot);
        unbinder = ButterKnife.bind(this);
        mPresenter = DepotPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(v -> {
            DepotEditActivity.start(this, REQUEST_CODE_TO_EDIT, null);
        });

        mSearchView.setTextColorWhite();
        mSearchView.setSearchTextLeft();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);

        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(DepotActivity.this, searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.loadList();
            }
        });

        mAdapter = new DepotAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurDepot = mAdapter.getItem(position);
            if (mCurDepot == null) return;
            switch (view.getId()) {
                case R.id.id_root:
                    if (mEnableDetail) {
                        DepotDetailActivity.start(this, REQUEST_CODE_TO_EDIT, mCurDepot.getId());
                    } else {
                        DepotEditActivity.start(this, REQUEST_CODE_TO_EDIT, mCurDepot);
                    }
                    break;
                case R.id.id_default:
                    mPresenter.setDefault(mCurDepot.getId());
                    break;
                case R.id.id_toggle:
                    mPresenter.toggleStatus(mCurDepot.getId(), 1 - mCurDepot.getIsActive());
                    break;
            }
        });
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(8)));
        mListView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
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

    @Override
    public void setData(List<DepotResp> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("喔唷，居然是「 空 」的").create());
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void toggleSuccess() {
        if (mCurDepot == null) return;
        for (DepotResp resp : mAdapter.getData()) {
            if (mCurDepot.getId().equals(resp.getId())) {
                mCurDepot.setIsActive(1 - mCurDepot.getIsActive());
                break;
            }
        }
        mAdapter.notifyItemChanged(mAdapter.getData().indexOf(mCurDepot));
    }

    @Override
    public void defaultIsOk() {
        if (mCurDepot == null) return;
        for (DepotResp resp : mAdapter.getData()) {
            if (mCurDepot.getId().equals(resp.getId())) {
                resp.setIsDefault(1);
            } else {
                resp.setIsDefault(0);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void enableDetail(boolean enable) {
        mEnableDetail = enable;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_TO_EDIT && resultCode == RESULT_OK) {
            mPresenter.refresh();
        }
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }
}
