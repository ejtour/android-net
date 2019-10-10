package com.hll_sc_app.app.report.salesReport;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.SalesDayReportSearch;
import com.hll_sc_app.app.search.stratery.SalesManSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateWeekWindow;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.DateYearMonthWindow;
import com.hll_sc_app.base.widget.DateYearWindow;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.enums.TimeFlagEnum;
import com.hll_sc_app.bean.enums.TimeTypeEnum;
import com.hll_sc_app.bean.event.SalesDayReportSearchEvent;
import com.hll_sc_app.bean.event.SalesManSearchEvent;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmount;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.bean.report.salesReport.SalesReportDetail;
import com.hll_sc_app.bean.report.salesReport.SalesReportRecord;
import com.hll_sc_app.bean.report.salesReport.SalesReportReq;
import com.hll_sc_app.bean.report.salesReport.SalesReportResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 销售日报
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_SALES_DAY_REPORT)
public class SalesReportActivity extends BaseLoadActivity implements SalesReportContract.ISalesReportView,BaseQuickAdapter.OnItemClickListener {
    private static final String FORMAT_DATE = "MM/dd";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_date)
    TextView mTxtDateName;
    @BindView(R.id.txt_options)
    ImageView txtOptions;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.edt_search)
    TextView searchEditText;
    @BindView(R.id.img_clear)
    ImageView imgClearView;
    DateWeekWindow weekWindow;

    SalesReportReq salesReportReq = new SalesReportReq();

    private SalesReportListAdapter mAdapter;
    private ContextOptionsWindow mExportOptionsWindow;
    private SalesReportPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales_day_report);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initDefaultTime();
        mPresenter = SalesReportPresenter.newInstance();
        mAdapter = new SalesReportListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreSalesReportList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.querySalesReportList(false);
            }
        });
        mPresenter.register(this);
        EventBus.getDefault().register(this);
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initDefaultTime() {
        Date currentDate = new Date();
        setDate(currentDate,0);
    }

    @OnClick({R.id.img_back,R.id.txt_options,R.id.txt_date_pre_week,R.id.txt_date_next_week,R.id.txt_date,R.id.edt_search,R.id.img_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_options:
                showExportOptionsWindow(txtOptions);
                break;
            case R.id.txt_date_pre_week:
                String date = salesReportReq.getDate();
                Date tempDate = CalendarUtils.parseLocal(date, CalendarUtils.FORMAT_LOCAL_DATE);
                setDate(tempDate,-1);
                mPresenter.querySalesReportList(true);
                break;
            case R.id.txt_date_next_week:
                String nextDate = salesReportReq.getDate();
                Date nextTempDate = CalendarUtils.parseLocal(nextDate, CalendarUtils.FORMAT_LOCAL_DATE);
                setDate(nextTempDate,1);
                mPresenter.querySalesReportList(true);
                break;
            case R.id.txt_date:
                showCustomerDate(mTxtDateName);
                break;
            case R.id.edt_search:
                SearchActivity.start("", SalesDayReportSearch.class.getSimpleName());
                break;
            case R.id.img_clear:
                searchEditText.setText("");
                salesReportReq.setKeyword("");
                mPresenter.querySalesReportList(true);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEvent(SalesDayReportSearchEvent event) {
        String name = event.getSearchWord();
        if (!TextUtils.isEmpty(name)) {
            searchEditText.setText(name);
            salesReportReq.setKeyword(name);
            imgClearView.setVisibility(View.VISIBLE);
        } else {
            salesReportReq.setKeyword("");
        }
        mPresenter.querySalesReportList(true);
    }

    //点击自定义事件
    private void showCustomerDate(TextView dateText){
        //周的
        weekWindow = weekWindow==null? new DateWeekWindow(this):weekWindow;
        weekWindow.setCalendar(new Date());
        weekWindow.setSelectListener(date->{
            String firstDate =  CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
            firstDate = DateUtil.getWeekFirstDay(0,Long.valueOf(firstDate))+"";
            String endDate = DateUtil.getWeekLastDay(0,Long.valueOf(firstDate))+"";
            String displayLocalDate = CalendarUtils.getDateFormatString(firstDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                    + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            setDateSelect(dateText,displayLocalDate,firstDate);
        });
        weekWindow.showAtLocation(getCurrentFocus(),Gravity.BOTTOM,0,0);
    }

    /**
     * 设置自定义的时间参数
     * @param dateText
     */
    private void setDateSelect(TextView dateText,String displayLocalDate,String firstDate){
        dateText.setText(displayLocalDate);
        salesReportReq.setDate(firstDate);
        mPresenter.querySalesReportList(true);
    }

    private void showExportOptionsWindow(View view) {
        if (mExportOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_REPORT_DETAIL));
            mExportOptionsWindow = new ContextOptionsWindow(this).setListener(this).refreshList(list);
        }
        mExportOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }

    @Override
    public void showSalesReportList(SalesReportResp salesReportResp, boolean append, int total) {
        if (append) {
            if (!CommonUtils.isEmpty(salesReportResp.getRecords()))
                mAdapter.addData(salesReportResp.getRecords());
        } else {
            mAdapter.setNewData(salesReportResp.getRecords());
        }
    }

    @Override
    public SalesReportReq getRequestParams() {
        //app端只有周的数据
        salesReportReq.setTimeType(2);
        return salesReportReq;
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
        Utils.bindEmail(this, email -> mPresenter.exportSalesReport(email));
    }

    @Override
    public void export(String email) {
        mPresenter.exportSalesReport(email);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_DETAIL)){
            export(null);
        }

    }



    private void setDate(Date currentDate,int week){
        Long startDate = DateUtil.getWeekFirstDay(week, Long.valueOf(CalendarUtils.format(currentDate, CalendarUtils.FORMAT_LOCAL_DATE)));
        Long endDate   =  DateUtil.getWeekLastDay(week, Long.valueOf(CalendarUtils.format(currentDate, CalendarUtils.FORMAT_LOCAL_DATE)));
        String startStr = CalendarUtils.getDateFormatString(startDate+"", CalendarUtils.FORMAT_LOCAL_DATE,FORMAT_DATE);
        String endStr = CalendarUtils.getDateFormatString(endDate+"", CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
        mTxtDateName.setText(String.format("%s - %s", startStr, endStr));
        salesReportReq.setDate(startDate+"");
    }

    class SalesReportListAdapter extends BaseQuickAdapter<SalesReportRecord, BaseViewHolder> {

        SalesReportListAdapter() {
            super(R.layout.item_report_sales_day_report);
        }

        @Override
        protected void convert(BaseViewHolder helper, SalesReportRecord bean) {
            helper.setText(R.id.txt_day_report_salesman,bean.getSalesmanName());
            helper.setText(R.id.txt_day_report_reportnum,String.format("共 %d 条",bean.getReportNum()));
            List<SalesReportDetail> reportDetail = bean.getReportDetail();
            helper.setText(R.id.day_report_sunday,reportDetail.get(reportDetail.size()-1).getReportNum()+"");
            helper.setText(R.id.day_report_monday,reportDetail.get(1).getReportNum()+"");
            helper.setText(R.id.day_report_tuesday,reportDetail.get(2).getReportNum()+"");
            helper.setText(R.id.day_report_wedday,reportDetail.get(3).getReportNum()+"");
            helper.setText(R.id.day_report_thurday,reportDetail.get(4).getReportNum()+"");
            helper.setText(R.id.day_report_friday,reportDetail.get(5).getReportNum()+"");
            helper.setText(R.id.day_report_satday,reportDetail.get(6).getReportNum()+"");
        }
    }
}
