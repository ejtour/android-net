package com.hll_sc_app.app.deliveryroute.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ShopAssociationSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.ShopSearchEvent;
import com.hll_sc_app.bean.other.RouteDetailBean;
import com.hll_sc_app.bean.other.RouteDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/28
 */
@Route(path = RouterConfig.DELIVERY_ROUTE_DETAIL)
public class RouteDetailActivity extends BaseLoadActivity implements IRouteDetailContract.IRouteDetailView {

    @BindView(R.id.drd_search_view)
    SearchView mSearchView;
    @BindView(R.id.drd_header)
    TextView mHeader;
    @BindView(R.id.drd_list_view)
    RecyclerView mListView;
    @BindView(R.id.drd_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "object0")
    String mDeliveryNo;
    private RouteDetailAdapter mAdapter;
    private String mShopID;
    private IRouteDetailContract.IRouteDetailPresenter mPresenter;

    /**
     * @param deliveryNo 运输单号
     */
    public static void start(String deliveryNo) {
        RouterUtil.goToActivity(RouterConfig.DELIVERY_ROUTE_DETAIL, deliveryNo);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_delivery_route_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new RouteDetailPresenter();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setSearchTextLeft();
        mSearchView.setTextColorWhite();
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, ShopAssociationSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mShopID = searchContent;
                    mPresenter.start();
                }
            }
        });
        mAdapter = new RouteDetailAdapter();
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
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
        setHeaderData(0, 0);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(priority = 3)
    public void handleSearchEvent(ShopSearchEvent event) {
        EventBus.getDefault().cancelEventDelivery(event);
        if (!TextUtils.isEmpty(event.getShopMallId())) {
            mSearchView.showSearchContent(true, event.getName());
            mShopID = event.getShopMallId();
            mPresenter.start();
        }
    }

    @Override
    @OnClick(R.id.drd_close)
    public void finish() {
        super.finish();
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setRouteDetailData(RouteDetailResp resp, boolean append) {
        setHeaderData(resp.getShopNum(), resp.getBillNum());
        List<RouteDetailBean> list = resp.getRecords();
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else mAdapter.setNewData(list);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    private void setHeaderData(int shopNum, int orderNum) {
        String source = String.format("门店数：%s个\t订单数：%s笔", shopNum, orderNum);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary))
                , source.indexOf("："), source.indexOf("个"), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_ed5655))
                , source.lastIndexOf("："), source.lastIndexOf("笔"), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        mHeader.setText(ss);
    }

    @Override
    public String getDeliveryNo() {
        return mDeliveryNo;
    }

    @Override
    public String getShopID() {
        return mShopID;
    }
}
