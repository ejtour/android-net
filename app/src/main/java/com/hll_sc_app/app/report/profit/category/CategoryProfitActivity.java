package com.hll_sc_app.app.report.profit.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.template.CategoryFilterWindow;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.profit.ProfitBean;
import com.hll_sc_app.bean.report.profit.ProfitResp;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Tuple;
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
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */
@Route(path = RouterConfig.REPORT_PROFIT_CATEGORY)
public class CategoryProfitActivity extends BaseLoadActivity implements ICategoryProfitContract.ICategoryProfitView {
    private static final int[] WIDTH_ARRAY = {150, 110, 110, 80, 80, 80};
    @BindView(R.id.trl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.trl_tab_one)
    TextView mCategory;
    @BindView(R.id.trl_tab_one_arrow)
    TriangleView mCategoryArrow;
    @BindView(R.id.trl_tab_two)
    TextView mDate;
    @BindView(R.id.trl_tab_two_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.tte_excel)
    ExcelLayout mExcel;
    private ICategoryProfitContract.ICategoryProfitPresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    private ExcelFooter mFooter;
    private CategoryResp mCategoryResp;
    private CategoryFilterWindow mCategoryWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_two_excel);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("type", "3");
        mReq.put("groupID", UserConfig.getGroupID());
        mReq.put("productCategoryLevel", "3");
        Date date = new Date();
        mDate.setTag(R.id.date_start, CalendarUtils.getDateBefore(date, 29));
        mDate.setTag(R.id.date_end, date);
        updateSelectedDate();
        mPresenter = CategoryProfitPresenter.newInstance();
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

    private void initView() {
        initExcel();
        mTitleBar.setHeaderTitle("品类毛利统计");
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mCategory.setText("分类");
    }

    private void initExcel() {
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

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("商品分类", "含税应收金额(元)", "不含税应收金额(元)", "采购成本(元)", "毛利额(元)", "毛利率");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL);
        for (int i = 1; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @OnClick({R.id.trl_tab_two_btn})
    public void showDateRangeWindow(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectedDate();
                mPresenter.loadList();
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
    public void setData(ProfitResp resp, boolean append) {
        CharSequence[] array = {};
        mFooter.updateRowDate(getFooterData(resp).toArray(array));
        List<ProfitBean> records = resp.getRecords();
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(records) && records.size() == 20);
        mExcel.setData(records, append);
    }

    private List<CharSequence> getFooterData(ProfitResp resp) {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add(CommonUtils.formatMoney(resp.getTotalReceiveAmount()));
        list.add(CommonUtils.formatMoney(resp.getTotalUntaxReceiveAmount()));
        list.add(CommonUtils.formatMoney(resp.getTotalOutAmount()));
        list.add(CommonUtils.formatMoney(resp.getTotalProfitAmount()));
        list.add(resp.getTotalProfitRate());
        return list;
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public void setCategory(CategoryResp resp) {
        mCategoryResp = resp;
    }

    @OnClick(R.id.trl_tab_one_btn)
    public void showPurchaserWindow(View view) {
        if (mCategoryResp == null) {
            mPresenter.queryCategory();
            return;
        }
        mCategoryArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mCategory.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mCategoryWindow == null) {
            mCategoryWindow = new CategoryFilterWindow(this, mCategoryResp);
            mCategoryWindow.setListener(beans -> {
                Tuple<List<String>, List<String>> tuple = mCategoryWindow.getCategoryThreeIds();
                mReq.put("productCategoryIDs",  TextUtils.join(",", tuple.getKey2()));
                mPresenter.loadList();
            });
            mCategoryWindow.setOnDismissListener(() -> {
                mCategoryArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mCategory.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mCategoryWindow.showAsDropDownFix(view);
    }
}
