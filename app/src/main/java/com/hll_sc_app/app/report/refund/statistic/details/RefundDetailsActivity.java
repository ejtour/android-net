package com.hll_sc_app.app.report.refund.statistic.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
import com.hll_sc_app.bean.report.refund.RefundDetailsBean;
import com.hll_sc_app.bean.report.refund.RefundDetailsResp;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退货明细统计
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_REFUND_STATISTIC_DETAILS)
public class RefundDetailsActivity extends BaseLoadActivity implements IRefundDetailsContract.IRefundDetailsView {
    private static final int[] WIDTH_ARRAY = {90, 100, 100, 100, 60};
    @BindView(R.id.rsd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.rsd_type)
    TextView mType;
    @BindView(R.id.rsd_date)
    TextView mDate;
    @BindView(R.id.rsd_type_arrow)
    TriangleView mTypeArrow;
    @BindView(R.id.rsd_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.rsd_title_bar)
    TitleBar mTitleBar;
    private IRefundDetailsContract.IRefundDetailsPresenter mPresenter;
    private ContextOptionsWindow mExportOptionsWindow;
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mOptionsWindow;
    private ExcelFooter mFooter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_refund_statistic_details);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        mReq.put("sign", "1");
        Date currentDate = new Date();
        Date firstDate = CalendarUtils.getFirstDateInMonth(currentDate);
        mDate.setTag(R.id.date_start, firstDate);
        mDate.setTag(R.id.date_end, currentDate);
        updateSelectDate();
        mPresenter = RefundDetailsPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        mReq.put("startDate", CalendarUtils.toLocalDate(startDate));
        mReq.put("endDate", CalendarUtils.toLocalDate(endDate));
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mFooter = new ExcelFooter(this);
        mFooter.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] dataArray = generateColumnData();
        mFooter.updateItemData(dataArray);
        mExcel.setTips("按退货申请日期统计自营业务，每小时更新一次");
        mExcel.setColumnDataList(dataArray);
        mExcel.setFooterView(mFooter);
        mExcel.setHeaderView(generateHeader());
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

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
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

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    private void showExportOptionsWindow(View v) {
        if (mExportOptionsWindow == null) {
            mExportOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option,
                            OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mExportOptionsWindow.dismiss();
                        mPresenter.export(null);
                    });
        }
        mExportOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    @OnClick(R.id.rsd_type_btn)
    public void selectType(View view) {
        mTypeArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mType.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this);
            List<OptionsBean> optionsBeans = new ArrayList<>();
            optionsBeans.add(new OptionsBean(R.drawable.ic_menu_all, OptionType.OPTION_ALL));
            optionsBeans.add(new OptionsBean(R.drawable.ic_menu_deposit, OptionType.OPTION_NOT_CONTAIN_DEPOSIT));
            mOptionsWindow.refreshList(optionsBeans);
            mOptionsWindow.setListener((adapter, view1, position) -> {
                OptionsBean item = (OptionsBean) adapter.getItem(position);
                if (item == null) return;
                mOptionsWindow.dismiss();
                mReq.put("sign", TextUtils.equals(item.getLabel(), OptionType.OPTION_ALL) ? "1" : "2");
                mPresenter.start();
                mType.setText(item.getLabel());
            });
            mOptionsWindow.setOnDismissListener(() -> {
                mTypeArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_666666));
                mType.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.START);
    }

    @OnClick(R.id.rsd_date_btn)
    public void selectDate(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_end, end);
                mDate.setTag(R.id.date_start, start);
                updateSelectDate();
                mPresenter.start();
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange((Date) mDate.getTag(R.id.date_start), (Date) mDate.getTag(R.id.date_end));
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("日期", "退单数", "退货客户数", "退货商品数", "退款金额");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        for (int i = 1; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    public void setData(RefundDetailsResp resp, boolean append) {
        CharSequence[] array = {};
        mFooter.updateRowDate(resp.convertToRowData().toArray(array));
        List<RefundDetailsBean> records = resp.getGroupVoList();
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(records) && records.size() == 20);
        mExcel.setData(records, append);
    }
}
