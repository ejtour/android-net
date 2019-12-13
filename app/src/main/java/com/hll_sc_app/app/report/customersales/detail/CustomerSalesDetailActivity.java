package com.hll_sc_app.app.report.customersales.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.search.CustomerSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesBean;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.DateFilterView;
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

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/11
 */
@Route(path = RouterConfig.CUSTOMER_SALE_AGGREGATION_DETAIL)
public class CustomerSalesDetailActivity extends BaseLoadActivity implements DateFilterView.DateFilterCallback, ICustomerSalesDetailContract.ICustomerSalesDetailView {
    private static final int[] WIDTH_ARRAY = {120, 80, 80, 80, 100, 100, 80, 100, 100};
    @BindView(R.id.csd_search_view)
    SearchView mSearchView;
    @BindView(R.id.csd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.csd_date_filter)
    DateFilterView mDateFilter;
    @BindView(R.id.csd_excel)
    ExcelLayout mExcel;
    @Autowired(name = "object0")
    int mActionType;
    private ContextOptionsWindow mOptionsWindow;
    private ExcelFooter mFooter;
    private ICustomerSalesDetailContract.ICustomerSalesDetailPresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

    /**
     * @param actionType 0-客户销售 1-客户销售门店
     */
    public static void start(int actionType) {
        RouterUtil.goToActivity(RouterConfig.CUSTOMER_SALE_AGGREGATION_DETAIL, actionType);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_customer_sales_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                PurchaserBean bean = data.getParcelableExtra("parcelable");
                mReq.put("purchaserID", bean.getPurchaserID());
                mSearchView.showSearchContent(true, bean.getPurchaserName());
            }
        }
    }

    private void initData() {
        mReq.put("actionType", String.valueOf(mActionType));
        mReq.put("date", CalendarUtils.toLocalDate(new Date()));
        mReq.put("groupID", UserConfig.getGroupID());
        mReq.put("timeFlag", "0");
        mReq.put("order", "1");
        mReq.put("sortBy", "1");
        mPresenter = CustomerSalesDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSearchView.setTextColorWhite();
        mSearchView.setSearchTextLeft();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                CustomerSearchActivity.start(CustomerSalesDetailActivity.this, searchContent);
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mReq.put("purchaserID", "");
                }
                mPresenter.start();
            }
        });
        mDateFilter.setDateFilterCallback(this);
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        initExcel();
    }

    private void initExcel() {
        WIDTH_ARRAY[1] = mActionType == 0 ? 80 : 120;
        mExcel.setData(null, false);
        mFooter = new ExcelFooter(this);
        mFooter.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] dataArray = generateColumnData();
        mFooter.updateItemData(dataArray);
        mExcel.setColumnDataList(dataArray);
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

    private void showExportOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAIL_INFO));
            mOptionsWindow = new ContextOptionsWindow(this).setListener((adapter, view1, position) -> {
                mOptionsWindow.dismiss();
                mPresenter.export(null);
            }).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.RIGHT);
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("采购商名称", mActionType == 0 ? "合作门店数" : "门店名称", "订单数", "有效订单数", "交易金额(元)", "单均(元)", "退单数", "退货金额(元)", "小计金额(元)");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }


    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL | (mActionType == 0 ? Gravity.RIGHT : Gravity.LEFT));
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[6] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[7] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[7]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[8] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[8]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return array;
    }

    @Override
    public void onTimeTypeChanged(int type) {
        mReq.put("timeType", type == 0 ? "" : String.valueOf(type));
    }

    @Override
    public void onTimeFlagChanged(int flag) {
        mReq.put("timeFlag", String.valueOf(flag));
    }

    @Override
    public void onDateChanged(String date) {
        mReq.put("date", date);
        mPresenter.start();
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(CustomerSalesResp resp, boolean append) {
        CharSequence[] array = {};
        mFooter.updateRowDate(resp.convertToRowData().toArray(array));
        List<CustomerSalesBean> records = resp.getRecords();
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
