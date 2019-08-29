package com.hll_sc_app.app.report.produce;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.filter.DateParam;
import com.hll_sc_app.bean.report.produce.ProduceBean;
import com.hll_sc_app.bean.report.produce.ProduceSummaryResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.report.ExcelFooter;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
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
 * @since 2019/8/28
 */
@Route(path = RouterConfig.REPORT_PRODUCE_STATISTIC)
public class ProduceSummaryActivity extends BaseLoadActivity implements IProduceSummaryContract.IProduceSummaryView {
    private static final int COLUMN_NUM = 12;
    private static final int[] WIDTH_ARRAY = {80, 100, 60, 60, 60, 60, 60, 60, 100, 70, 70, 70};
    @BindView(R.id.rps_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rps_arrow)
    TriangleView mArrow;
    @BindView(R.id.rps_date)
    TextView mDate;
    @BindView(R.id.rps_excel_layout)
    ExcelLayout mExcelLayout;
    private ContextOptionsWindow mOptionsWindow;
    private final DateParam mParam = new DateParam();
    private DateRangeWindow mDateRangeWindow;
    private ExcelFooter mFooter;
    private IProduceSummaryContract.IProduceSummaryPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_produce_summary);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        mFooter = new ExcelFooter(this);
        mFooter.updateChildView(COLUMN_NUM);
        mFooter.updateItemData(generateColumnData());
        mExcelLayout.setHeaderView(View.inflate(this, R.layout.view_report_produce_summary_header, null));
        mExcelLayout.setColumnDataList(generateColumnData());
        mExcelLayout.setFooterView(mFooter);
        mExcelLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
        mPresenter = ProduceSummaryPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void showOptionsWindow(View v) {
        /*if (mOptionsWindow == null) {
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
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);*/
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]));
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[6] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[7] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[7]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[8] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[8]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[9] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[9]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[10] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[10]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[11] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[11]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return array;
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
        mExcelLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    private void updateDateText() {
        mDate.setText(String.format("%s - %s", mParam.getFormatStartDate(Constants.SLASH_YYYY_MM_DD),
                mParam.getFormatEndDate(Constants.SLASH_YYYY_MM_DD)));
    }

    @Override
    public void setData(ProduceSummaryResp resp, boolean append) {
        CharSequence[] array = {};
        mFooter.updateRowDate(resp.getTotal().convertToRowData().toArray(array));
        mExcelLayout.setEnableLoadMore(!CommonUtils.isEmpty(resp.getList()) && resp.getList().size() == 20);
        List<List<CharSequence>> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(resp.getList())) {
            for (ProduceBean bean : resp.getList()) {
                list.add(bean.convertToRowData());
            }
        }
        mExcelLayout.setData(list, append);
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
