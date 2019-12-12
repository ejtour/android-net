package com.hll_sc_app.app.report.receive.diff;

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
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.receive.ReceiveDiffBean;
import com.hll_sc_app.bean.report.receive.ReceiveDiffResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */
@Route(path = RouterConfig.REPORT_RECEIVE_DIFF)
public class ReceiveDiffActivity extends BaseLoadActivity implements IReceiveDiffContract.IReceiveDiffView {
    private static final int[] WIDTH_ARRAY = {120, 80, 80, 100, 90, 80, 80, 80};
    @BindView(R.id.rps_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rps_arrow)
    TriangleView mArrow;
    @BindView(R.id.rps_date)
    TextView mDate;
    @BindView(R.id.rps_excel_layout)
    ExcelLayout mExcel;
    private ContextOptionsWindow mOptionsWindow;
    private DateRangeWindow mDateRangeWindow;
    private ExcelFooter mFooter;
    private IReceiveDiffContract.IReceiveDiffPresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

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
        mTitleBar.setHeaderTitle("收货差异汇总");
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        mFooter = new ExcelFooter(this);
        mFooter.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] dataArray = generateColumnData();
        mFooter.updateItemData(dataArray);
        mExcel.setHeaderView(generateHeader());
        mExcel.setColumnDataList(dataArray);
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

    private void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        Date endDate = new Date();
        mDate.setTag(R.id.date_start, CalendarUtils.getDateBefore(endDate, 29));
        mDate.setTag(R.id.date_end, endDate);
        updateSelectedDate();
        mPresenter = ReceiveDiffPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectedDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
        mReq.put("startDate", CalendarUtils.toLocalDate(startDate));
        mReq.put("endDate", CalendarUtils.toLocalDate(endDate));
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAIL_INFO));
            mOptionsWindow = new ContextOptionsWindow(this);
            mOptionsWindow.setListener((adapter, view1, position) -> {
                mOptionsWindow.dismiss();
                mPresenter.export(null);
            });
            mOptionsWindow.refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.RIGHT);
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("日期", "收货单数", "收货金额(元)", "原发货金额(元)", "收货差异商品数", "收货差异量", "收货差异金额", "收货差异率");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[6] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[7] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[7]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return array;
    }

    @Override
    public void setData(ReceiveDiffResp resp, boolean append) {
        CharSequence[] array = {};
        mFooter.updateRowDate(resp.convertToRowData().toArray(array));
        List<ReceiveDiffBean> records = resp.getRecords();
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(records) && records.size() == 20);
        mExcel.setData(records, append);
    }

    @OnClick(R.id.rps_filter_btn)
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
    public BaseMapReq.Builder getReq() {
        return mReq;
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
