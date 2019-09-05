package com.hll_sc_app.app.inspection.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.quotation.PurchaserSelectWindow;
import com.hll_sc_app.app.inspection.detail.InspectionDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.filter.DateShopParam;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.inspection.InspectionBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

@Route(path = RouterConfig.INSPECTION_LIST)
public class InspectionListActivity extends BaseLoadActivity implements IInspectionListContract.IInspectionListView {

    @BindView(R.id.rog_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rog_purchaser)
    TextView mPurchaser;
    @BindView(R.id.rog_purchaser_arrow)
    TriangleView mPurchaserArrow;
    @BindView(R.id.rog_date)
    TextView mDate;
    @BindView(R.id.rog_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.rog_list_view)
    RecyclerView mListView;
    @BindView(R.id.rog_refresh_view)
    SmartRefreshLayout mRefreshView;
    private DateRangeWindow mDateRangeWindow;
    private PurchaserSelectWindow mPurchaserWindow;
    private List<PurchaserBean> mPurchaserBeans;
    private IInspectionListContract.IInspectionListPresenter mPresenter;
    private final DateShopParam mParam = new DateShopParam();
    private InspectionListAdapter mAdapter;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_order_goods);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Date endDate = new Date();
        mParam.setEndDate(endDate);
        mParam.setStartDate(CalendarUtils.getDateBefore(endDate, 30));
        updateSelectedDate();
        mPresenter = InspectionListPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectedDate() {
        mDate.setText(String.format("%s - %s",
                mParam.getFormatStartDate(Constants.SLASH_YYYY_MM_DD),
                mParam.getFormatEndDate(Constants.SLASH_YYYY_MM_DD)));
    }

    private void initView() {
        mTitleBar.setRightBtnVisible(false);
        mTitleBar.setHeaderTitle("查看验货单");
        mPurchaser.setText("采购商");
        mAdapter = new InspectionListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            InspectionBean item = mAdapter.getItem(position);
            if (item == null) return;
            InspectionDetailActivity.start(item.getId());
        });
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

    @OnClick({R.id.rog_purchaser_btn, R.id.rog_date_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rog_purchaser_btn:
                if (mPurchaserBeans == null) {
                    mPresenter.getPurchaserList();
                    return;
                }
                showPurchaserWindow(view);
                break;
            case R.id.rog_date_btn:
                showDateRangeWindow(view);
                break;
        }
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showList(List<InspectionBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTipsTitle("您还没有收到采购商上传的验货单哦");
            }
        }
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void cachePurchaserList(List<PurchaserBean> list) {
        mPurchaserBeans = list;
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
                    .setOnClickListener(mPresenter::start).create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    private void showDateRangeWindow(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                if (start == null || end == null) return;
                String oldStart = mParam.getFormatStartDate();
                String oldEnd = mParam.getFormatEndDate();

                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTimeInMillis(start.getTimeInMillis());
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTimeInMillis(end.getTimeInMillis());
                mParam.setStartDate(calendarStart.getTime());
                mParam.setEndDate(calendarEnd.getTime());

                if (!mParam.getFormatStartDate().equals(oldStart) || !mParam.getFormatEndDate().equals(oldEnd)) {
                    updateSelectedDate();
                    mPresenter.reload();
                }
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mDateRangeWindow.setReset(false);
            Calendar start = Calendar.getInstance(), end = Calendar.getInstance();
            start.setTime(mParam.getStartDate());
            end.setTime(mParam.getEndDate());
            mDateRangeWindow.setSelectCalendarRange(start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1, start.get(Calendar.DATE),
                    end.get(Calendar.YEAR), end.get(Calendar.MONTH) + 1, end.get(Calendar.DATE));
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }

    private void showPurchaserWindow(View view) {
        if (mPurchaserBeans == null) {
            mPresenter.getPurchaserList();
            return;
        }
        mPurchaserArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mPurchaserWindow == null) {
            mPurchaserWindow = new PurchaserSelectWindow(this, mPurchaserBeans);
            mPurchaserWindow.setListener(bean -> {
                if (TextUtils.equals(bean.getPurchaserName(), "全部")) {
                    mPurchaser.setText("采购商");
                } else {
                    mPurchaser.setText(bean.getPurchaserName());
                }
                mParam.setShopIDs(bean.getPurchaserID());
                mPresenter.reload();
            });
            mPurchaserWindow.setOnDismissListener(() -> {
                mPurchaserArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mPurchaserWindow.showAsDropDownFix(view);
    }
}
