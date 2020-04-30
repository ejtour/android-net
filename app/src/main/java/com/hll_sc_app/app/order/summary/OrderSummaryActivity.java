package com.hll_sc_app.app.order.summary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.summary.detail.OrderSummaryDetailActivity;
import com.hll_sc_app.app.order.summary.search.OrderSummarySearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.summary.SummaryPurchaserBean;
import com.hll_sc_app.bean.order.summary.SummaryShopBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.order.OrderStallSummaryDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */
@Route(path = RouterConfig.ORDER_SUMMARY)
public class OrderSummaryActivity extends BaseLoadActivity implements BaseQuickAdapter.OnItemChildClickListener, IOrderSummaryContract.IOrderSummaryView {
    public static final int REQ_CODE = 0x406;
    @BindView(R.id.aos_search_view)
    SearchView mSearchView;
    @BindView(R.id.aos_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aos_list_view)
    RecyclerView mListView;
    @BindView(R.id.aos_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyView;
    private OrderSummaryAdapter mAdapter;
    private IOrderSummaryContract.IOrderSummaryPresenter mPresenter;
    private String mSearchId;
    private int mSearchType;
    private ContextOptionsWindow mOptionsWindow;

    public static void start(Activity context) {
        RouterUtil.goToActivity(RouterConfig.ORDER_SUMMARY, context, REQ_CODE);
    }

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
                    mSearchType = 0;
                }
                mPresenter.start();
            }
        });
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        mAdapter = new OrderSummaryAdapter();
        mAdapter.setOnItemClickListener(this::onItemChildClick);
        mAdapter.setOnItemChildClickListener(this);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(12)));
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

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this);
            List<OptionsBean> list = Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_PEND_RECEIVE_GOODS));
            mOptionsWindow.refreshList(list);
            mOptionsWindow.setListener((adapter, view1, position) -> {
                mOptionsWindow.dismiss();
                mPresenter.export(null);
            });
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.END);
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
        Intent intent = new Intent();
        Object tag = view.getTag();
        if (tag instanceof SummaryPurchaserBean) {
            SummaryPurchaserBean bean = (SummaryPurchaserBean) tag;
            intent.putExtra("index", mSearchType == 0 ? 3 : 5);
            intent.putExtra("name", bean.getPurchaserName());
            intent.putExtra("value", mSearchType == 0 ? bean.getPurchaserID()
                    : bean.getShopList().get(0).getShipperID());
        } else if (tag instanceof SummaryShopBean) {
            SummaryShopBean shopBean = (SummaryShopBean) tag;
            if (view.getId() == R.id.oss_search_shop) {
                intent.putExtra("index", mSearchType == 0 ? 4 : 6);
                intent.putExtra("name", shopBean.getShopName());
                intent.putExtra("value", shopBean.getShopID());
                intent.putExtra("extraId", shopBean.getShipperID());
            } else if (view.getId() == R.id.oss_stall) {
                new OrderStallSummaryDialog(this).setData(shopBean).show();
                return;
            } else if (view.getId() == R.id.oss_goods) {
                OrderSummaryDetailActivity.start(shopBean);
                return;
            }
        }
        setResult(Constants.SEARCH_RESULT_CODE, intent);
        finish();
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
    public void setData(List<SummaryPurchaserBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("没有订单汇总数据哦");
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
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }
}
