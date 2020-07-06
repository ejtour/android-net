package com.hll_sc_app.app.order.statistic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.statistic.shop.ShopOrderStatisticActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.statistic.OrderStatisticBean;
import com.hll_sc_app.bean.order.statistic.OrderStatisticResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.order.OrderStatisticHeader;
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
 * @since 2020/5/19
 */

@Route(path = RouterConfig.ORDER_STATISTIC)
public class OrderStatisticActivity extends BaseLoadActivity implements OnTabSelectListener, IOrderStatisticContract.IOrderStatisticView {
    @BindView(R.id.aos_title)
    TextView mTitle;
    @BindView(R.id.aos_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.aos_header)
    OrderStatisticHeader mHeaderView;
    @BindView(R.id.aos_list_view)
    RecyclerView mListView;
    @Autowired(name = "object0")
    int mCurIndex;
    private ContextOptionsWindow mOptionsWindow;
    private IOrderStatisticContract.IOrderStatisticPresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private OrderStatisticAdapter mAdapter;

    /**
     * 当前选中
     *
     * @param index 0-今日 1-本周 2-本月
     */
    public static void start(int index) {
        RouterUtil.goToActivity(RouterConfig.ORDER_STATISTIC, index);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_order_statistic);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        mReq.put("optype", "1");
        mPresenter = OrderStatisticPresenter.newInstance();
        mPresenter.register(this);
        mHeaderView.setCurrentTab(mCurIndex);
        onTabSelect(mCurIndex);
    }

    private void initView() {
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(12)));
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new OrderStatisticAdapter();
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            OrderStatisticBean item = mAdapter.getItem(position);
            if (item == null) return;
            ShopOrderStatisticActivity.start(item, !showSummary(), mCurIndex + 1);
        });
        mHeaderView.setOnTabSelectListener(this);
        mHeaderView.showSummary(showSummary());
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

    @OnClick(R.id.aos_close)
    public void finish() {
        super.finish();
    }

    @OnClick(R.id.aos_title)
    public void showOptionWindow(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this);
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(OptionType.OPTION_ORDER_SHOP));
            list.add(new OptionsBean(OptionType.OPTION_NOT_ORDER_SHOP));
            mOptionsWindow.refreshList(list);
            mOptionsWindow.setItemGravity(Gravity.CENTER);
            int space = UIUtils.dip2px(20);
            mOptionsWindow.setListPadding(space, 0, space, 0);
            mOptionsWindow.setListener((adapter, view1, position) -> {
                mOptionsWindow.dismiss();
                OptionsBean item = (OptionsBean) adapter.getItem(position);
                if (item == null) return;
                String label = item.getLabel();
                if (mTitle.getText().equals(label)) return;
                mTitle.setText(label);
                mReq.put("optype", showSummary() ? "1" : "2");
                mHeaderView.showSummary(showSummary());
                mAdapter.setNewData(null);
                mPresenter.start();
            });
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.CENTER_HORIZONTAL);
    }

    @Override
    public void onTabSelect(int position) {
        mAdapter.setNewData(null);
        mCurIndex = position;
        mReq.put("timetype", String.valueOf(position + 1));
        mPresenter.refresh();
    }

    @Override
    public void onTabReselect(int position) {
        // no-op
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(OrderStatisticResp resp, boolean append) {
        List<OrderStatisticBean> list = resp.getList();
        boolean summary = showSummary();
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list, !summary, getPrefix());
        }
        if (summary) {
            mHeaderView.setData(resp);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 10 && !summary);
    }

    private String getPrefix() {
        if (showSummary()) return "";
        return mCurIndex == 0 ? "昨日" : mCurIndex == 1 ? "上周" : "上月";
    }

    private boolean showSummary() {
        return mTitle.getText().equals(OptionType.OPTION_ORDER_SHOP);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public boolean isNotOrder() {
        return !showSummary();
    }
}
