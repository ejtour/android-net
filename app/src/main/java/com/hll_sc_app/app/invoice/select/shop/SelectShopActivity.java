package com.hll_sc_app.app.invoice.select.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ShopSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.event.SearchEvent;
import com.hll_sc_app.widget.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private ISelectShopContract.ISelectShopPresenter mPresenter;
    private String mSearchWords;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_select_shop);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initData() {
        mPresenter = SelectShopPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, ShopSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mSearchWords = searchContent;
                mPresenter.start();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSearchEvent(SearchEvent event) {
        if (TextUtils.isEmpty(event.getName())) return;
        mSearchView.showSearchContent(true, event.getName());
    }

    @Override
    public void setListData(List<PurchaserShopBean> beans, boolean isMore) {

    }

    @Override
    public String getSearchWords() {
        return mSearchWords;
    }
}
