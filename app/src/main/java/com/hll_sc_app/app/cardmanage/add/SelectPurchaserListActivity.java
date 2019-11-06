package com.hll_sc_app.app.cardmanage.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.PurchaserNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择合作采购商
 *
 */
@Route(path = RouterConfig.ACTIVITY_CARD_MANAGE_ADD_SELECT_PURCHASER, extras = Constant.LOGIN_EXTRA)
public class SelectPurchaserListActivity extends BaseLoadActivity implements ISelectPurchaserContract.IView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private PurchaserListAdapter mAdapter;
    private ISelectPurchaserContract.IPresent mPresenter;
    private PurchaserBean mSelectPurchaser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage_select_purchaser);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = SelectPurchaserPresent.newInstance();
        mPresenter.register(this);
        mPresenter.queryList(true);
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new PurchaserListAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserBean bean = mAdapter.getItem(position);
            if (bean != null) {
                mSelectPurchaser = bean;
                mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SelectPurchaserListActivity.this,
                        searchContent, PurchaserNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryList(true);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //no-op
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryList(false);
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(true);
    }


    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name)) {
                mSearchView.showSearchContent(true, name);
            }
        }
    }

    @Override
    public void querySuccess(List<PurchaserBean> purchaseBeanList) {
        mAdapter.setEmptyView(EmptyView.newBuilder(this)
                .setTipsTitle(TextUtils.isEmpty(getSearchText()) ? "您还没有合作采购商噢" : "没有符合搜索条件的合作采购商")
                .create());
        mAdapter.setNewData(purchaseBeanList);
    }

    @Override
    public String getSearchText() {
        return mSearchView.getSearchContent();
    }

    private class PurchaserListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {
        public PurchaserListAdapter(@Nullable List<PurchaserBean> data) {
            super(R.layout.list_item_select_view, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            helper.setText(R.id.txt_name, item.getPurchaserName())
                    .setVisible(R.id.img_ok, mSelectPurchaser == item);
        }
    }
}
