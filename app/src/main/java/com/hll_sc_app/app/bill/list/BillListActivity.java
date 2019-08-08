package com.hll_sc_app.app.bill.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.bean.bill.BillParam;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/6
 */
@Route(path = RouterConfig.BILL_LIST)
public class BillListActivity extends BaseLoadActivity implements IBillListContract.IBillListView {

    @BindView(R.id.abl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.abl_purchase)
    TextView mPurchase;
    @BindView(R.id.abl_purchase_arrow)
    TriangleView mPurchaseArrow;
    @BindView(R.id.abl_date)
    TextView mDate;
    @BindView(R.id.abl_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.abl_type)
    TextView mType;
    @BindView(R.id.abl_type_arrow)
    TriangleView mTypeArrow;
    @BindView(R.id.abl_select_all)
    CheckBox mSelectAll;
    @BindView(R.id.abl_sum_label)
    TextView mSumLabel;
    @BindView(R.id.abl_amount)
    TextView mAmount;
    @BindView(R.id.abl_list_view)
    RecyclerView mListView;
    @BindView(R.id.abl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyView;
    private final BillParam mParam = new BillParam();
    private BillListAdapter mAdapter;
    private IBillListContract.IBillListPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initData() {
        Date endDate = new Date();
        mParam.setEndTime(endDate);
        mParam.setStateTime(CalendarUtils.getDateBefore(endDate, 30));
        mParam.setSettlementStatus(1);
        mPresenter = BillListPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mAdapter = new BillListAdapter();
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
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

    @OnClick(R.id.abl_purchase_btn)
    public void showPurchaseWindow(View view) {

    }

    @OnClick(R.id.abl_date_btn)
    public void showDateWindow(View view) {

    }

    @OnClick(R.id.abl_type_btn)
    public void showTypeWindow(View view) {

    }

    @Override
    public void setBillList(List<BillBean> list, boolean isMore) {
        if (isMore) mAdapter.addData(list);
        else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("当前没有对账单哦");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void actionSuccess() {

    }

    @Override
    public void bindEmail() {

    }

    @Override
    public void exportSuccess(String email) {

    }

    @Override
    public void exportFailure(String msg) {

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
            mEmptyView = EmptyView.newBuilder(this).setOnClickListener(mPresenter::start).create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
