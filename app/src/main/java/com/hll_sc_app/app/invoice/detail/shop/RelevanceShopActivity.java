package com.hll_sc_app.app.invoice.detail.shop;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.invoice.InvoiceShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/23
 */
@Route(path = RouterConfig.INVOICE_DETAIL_SHOP)
public class RelevanceShopActivity extends BaseLoadActivity implements IRelevanceShopContract.IRelevanceShopView {
    /**
     * @param invoiceID 发票 id
     */
    public static void start(String invoiceID) {
        RouterUtil.goToActivity(RouterConfig.INVOICE_DETAIL_SHOP, invoiceID);
    }

    @BindView(R.id.srl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshView;
    private EmptyView mEmptyView;
    @Autowired(name = "object0")
    String mInvoiceID;
    private RelevanceShopAdapter mAdapter;
    private IRelevanceShopContract.IRelevanceShopPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_refresh_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = RelevanceShopPresenter.newInstance(mInvoiceID);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("关联门店");
        mAdapter = new RelevanceShopAdapter();
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
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
    }

    @Override
    public void setListData(List<InvoiceShopBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("没有门店数据");
            }
        }
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
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
            mEmptyView = EmptyView.newBuilder(this).setOnClickListener(mPresenter::start).create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
