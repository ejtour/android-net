package com.hll_sc_app.app.invoice.select.shop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.invoice.search.InvoiceSearchActivity;
import com.hll_sc_app.app.invoice.select.order.SelectOrderActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

@Route(path = RouterConfig.INVOICE_SELECT_SHOP)
public class SelectShopActivity extends BaseLoadActivity implements ISelectShopContract.ISelectShopView {
    @BindView(R.id.iss_search_view)
    SearchView mSearchView;
    @BindView(R.id.iss_list_view)
    RecyclerView mListView;
    @BindView(R.id.iss_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.iss_commit)
    TextView mCommit;
    @BindView(R.id.iss_select_all)
    TextView mSelectAll;
    private ISelectShopContract.ISelectShopPresenter mPresenter;
    private SelectShopAdapter mAdapter;
    private EmptyView mEmptyView;
    private boolean mIsShop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_select_shop);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = SelectShopPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mAdapter = new SelectShopAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserShopBean item = mAdapter.getItem(position);
            if (item == null) return;
            item.setSelect(!item.isSelect());
            mAdapter.notifyDataSetChanged();
            updateBottomBar();
        });
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                InvoiceSearchActivity.start(SelectShopActivity.this,
                        searchContent, isShop() ? "1" : "0");
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.start();
            }
        });
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

    private void updateBottomBar() {
        int count = 0;
        List<PurchaserShopBean> list = mAdapter.getData();
        for (PurchaserShopBean bean : list) {
            if (bean.isSelect()) {
                count++;
            }
        }
        mCommit.setEnabled(count > 0);
        mCommit.setText(String.format("去开票 (%s) ", count));
        mSelectAll.setSelected(count != 0 && count == list.size());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            mIsShop = data.getIntExtra("index", 1) == 1;
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public void setListData(List<PurchaserShopBean> beans, boolean isMore) {
        if (isMore) {
            if (!CommonUtils.isEmpty(beans))
                mAdapter.addData(beans);
        } else {
            mAdapter.setNewData(beans);
            if (CommonUtils.isEmpty(beans)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("暂无门店列表");
            }
            updateBottomBar();
        }
        mRefreshLayout.setEnableLoadMore(beans != null && beans.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public boolean isShop() {
        return mIsShop;
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
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start).create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @OnClick(R.id.iss_commit)
    public void commit() {
        List<PurchaserShopBean> list = new ArrayList<>();
        List<PurchaserShopBean> data = mAdapter.getData();
        for (PurchaserShopBean bean : data) {
            if (bean.isSelect()) list.add(bean);
        }
        String groupID = list.get(0).getPurchaserID();
        for (PurchaserShopBean bean : list) {
            if (!groupID.equals(bean.getPurchaserID())) {
                showToast("只有同集团门店才可合并开票");
                return;
            }
        }
        SelectOrderActivity.start(list);
    }

    @OnClick(R.id.iss_select_all)
    public void selectAll(View view) {
        for (PurchaserShopBean bean : mAdapter.getData()) {
            bean.setSelect(!view.isSelected());
        }
        mAdapter.notifyDataSetChanged();
        updateBottomBar();
    }
}
