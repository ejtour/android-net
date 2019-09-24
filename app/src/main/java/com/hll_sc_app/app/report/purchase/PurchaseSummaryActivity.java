package com.hll_sc_app.app.report.purchase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.purchase.input.PurchaseInputActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.filter.DateParam;
import com.hll_sc_app.bean.report.purchase.PurchaseBean;
import com.hll_sc_app.bean.report.purchase.PurchaseSummaryResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.report.PurchaseSummaryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/27
 */
@Route(path = RouterConfig.REPORT_PURCHASE_STATISTIC)
public class PurchaseSummaryActivity extends BaseLoadActivity implements IPurchaseSummaryContract.IPurchaseSummaryView {
    @BindView(R.id.rps_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rps_date)
    TextView mDate;
    @BindView(R.id.rps_arrow)
    TriangleView mArrow;
    @BindView(R.id.rps_list_view)
    RecyclerView mListView;
    @BindView(R.id.rps_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private IPurchaseSummaryContract.IPurchaseSummaryPresenter mPresenter;
    private final DateParam mParam = new DateParam();
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mOptionsWindow;
    private PurchaseSummaryAdapter mAdapter;
    private PurchaseSummaryHeader mHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_purchase_summary);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PurchaseInputActivity.REQ_CODE) {
            mPresenter.start();
        }
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        mAdapter = new PurchaseSummaryAdapter();
        mHeader = new PurchaseSummaryHeader(this);
        mAdapter.setHeaderView(mHeader);
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.rps_modify_amount:
                    recordAmount();
                    break;
                case R.id.rps_modify_logistics:
                    recordLogistics();
                    break;
            }
        });
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

    private void initData() {
        Date endDate = new Date();
        mParam.setEndDate(endDate);
        mParam.setStartDate(CalendarUtils.getFirstDateInMonth(endDate));
        updateDateText();
        mPresenter = PurchaseSummaryPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void showOptionsWindow(View v) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_SUMMARY_TABLE));
            list.add(new OptionsBean(R.drawable.ic_import_option, OptionType.OPTION_RECORD_PURCHASE_LOGISTICS));
            list.add(new OptionsBean(R.drawable.ic_import_option, OptionType.OPTION_RECORD_PURCHASE_AMOUNT));
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(list)
                    .setListener((adapter, view, position) -> {
                        mOptionsWindow.dismiss();
                        OptionsBean bean = (OptionsBean) adapter.getItem(position);
                        if (bean == null) return;
                        switch (bean.getLabel()) {
                            case OptionType.OPTION_EXPORT_SUMMARY_TABLE:
                                mPresenter.export(null);
                                break;
                            case OptionType.OPTION_RECORD_PURCHASE_LOGISTICS:
                                recordLogistics();
                                break;
                            case OptionType.OPTION_RECORD_PURCHASE_AMOUNT:
                                recordAmount();
                                break;
                        }
                    });
        }
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    private void recordLogistics() {
        PurchaseBean logPurchase;
        if (CommonUtils.isEmpty(mAdapter.getData()) ||
                !CalendarUtils.toLocalDate(new Date()).equals(mAdapter.getData().get(0).getDate())) {
            logPurchase = new PurchaseBean();
        } else {
            logPurchase = mAdapter.getData().get(0).deepCopy();
        }
        logPurchase.setPurchaserNum(-1);
        PurchaseInputActivity.start(this, logPurchase);
    }

    private void recordAmount() {
        PurchaseBean amountPurchase;
        if (CommonUtils.isEmpty(mAdapter.getData()) ||
                !CalendarUtils.toLocalDate(new Date()).equals(mAdapter.getData().get(0).getDate())) {
            amountPurchase = new PurchaseBean();
        } else {
            amountPurchase = mAdapter.getData().get(0).deepCopy();
        }
        amountPurchase.setCarNums(-1);
        PurchaseInputActivity.start(this, amountPurchase);
    }

    private void updateDateText() {
        mDate.setText(String.format("%s - %s", mParam.getFormatStartDate(Constants.SLASH_YYYY_MM_DD),
                mParam.getFormatEndDate(Constants.SLASH_YYYY_MM_DD)));
    }

    @OnClick(R.id.rps_filter_btn)
    public void dateFilter(View view) {
        mArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
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
                    updateDateText();
                    mPresenter.start();
                }
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
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

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setList(PurchaseSummaryResp resp, boolean append) {
        mHeader.setData(resp);
        if (append) {
            if (!CommonUtils.isEmpty(resp.getRecords())) {
                mAdapter.addData(resp.getRecords());
            }
        } else {
            mAdapter.setNewData(resp.getRecords());
        }
        mRefreshLayout.setEnableLoadMore(resp.getRecords() != null && resp.getRecords().size() == 20);
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
