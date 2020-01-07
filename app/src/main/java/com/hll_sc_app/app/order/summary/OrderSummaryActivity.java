package com.hll_sc_app.app.order.summary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.StickyItemDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public class OrderSummaryActivity extends BaseLoadActivity {
    @BindView(R.id.aos_search_view)
    SearchView mSearchView;
    @BindView(R.id.aos_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aos_list_view)
    RecyclerView mListView;
    @BindView(R.id.aos_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private StickyItemDecoration mStickyItemDecoration;
    private OrderSummaryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_order_summary);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mSearchView.setSearchTextLeft();
        mSearchView.setTextColorWhite();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mTitleBar.setRightBtnVisible(false);
        mStickyItemDecoration = new StickyItemDecoration();
        mListView.addItemDecoration(mStickyItemDecoration);
        mListView.setAdapter(mAdapter);
    }

    private void initData() {

    }
}
