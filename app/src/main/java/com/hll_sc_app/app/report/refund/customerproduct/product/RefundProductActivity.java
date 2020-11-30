package com.hll_sc_app.app.report.refund.customerproduct.product;

import android.content.Intent;
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
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ProductNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.refund.RefundProductBean;
import com.hll_sc_app.bean.report.refund.RefundProductResp;
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
 * @since 2019/12/13
 */
@Route(path = RouterConfig.REPORT_REFUNDED_PRODUCT_DETAIL)
public class RefundProductActivity extends BaseLoadActivity implements IRefundProductContract.IRefundProductView {
    private static final int[] WIDTH_ARRAY = {120, 140, 100, 80, 80, 80};
    @BindView(R.id.rrc_search_view)
    SearchView mSearchView;
    @BindView(R.id.rrc_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rrc_type)
    TextView mType;
    @BindView(R.id.rrc_type_arrow)
    TriangleView mTypeArrow;
    @BindView(R.id.rrc_date)
    TextView mDate;
    @BindView(R.id.rrc_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.rrc_excel)
    ExcelLayout mExcel;
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mOptionsWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    private ExcelFooter mFooter;
    private IRefundProductContract.IRefundProductPresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_refund_customer);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Date end = new Date();
        mDate.setTag(R.id.date_start, CalendarUtils.getFirstDateInMonth(end));
        mDate.setTag(R.id.date_end, end);
        mReq.put("groupID", UserConfig.getGroupID());
        mReq.put("sign", "1");
        updateSelectDate();
        mPresenter = RefundProductPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
        mReq.put("startDate", CalendarUtils.toLocalDate(startDate));
        mReq.put("endDate", CalendarUtils.toLocalDate(endDate));
    }

    private void initView() {
        initExcel();
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mSearchView.setSearchTextLeft();
        mSearchView.setTextColorWhite();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(RefundProductActivity.this,
                        "", ProductNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mReq.put("productName", searchContent);
                mPresenter.start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    private void initExcel() {
        mExcel.setTips("按退货申请日期统计自营业务，每小时更新一次");
        mFooter = new ExcelFooter(this);
        mFooter.updateChildView(WIDTH_ARRAY.length);
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

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("商品编码", "商品名称", "商品规格", "单位", "数量", "退货金额");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < 3; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]));
        for (int i = 4; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
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

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick(R.id.rrc_type_btn)
    public void selectType(View view) {
        mTypeArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mType.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_menu_all, OptionType.OPTION_ALL));
            list.add(new OptionsBean(R.drawable.ic_menu_deposit, OptionType.OPTION_NOT_DEPOSIT));
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(list)
                    .setListener((adapter, view1, position) -> {
                        OptionsBean item = (OptionsBean) adapter.getItem(position);
                        if (item == null) return;
                        mOptionsWindow.dismiss();
                        mType.setText(item.getLabel());
                        mReq.put("sign", TextUtils.equals(item.getLabel(), OptionType.OPTION_ALL) ? "1" : "2");
                        mPresenter.start();
                    });
            mOptionsWindow.setOnDismissListener(() -> {
                mTypeArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_666666));
                mType.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.START);
    }

    @OnClick(R.id.rrc_date_btn)
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

    @Override
    public void setData(RefundProductResp resp, boolean append) {
        CharSequence[] array = {};
        mFooter.updateRowDate(resp.convertToRowData().toArray(array));
        List<RefundProductBean> records = resp.getRecords();
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(records) && records.size() == 20);
        mExcel.setData(records, append);
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
