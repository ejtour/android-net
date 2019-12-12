package com.hll_sc_app.app.report.customerLack;

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
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.customerLack.detail.CustomerLackDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.report.customerLack.CustomerLackReq;
import com.hll_sc_app.bean.report.customerLack.CustomerLackSummary;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.aftersales.PurchaserShopSelectWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chukun
 * @since 2019/8/14
 */

@Route(path = RouterConfig.REPORT_CUSTOMER_LACK_SUMMARY)
public class CustomerLackSummaryActivity extends BaseLoadActivity implements ICustomerLackSummaryContract.ICustomerLackView {
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
    private ContextOptionsWindow mOptionsWindow;
    private DateRangeWindow mDateRangeWindow;
    private PurchaserShopSelectWindow mSelectionWindow;
    private List<PurchaserBean> mPurchaserBeans;
    private ICustomerLackSummaryContract.ICustomerLackPresenter mPresenter;
    CustomerLackReq mParam = new CustomerLackReq();
    private CustomerLackSummaryAdapter mAdapter;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_customer_lack_summary);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Date endDate = new Date();
        String endDateStr = CalendarUtils.format(endDate,CalendarUtils.FORMAT_LOCAL_DATE);
        mParam.setEndDate(endDateStr);
        mParam.setStartDate(endDateStr);
        updateSelectedDate();
        mPresenter = CustomerLackSummaryPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectedDate() {
        mDate.setText(String.format("%s - %s",
                CalendarUtils.getDateFormatString(mParam.getStartDate(),CalendarUtils.FORMAT_LOCAL_DATE,Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.getDateFormatString(mParam.getEndDate(), CalendarUtils.FORMAT_LOCAL_DATE,Constants.SLASH_YYYY_MM_DD)));
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        mAdapter = new CustomerLackSummaryAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CustomerLackSummary item = mAdapter.getItem(position);
            if (item == null) return;
            CustomerLackDetailActivity.start(item,mParam.getStartDate(),mParam.getEndDate());
        });
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mListView.setAdapter(mAdapter);
        mEmptyView = EmptyView.newBuilder(this).setImage(R.drawable.ic_char_empty).setTips("当前日期下没有统计数据噢").create();
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

    private void showOptionsWindow(View v) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mOptionsWindow.dismiss();
                        export(null);
                    });
        }
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    public void export(String email) {
        Gson gson = new Gson();
        String json = gson.toJson(mParam);
        mPresenter.export(json,email);
    }

    @OnClick({R.id.rog_purchaser_btn, R.id.rog_date_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rog_purchaser_btn:
                if (mPurchaserBeans == null) {
                    mPresenter.getPurchaserList("");
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showSummaryList(List<CustomerLackSummary> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, this::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }

    @Override
    public void refreshPurchaserList(List<PurchaserBean> list) {
        mPurchaserBeans = list;
        if (mSelectionWindow != null && mSelectionWindow.isShowing()) {
            mSelectionWindow.setLeftList(list);
        }
    }

    @Override
    public void refreshShopList(List<PurchaserShopBean> list) {
        mSelectionWindow.setRightList(list);
    }

    @Override
    public  CustomerLackReq getRequestParams(){
        return mParam;
    }

    private void showDateRangeWindow(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                String oldBegin = mParam.getStartDate();
                String oldEnd = mParam.getEndDate();
                if (start == null && end == null) {
                    mParam.setStartDate(null);
                    mParam.setEndDate(null);
                    mDate.setText("按日期筛选");
                    if (oldBegin != null && oldEnd != null) {
                        mPresenter.reload();
                    }
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    mParam.setStartDate(CalendarUtils.format(calendarStart.getTime(),CalendarUtils.FORMAT_LOCAL_DATE));
                    mParam.setEndDate(CalendarUtils.format(calendarEnd.getTime(),CalendarUtils.FORMAT_LOCAL_DATE));
                    updateSelectedDate();
                    if ((oldBegin == null && oldEnd == null) ||
                            !mParam.getStartDate().equals(oldBegin) ||
                            !mParam.getEndDate().equals(oldEnd)) {
                        mPresenter.reload();
                    }
                }
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mDateRangeWindow.setReset(false);
            Calendar start = Calendar.getInstance(), end = Calendar.getInstance();
            start.setTime(CalendarUtils.parseLocal(mParam.getStartDate(),CalendarUtils.FORMAT_LOCAL_DATE));
            end.setTime(CalendarUtils.parseLocal(mParam.getEndDate(),CalendarUtils.FORMAT_LOCAL_DATE));
            mDateRangeWindow.setSelectCalendarRange(start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1, start.get(Calendar.DATE),
                    end.get(Calendar.YEAR), end.get(Calendar.MONTH) + 1, end.get(Calendar.DATE));
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }

    private void showPurchaserWindow(View view) {
        mPurchaserArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mSelectionWindow == null) {
            mSelectionWindow = PurchaserShopSelectWindow.create(this, new PurchaserShopSelectWindow.PurchaserShopSelectCallback() {
                @Override
                public void onSelect(String purchaserID, String shopID, List<String> shopNameList) {
                    mSelectionWindow.dismiss();
                    mParam.setPurchaserID(purchaserID);
                    mParam.setShopID(shopID);
                    mPresenter.reload();
                    if (!CommonUtils.isEmpty(shopNameList)) {
                        mPurchaser.setText(TextUtils.join(",", shopNameList));
                    } else mPurchaser.setText("采购商");
                }

                @Override
                public boolean search(String searchWords, int flag, String purchaserID) {
                    if (flag == 0) mPresenter.getPurchaserList(searchWords);
                    else mPresenter.getShopList(purchaserID, searchWords);
                    return true;
                }

                @Override
                public void loadPurchaserShop(String purchaserID, String searchWords) {
                    mPresenter.getShopList(purchaserID, searchWords);
                }
            }).setMulti(true).setLeftList(mPurchaserBeans).setRightList(null);
            mSelectionWindow.setOnDismissListener(() -> {
                mPurchaserArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mSelectionWindow.showAsDropDownFix(view);
    }
}
