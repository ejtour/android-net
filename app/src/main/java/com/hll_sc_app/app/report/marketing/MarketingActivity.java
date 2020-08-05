package com.hll_sc_app.app.report.marketing;

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
import com.hll_sc_app.app.search.stratery.SimpleSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.marketing.MarketingBean;
import com.hll_sc_app.bean.report.marketing.MarketingResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SingleSelectionWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.report.ExcelFooter;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/27
 */
@Route(path = RouterConfig.REPORT_MARKETING)
public class MarketingActivity extends BaseLoadActivity implements IMarketingContract.IMarketingView {
    private static final int[] WIDTH_ARRAY = {40, 90, 170, 120, 120, 120, 120, 120, 120, 120};
    private static final int[] FOOTER_WIDTH_ARRAY = {131, 170, 120, 120, 120, 120, 120, 120, 120};

    @BindView(R.id.trl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.trl_tab_one)
    TextView mDate;
    @BindView(R.id.trl_tab_one_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.trl_tab_two)
    TextView mType;
    @BindView(R.id.trl_tab_two_arrow)
    TriangleView mTypeArrow;
    @BindView(R.id.arm_search_view)
    SearchView mSearchView;
    @BindView(R.id.arm_excel_layout)
    ExcelLayout mExcel;
    private ExcelFooter mFooter;
    private IMarketingContract.IMarketingPresenter mPresenter;
    private AtomicInteger mIndex = new AtomicInteger();
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private DateRangeWindow mDateRangeWindow;
    private SingleSelectionWindow<NameValue> mTypeWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_marketing);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        Date endDate = new Date();
        mDate.setTag(R.id.date_start, CalendarUtils.getFirstDateInMonth(endDate));
        mDate.setTag(R.id.date_end, endDate);
        updateSelectDate();
        mPresenter = MarketingPresenter.newInstance();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("营销活动统计表");
        mTitleBar.setRightBtnVisible(false);
        mDate.setText("日期筛选");
        mType.setText("活动类型");
        mSearchView.setHint("可以根据活动名称关键字进行搜索");
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(MarketingActivity.this, searchContent, SimpleSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mReq.put("discountName", searchContent);
                mPresenter.start();
            }
        });
        mFooter = new ExcelFooter(this);
        mFooter.updateChildView(FOOTER_WIDTH_ARRAY.length);
        mFooter.updateItemData(generateFooterColumnData());
        mExcel.setFooterView(mFooter);
        mExcel.setHeaderView(generateHeader());
        mExcel.setColumnDataList(generateColumnData());
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
        row.updateRowDate("序号", "活动类型", "活动名称", "参与活动商品", "参与门店", "拉动单数", "流水金额", "实际金额", "优惠金额", "优惠占比");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]));
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL);
        for (int i = 3; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    private ExcelRow.ColumnData[] generateFooterColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[FOOTER_WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(FOOTER_WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(FOOTER_WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL);
        for (int i = 2; i < FOOTER_WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(FOOTER_WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    public void setData(MarketingResp resp, boolean append) {
        mFooter.updateRowDate(resp.convertToRowData().toArray(new CharSequence[]{}));
        if (!append) {
            mIndex.set(0);
        }
        List<MarketingBean> list = resp.getList();
        if (!CommonUtils.isEmpty(list)) {
            for (MarketingBean bean : list) {
                LinkedHashMap<String, String> data = mReq.create().getData();
                bean.setStartDate(data.get("startDate"));
                bean.setEndDate(data.get("endDate"));
                bean.setSequenceNo(mIndex.incrementAndGet());
            }
        }
        mExcel.setEnableLoadMore(list != null && list.size() == 20);
        mExcel.setData(list, append);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @OnClick(R.id.trl_tab_one_btn)
    public void showDateRangeWindow(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectDate();
                mPresenter.start();
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange((Date) mDate.getTag(R.id.date_start), (Date) mDate.getTag(R.id.date_end));
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }

    @OnClick(R.id.trl_tab_two_btn)
    public void showTypeWindow(View view) {
        mTypeArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mType.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mTypeWindow == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("全部活动", ""));
            list.add(new NameValue("订单活动", "1"));
            list.add(new NameValue("商品活动", "2"));
            mTypeWindow = new SingleSelectionWindow<>(this, NameValue::getName);
            mTypeWindow.refreshList(list);
            mTypeWindow.setSelectListener(nameValue -> {
                mType.setText(nameValue.getName());
                mReq.put("discountType", nameValue.getValue());
                mPresenter.start();
            });
            mTypeWindow.setOnDismissListener(() -> {
                mTypeArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mType.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mTypeWindow.showAsDropDownFix(view);
    }
}
