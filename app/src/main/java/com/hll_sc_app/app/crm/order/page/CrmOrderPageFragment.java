package com.hll_sc_app.app.crm.order.page;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.filter.CrmOrderParam;
import com.hll_sc_app.bean.order.search.OrderSearchBean;
import com.hll_sc_app.bean.order.shop.OrderShopBean;
import com.hll_sc_app.bean.order.shop.OrderShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.order.OrderFilterView;
import com.hll_sc_app.widget.order.OrderShopInfoWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/4
 */

public class CrmOrderPageFragment extends BaseLazyFragment implements ICrmOrderPageContract.ICrmOrderPageView {
    @BindView(R.id.cop_filter_view)
    OrderFilterView mFilterHeader;
    @BindView(R.id.cop_label)
    TextView mLabel;
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshView;
    private CrmOrderPageAdapter mAdapter;
    Unbinder unbinder;
    @Autowired(name = "status")
    int mBillStatus;
    private CrmOrderParam mOrderParam;
    private OrderShopInfoWindow mShopInfoWindow;
    private ICrmOrderPageContract.ICrmOrderPagePresenter mPresenter;
    private EmptyView mEmptyView;
    private OrderShopBean mCurBean;

    public static CrmOrderPageFragment newInstance(CrmOrderParam param, int billStatus) {
        Bundle args = new Bundle();
        args.putInt("status", billStatus);
        CrmOrderPageFragment fragment = new CrmOrderPageFragment();
        fragment.setArguments(args);
        fragment.mOrderParam = param;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        mPresenter = CrmOrderPagePresenter.newInstance(mOrderParam, mBillStatus);
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_crm_order_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        updateHeaderLabel(0, 0, 0);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(8)));
        mAdapter = new CrmOrderPageAdapter(mBillStatus);
        mListView.setAdapter(mAdapter);
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurBean = mAdapter.getItem(position);
            if (mCurBean == null) return;
            switch (view.getId()) {
                case R.id.cop_all_orders_label:
                case R.id.cop_ave_order_label:
                    showToast("订单列表待添加");
                    break;
                case R.id.cop_shop_detail:
                    showShopInfoWindow(view);
                    break;
                case R.id.cop_make_order:
                    showToast("代客下单待添加");
                    break;
            }
        });
    }

    private void showShopInfoWindow(View view) {
        if (mShopInfoWindow == null)
            mShopInfoWindow = new OrderShopInfoWindow(requireActivity());
        mShopInfoWindow.setData(mCurBean).showAtLocationCompat(view);
    }

    @Override
    protected void initData() {
        if ("1".equals(mOrderParam.getActionType()))
            mFilterHeader.setVisibility(View.GONE);
        else mFilterHeader.setData(mOrderParam);
        mPresenter.start();
    }

    @OnClick(R.id.cop_filter_view)
    public void cancelFilter() {
        mOrderParam.cancelTimeInterval();
        EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe
    public void handleOrderEvent(OrderEvent event) {
        switch (event.getMessage()) {
            case OrderEvent.SEARCH_WORDS:
                if (isFragmentVisible())
                    mOrderParam.setSearchBean((OrderSearchBean) event.getData());
            case OrderEvent.REFRESH_LIST:
                setForceLoad(true);
                lazyLoad();
                break;
        }
    }

    private void updateHeaderLabel(int billNum, double amount, int shopNum) {
        String amountText = CommonUtils.formatMoney(amount);
        int color = ContextCompat.getColor(requireContext(), R.color.color_666666);
        String source = String.format("订单量：%s笔 订单金额：%s元 %s下单门店：%s家",
                billNum, amountText,
                mBillStatus == 0 ? "未" : "",
                shopNum);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(color),
                source.indexOf("笔") - String.valueOf(billNum).length(), source.indexOf("笔"),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color),
                source.indexOf("元") - amountText.length(), source.indexOf("元"),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color),
                source.indexOf("家") - String.valueOf(shopNum).length(), source.indexOf("家"),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mLabel.setText(ss);
    }

    @Override
    public void setShopListData(OrderShopResp resp, boolean append) {
        updateHeaderLabel(resp.getBillNum(), resp.getAmount(), resp.getShopNum());
        if (append) {
            if (!CommonUtils.isEmpty(resp.getOrders())) {
                mAdapter.addData(resp.getOrders());
            }
        } else {
            if (CommonUtils.isEmpty(resp.getOrders())) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("哎呀，还没有订单呢");
            }
            mAdapter.setNewData(resp.getOrders(), mOrderParam.getActionType());
        }
        mRefreshView.setEnableLoadMore(resp.getOrders() != null && resp.getOrders().size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
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
            mEmptyView = EmptyView.newBuilder(requireActivity())
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
