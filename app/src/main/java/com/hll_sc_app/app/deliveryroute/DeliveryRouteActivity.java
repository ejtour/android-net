package com.hll_sc_app.app.deliveryroute;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ShopAssociationSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.bean.event.ShopSearchEvent;
import com.hll_sc_app.bean.filter.DateStringParam;
import com.hll_sc_app.bean.other.RouteBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

@Route(path = RouterConfig.DELIVERY_ROUTE)
public class DeliveryRouteActivity extends BaseLoadActivity implements IDeliveryRouteContract.IDeliveryRouteView {
    private final DateStringParam mParam = new DateStringParam();
    @BindView(R.id.adr_search_view)
    SearchView mSearchView;
    @BindView(R.id.adr_arrow)
    TriangleView mArrow;
    @BindView(R.id.adr_date)
    TextView mDate;
    @BindView(R.id.adr_list_view)
    RecyclerView mListView;
    @BindView(R.id.adr_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private DeliveryRouteAdapter mAdapter;
    private DateWindow mDateWindow;
    private IDeliveryRouteContract.IDeliveryRoutePresenter mPresenter;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_delivery_route);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(priority = 2)
    public void handleSearchEvent(ShopSearchEvent event) {
        EventBus.getDefault().cancelEventDelivery(event);
        if (!TextUtils.isEmpty(event.getShopMallId())) {
            mSearchView.showSearchContent(true, event.getName());
            mParam.setExtra(event.getShopMallId());
            mPresenter.start();
        }
    }

    private void initData() {
        Date date = new Date();
        mParam.setStartDate(date);
        mDate.setText(CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD));
        mPresenter = DeliveryRoutePresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSearchView.setTextColorWhite();
        mSearchView.setSearchTextLeft();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, ShopAssociationSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mParam.setExtra(searchContent);
                    mPresenter.start();
                }
            }
        });
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mAdapter = new DeliveryRouteAdapter();
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

    @OnClick({R.id.adr_close, R.id.adr_filter_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.adr_close:
                finish();
                break;
            case R.id.adr_filter_btn:
                showDateWindow();
                break;
        }
    }

    protected void showDateWindow() {
        mArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateWindow == null) {
            mDateWindow = new DateWindow(this);
            mDateWindow.setSelectListener(date -> {
                mParam.setStartDate(date);
                mDate.setText(CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD));
                mPresenter.start();
            });
            mDateWindow.setOnDismissListener(() -> {
                mArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mDateWindow.setCalendar(mParam.getStartDate());
        mDateWindow.showAtLocation(getWindow().getDecorView(), Gravity.END, 0, 0);
    }

    @Override
    public void setRouteInfo(List<RouteBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("没有数据哦");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
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
            mEmptyView = EmptyView.newBuilder(this).setOnClickListener(mPresenter::start).create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
