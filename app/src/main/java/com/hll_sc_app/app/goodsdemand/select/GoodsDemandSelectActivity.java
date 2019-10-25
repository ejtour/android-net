package com.hll_sc_app.app.goodsdemand.select;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.GoodsDemandActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
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
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/24
 */

@Route(path = RouterConfig.GOODS_DEMAND_SELECT)
public class GoodsDemandSelectActivity extends BaseLoadActivity implements IGoodsDemandSelectContract.IGoodsDemandSelectView {

    @BindView(R.id.gds_search_view)
    SearchView mSearchView;
    @BindView(R.id.gds_list_view)
    RecyclerView mListView;
    @BindView(R.id.gds_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "object0")
    String mID;
    @Autowired(name = "object1")
    String mPurchaserID;
    private String mSearchWords;
    private GoodsDemandSelectAdapter mAdapter;
    private IGoodsDemandSelectContract.IGoodsDemandSelectPresenter mPresenter;
    private EmptyView mEmptyView;

    /**
     * @param id          反馈单id
     * @param purchaserID 采购商id
     */
    public static void start(String id, String purchaserID) {
        RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_SELECT, id, purchaserID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_goods_demand_select);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    private void initData() {
        mPresenter = GoodsDemandSelectPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mAdapter = new GoodsDemandSelectAdapter();
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(80), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
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
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(GoodsDemandSelectActivity.this,
                        searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mSearchWords = searchContent;
                mPresenter.start();
            }
        });
    }

    @OnClick(R.id.gds_confirm)
    public void confirm() {
        if (mAdapter.getCurBean() != null)
            mPresenter.confirm(mAdapter.getCurBean());
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
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
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void setData(List<GoodsBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("这里没有商品哦，请去添加上架商品");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public String getSearchWords() {
        return mSearchWords;
    }

    @Override
    public String getID() {
        return mID;
    }

    @Override
    public String getPurchaserID() {
        return mPurchaserID;
    }

    @Override
    public void success() {
        GoodsDemandActivity.start();
    }
}
