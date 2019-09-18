package com.hll_sc_app.app.aftersales.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.apply.AfterSalesApplyActivity;
import com.hll_sc_app.app.aftersales.audit.AuditAdapter;
import com.hll_sc_app.app.aftersales.detail.AfterSalesDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/10
 */
@Route(path = RouterConfig.AFTER_SALES_LIST)
public class AfterSalesListActivity extends BaseLoadActivity implements IAfterSalesListContract.IAfterSalesListView {
    public static void start(ArrayList<AfterSalesBean> list) {
        RouterUtil.goToActivity(RouterConfig.AFTER_SALES_LIST, list);
    }

    @BindView(R.id.srl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshView;
    @Autowired(name = "parcelable")
    ArrayList<AfterSalesBean> mList;
    private AuditAdapter mAdapter;
    private IAfterSalesListContract.IAfterSalesListPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_refresh_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        AfterSalesBean bean = mList.get(0);
        if (bean == null)
            return;
        mPresenter = AfterSalesListPresenter.newInstance(bean.getSubBillID());
        mPresenter.register(this);
        setData(mList, false);
    }

    private void initView() {
        mTitleBar.setHeaderTitle("查看退货单");
        mAdapter = new AuditAdapter();
        mAdapter.setOnItemClickListener(this::onItemClick);
        mAdapter.setOnItemChildClickListener(this::onItemClick);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
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
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(List<AfterSalesBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else mAdapter.setNewData(list);
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AfterSalesBean item = mAdapter.getItem(position);
        if (item == null) return;
        if (view.getId() == R.id.asa_reapply)
            AfterSalesApplyActivity.start(AfterSalesApplyParam.afterSalesFromAfterSales(item, item.getRefundBillType()));
        else AfterSalesDetailActivity.start(this, item.getId());
    }
}
