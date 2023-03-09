package com.hll_sc_app.app.report.purchase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.hll_sc_app.impl.IExportView;
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
    private int mRecordFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_purchase_summary);
        ButterKnife.bind(this);
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
        mRecordFlag = 0;
        if (CommonUtils.isEmpty(mAdapter.getData()) ||
                !CalendarUtils.toLocalDate(new Date()).equals(mAdapter.getData().get(0).getDate())) {
            mPresenter.getTodayData();
        } else toInput(mAdapter.getData().get(0).deepCopy());
    }

    private void toInput(PurchaseBean bean) {
        if (mRecordFlag == 0) bean.setPurchaserNum(-1);
        else bean.setCarNums(-1);
        PurchaseInputActivity.start(this, bean);
    }

    private void recordAmount() {
        mRecordFlag = 1;
        if (CommonUtils.isEmpty(mAdapter.getData()) ||
                !CalendarUtils.toLocalDate(new Date()).equals(mAdapter.getData().get(0).getDate())) {
            mPresenter.getTodayData();
        } else toInput(mAdapter.getData().get(0).deepCopy());
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
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mParam.setStartDate(start);
                mParam.setEndDate(end);
                updateDateText();
                mPresenter.start();
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange(mParam.getStartDate(), mParam.getEndDate());
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
    public void handleTodayData(PurchaseSummaryResp resp) {
        toInput(CommonUtils.isEmpty(resp.getRecords()) ? new PurchaseBean() : resp.getRecords().get(0));
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

    @Override
    public void exportReportID(String reportID, IExportView export) {
        Utils.exportReportID(this, reportID,export);
    }
}
