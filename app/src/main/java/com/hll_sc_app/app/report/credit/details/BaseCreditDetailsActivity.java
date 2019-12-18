package com.hll_sc_app.app.report.credit.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.refund.search.RefundSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.credit.CreditDetailsBean;
import com.hll_sc_app.bean.report.credit.CreditDetailsResp;
import com.hll_sc_app.bean.report.search.SearchResultItem;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.report.ExcelFooter;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

public abstract class BaseCreditDetailsActivity extends BaseLoadActivity implements ICreditDetailsContract.ICreditDetailsView {
    @BindView(R.id.rdd_date)
    TextView mDate;
    @BindView(R.id.rdd_arrow)
    TriangleView mArrow;
    @BindView(R.id.rdd_excel_layout)
    ExcelLayout mExcel;
    @BindView(R.id.rdd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rdd_search_view)
    SearchView mSearchView;
    private DateRangeWindow mDateRangeWindow;
    private ICreditDetailsContract.ICreditDetailsPresenter mPresenter;
    private ExcelFooter mFooter;
    private ContextOptionsWindow mOptionsWindow;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_receive_diff_details);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("groupIDs", UserConfig.getGroupID());
        Date date = new Date();
        mDate.setTag(R.id.date_start, CalendarUtils.getDateBefore(date, 29));
        mDate.setTag(R.id.date_end, date);
        updateSelectedDate();
        mPresenter = CreditDetailsPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        initExcel();
        if (isDaily()) {
            mSearchView.setVisibility(View.GONE);
            mTitleBar.setHeaderTitle("日应收账款明细");
        } else {
            mSearchView.setSearchTextLeft();
            mSearchView.setTextColorWhite();
            mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
            mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
                @Override
                public void click(String searchContent) {
                    RefundSearchActivity.start(BaseCreditDetailsActivity.this);
                }

                @Override
                public void toSearch(String searchContent) {
                    if (TextUtils.isEmpty(searchContent)) {
                        mReq.put("purchaserID", "");
                        mReq.put("shopID", "");
                    }
                    mPresenter.start();
                }
            });
        }
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
    }

    private void initExcel() {
        mFooter = new ExcelFooter(this);
        mFooter.updateChildView(getWidthArray().length);
        ExcelRow.ColumnData[] dataArray = generateColumnData();
        mExcel.setColumnDataList(dataArray);
        mFooter.updateItemData(dataArray);
        mExcel.setHeaderView(generateHeader());
        mExcel.setFooterView(mFooter);
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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

    private void updateSelectedDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        mReq.put("startDate", CalendarUtils.toLocalDate(startDate));
        mReq.put("endDate", CalendarUtils.toLocalDate(endDate));
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            SearchResultItem bean = data.getParcelableExtra("result");
            mReq.put(bean.getType() == 0 ? "purchaserID" : "shopID", bean.getShopMallId());
            mReq.put(bean.getType() == 0 ? "shopID" : "purchaserID", "");
            mSearchView.showSearchContent(!TextUtils.isEmpty(bean.getName()), bean.getName());
        }
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String tip) {
        Utils.exportFailure(this, tip);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    private void showExportOptionsWindow(View v) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option,
                            OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mOptionsWindow.dismiss();
                        mPresenter.export(null);
                    });
        }
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(getWidthArray().length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[getWidthArray().length];
        for (int i = 0; i < getWidthArray().length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(getWidthArray()[i]));
        }
        row.updateItemData(array);
        row.updateRowDate(getExcelHeaderText());
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    protected abstract ExcelRow.ColumnData[] generateColumnData();

    @OnClick(R.id.rdd_filter_btn)
    public void showDateRangeWindow(View view) {
        mArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectedDate();
                mPresenter.start();
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange((Date) mDate.getTag(R.id.date_start), (Date) mDate.getTag(R.id.date_end));
        }
        mDateRangeWindow.setOnDismissListener(() -> {
            mArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
            mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        });
        mDateRangeWindow.showAsDropDownFix(view);
    }

    @Override
    public void setData(CreditDetailsResp resp, boolean append) {
        CharSequence[] array = {};
        mFooter.updateRowDate(getFooterData(resp).toArray(array));
        List<CreditDetailsBean> records = resp.getRecords();
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(records) && records.size() == 20);
        mExcel.setData(records, append);
    }

    private List<CharSequence> getFooterData(CreditDetailsResp resp) {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        if (!isDaily()) {
            list.add("- -");
            list.add("- -");
        }
        list.add(CommonUtils.formatMoney(resp.getTotalReceiveAmount()));
        list.add(CommonUtils.formatMoney(resp.getTotalUntaxReceiveAmount()));
        list.add(CommonUtils.formatMoney(resp.getTotalPayAmount()));
        list.add(CommonUtils.formatMoney(resp.getTotalUnPayAmount()));
        list.add(resp.getTotalPayAmountRate());
        return list;
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    protected abstract int[] getWidthArray();

    protected abstract String[] getExcelHeaderText();
}
