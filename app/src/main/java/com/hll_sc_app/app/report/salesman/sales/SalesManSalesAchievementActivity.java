package com.hll_sc_app.app.report.salesman.sales;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.SalesManSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.enums.TimeFlagEnum;
import com.hll_sc_app.bean.enums.TimeTypeEnum;
import com.hll_sc_app.bean.event.SalesManSearchEvent;
import com.hll_sc_app.bean.report.salesman.SalesManAchievementReq;
import com.hll_sc_app.bean.report.salesman.SalesManSalesAchievement;
import com.hll_sc_app.bean.report.salesman.SalesManSalesResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SyncHorizontalScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 业务员签约绩效明细
 *
 * @author 初坤
 * @date 20190723
 */
@Route(path = RouterConfig.REPORT_SALESMAN_SALES_ACHIEVEMENT)
public class SalesManSalesAchievementActivity extends BaseLoadActivity implements BaseQuickAdapter.OnItemClickListener, SalesManSalesAchievementContract.ISalesManSalesAchievementView {
    String FORMAT_DATE = "yyyy/MM/dd";
    @BindView(R.id.txt_date_name_title)
    TextView textDate;
    @BindView(R.id.report_date_arrow)
    ImageView reportDateArrow;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_options)
    ImageView exportView;
    @BindView(R.id.txt_date_name)
    TextView dateTextView;
    @BindView(R.id.salesman_sale_content_detail)
    SyncHorizontalScrollView syncHorizontalScrollView;
    @BindView(R.id.salesman_sale_total_detail)
    SyncHorizontalScrollView footSyncHorizontalScrollView;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.total_txt)
    TextView totalTxt;
    @BindView(R.id.total_valid_order_num)
    TextView totalValidOrderNum;
    @BindView(R.id.total_trade_amount)
    TextView totalTradeAmount;
    @BindView(R.id.total_settle_order_num)
    TextView totalSettleOrderNum;
    @BindView(R.id.total_settle_amount)
    TextView totalSettleAmount;
    @BindView(R.id.total_refund_amount)
    TextView totalRefundAmount;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.report_date_customer_arrow)
    ImageView reportCustomerDateArrow;
    @BindView(R.id.date_customer)
    TextView dateCustomer;
    @BindView(R.id.date_customer_display)
    LinearLayout linearLayout;

    SalesManSalesAchievementPresenter mPresenter;
    SalesManSalesAchievementAdapter mAdapter;
    String startDate = "";
    String endDate = "";
    String localDate = "";
    int timeType = TimeTypeEnum.DAY.getCode();
    int timeFlag = TimeFlagEnum.TODAY.getCode();
    private ContextOptionsWindow mOptionsWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    private EmptyView mEmptyView;
    private SalesManAchievementReq params = new SalesManAchievementReq();
    boolean isClickCustomer = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_salesman_sales_achievement);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initDefaultTime();
        mPresenter = new SalesManSalesAchievementPresenter();
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreSalesManSalesAchievementList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.querySalesManSalesAchievementList(false);
            }
        });
        mAdapter = new SalesManSalesAchievementAdapter();
        View headView = LayoutInflater.from(this).inflate(R.layout.item_salesman_sales_title, recyclerView, false);
        mAdapter.addHeaderView(headView);
        recyclerView.setAdapter(mAdapter);
        mEmptyView = EmptyView.newBuilder(this).setTipsTitle("当前日期下没有数据").create();
        syncHorizontalScrollView.setLinkageViews(footSyncHorizontalScrollView);
        footSyncHorizontalScrollView.setLinkageViews(syncHorizontalScrollView);
        mPresenter.register(this);
        EventBus.getDefault().register(this);
        mPresenter.start();
    }

    /**
     * 初始化时间
     */
    private void initDefaultTime() {
        Date currentDate = new Date();
        String date = CalendarUtils.format(currentDate, CalendarUtils.FORMAT_LOCAL_DATE);
        date = CalendarUtils.getDateFormatString(date, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
        dateTextView.setText(String.format("%s", date));
        params.setStartDate(date);
        params.setEndDate(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        boolean isExport = false;
        String dateText = TimeFlagEnum.TODAY.getDesc();
        String dateCustomerText=OptionType.OPTION_REPORT_DATE_AGGREGATION;
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CURRENT_DATE)) {
            startDate = DateUtil.currentTimeHllDT8() + "";
            endDate = startDate;
            localDate = CalendarUtils.format(new Date(), FORMAT_DATE);
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_YES_DATE)) {
            startDate = CalendarUtils.format(CalendarUtils.getDateBefore(new Date(), 1),
                CalendarUtils.FORMAT_LOCAL_DATE);
            endDate = startDate;
            localDate = CalendarUtils.format(CalendarUtils.getDateBefore(new Date(), 1), FORMAT_DATE);
            timeFlag = TimeFlagEnum.YESTERDAY.getCode();
            dateText = TimeFlagEnum.YESTERDAY.getDesc();
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CURRENT_WEEK)) {
            startDate = DateUtil.getWeekFirstDay(0) + "";
            endDate = DateUtil.getWeekLastDay(0) + "";
            localDate = CalendarUtils.getDateFormatString(startDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.WEEK.getCode();
            timeFlag = TimeFlagEnum.CURRENTWEEK.getCode();
            dateText = TimeFlagEnum.CURRENTWEEK.getDesc();
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_WEEK)) {
            startDate = DateUtil.getWeekFirstDay(-1) + "";
            endDate = DateUtil.getWeekLastDay(-1) + "";
            localDate = CalendarUtils.getDateFormatString(startDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.WEEK.getCode();
            timeFlag = TimeFlagEnum.LASTWEEK.getCode();
            dateText = TimeFlagEnum.LASTWEEK.getDesc();
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CURRENT_MONTH)) {
            startDate = DateUtil.getMonthFirstDay(0) + "";
            endDate = DateUtil.getMonthLastDay(0) + "";
            localDate = CalendarUtils.getDateFormatString(startDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.MONTH.getCode();
            timeFlag = TimeFlagEnum.CURRENTMONTH.getCode();
            dateText = TimeFlagEnum.CURRENTMONTH.getDesc();
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_MONTH)) {
            startDate = DateUtil.getMonthFirstDay(-1) + "";
            endDate = DateUtil.getMonthLastDay(-1) + "";
            localDate = CalendarUtils.getDateFormatString(startDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.MONTH.getCode();
            timeFlag = TimeFlagEnum.LASTMONTH.getCode();
            dateText = TimeFlagEnum.CURRENTMONTH.getDesc();
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CUSTOMER_DEFINE) || isClickCustomer) {
            isClickCustomer = true;
            linearLayout.setVisibility(View.VISIBLE);
            timeFlag = TimeFlagEnum.CUSTOMDEFINE.getCode();
            timeType = TimeTypeEnum.DAY.getCode();
            startDate = DateUtil.currentTimeHllDT8() + "";
            endDate =startDate;
            localDate = CalendarUtils.format(new Date(), FORMAT_DATE);
            dateText = TimeFlagEnum.CUSTOMDEFINE.getDesc();
            dateCustomerText = OptionType.OPTION_REPORT_DATE_AGGREGATION;
            if(TextUtils.equals(optionsBean.getLabel(),OptionType.OPTION_REPORT_WEEK_AGGREGATION)){
                startDate = DateUtil.getWeekFirstDay(0) + "";
                endDate = DateUtil.getWeekLastDay(0) + "";
                localDate = CalendarUtils.getDateFormatString(startDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                        + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
                timeType = TimeTypeEnum.WEEK.getCode();
                dateCustomerText = OptionType.OPTION_REPORT_WEEK_AGGREGATION;
            }else if(TextUtils.equals(optionsBean.getLabel(),OptionType.OPTION_REPORT_MONTH_AGGREGATION)){
                startDate = DateUtil.getMonthFirstDay(0) + "";
                endDate = DateUtil.getWeekLastDay(0)+"";
                localDate = startDate.substring(0,4)+"年"+"-"+startDate.substring(4,6)+"月";
                timeType = TimeTypeEnum.MONTH.getCode();
                dateCustomerText = OptionType.OPTION_REPORT_MONTH_AGGREGATION;
            }else if(TextUtils.equals(optionsBean.getLabel(),OptionType.OPTION_REPORT_YEAR_AGGREGATION)){
                startDate = (DateUtil.currentTimeHllDT8()+"").substring(0,4)+"0101";
                localDate = startDate.substring(0,4)+"年";
                timeType = TimeTypeEnum.YEAR.getCode();
                dateCustomerText = OptionType.OPTION_REPORT_MONTH_AGGREGATION;
            }
        } else {
            //导出
            isExport = true;
        }
        if(!isClickCustomer){
            linearLayout.setVisibility(View.GONE);
        }
        params.setStartDate(startDate);
        params.setEndDate(endDate);
        params.setTimeType(timeType);
        params.setTimeFlag(timeFlag);
        if (!isExport) {
            dateTextView.setText(String.format("%s", localDate));
            textDate.setText(dateText);
            dateCustomer.setText(dateCustomerText);
            mPresenter.querySalesManSalesAchievementList(true);
        } else {
            export(null);
        }
        if (mOptionsWindow != null) {
            mOptionsWindow.dismiss();
        }
        if (mExportOptionsWindow != null) {
            mExportOptionsWindow.dismiss();
        }
    }

    @Subscribe
    public void onEvent(SalesManSearchEvent event) {
        String name = event.getSearchWord();
        if (!TextUtils.isEmpty(name)) {
            edtSearch.setText(name);
            params.setKeyWords(name);
            imgClear.setVisibility(View.VISIBLE);
        } else {
            params.setKeyWords("");
        }
        mPresenter.querySalesManSalesAchievementList(true);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showSalesManSalesAchievementList(List<SalesManSalesAchievement> records, boolean append, int total) {
        if (append) {
            mAdapter.addData(records);
        } else {
            mAdapter.setNewData(records);
        }
        mAdapter.setEmptyView(mEmptyView);
        if (null == records || records.size() == 0) {
            footSyncHorizontalScrollView.setVisibility(View.GONE);
        } else {
            footSyncHorizontalScrollView.setVisibility(View.VISIBLE);
        }
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() - 1 != total);

    }

    @Override
    public void showSalesManSalesTotalDatas(SalesManSalesResp salesManSalesResp) {
        txtTotal.setText("合计");
        totalTxt.setText(String.valueOf(salesManSalesResp.getTotalSize()));
        totalValidOrderNum.setText(String.valueOf(salesManSalesResp.getTotalValidBillNum()));
        totalTradeAmount.setText(CommonUtils.formatMoney(salesManSalesResp.getTotalSalesAmount()));
        totalSettleOrderNum.setText(String.valueOf(salesManSalesResp.getTotalSettleBillNum()));
        totalSettleAmount.setText(CommonUtils.formatMoney(salesManSalesResp.getTotalSettleAmount()));
        totalRefundAmount.setText(CommonUtils.formatMoney(salesManSalesResp.getTotalRefundAmount()));
    }


    @Override
    public SalesManAchievementReq getParams() {
        params.setTimeType(1);
        params.setTimeFlag(0);
        return params;
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
        Gson gson = new Gson();
        String reqParams = gson.toJson(params);
        Utils.bindEmail(this, (email) -> mPresenter.exportSalesManSalesAchievement(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(params);
        mPresenter.exportSalesManSalesAchievement(email, reqParams);
    }

    @OnClick({R.id.txt_date_name_title, R.id.img_back, R.id.txt_options, R.id.edt_search, R.id.img_clear,R.id.date_customer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_date_name_title:
                showOptionsWindow(textDate);
                break;
            case R.id.date_customer:
                showOptionsWindow(dateCustomer);
                break;
            case R.id.txt_options:
                showExportOptionsWindow(exportView);
            case R.id.edt_search:
                SearchActivity.start("", SalesManSearch.class.getSimpleName());
                break;
            case R.id.img_clear:
                params.setKeyWords("");
                edtSearch.setText("");
                mPresenter.querySalesManSalesAchievementList(true);
                imgClear.setVisibility(View.GONE);
            default:
                break;
        }
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        if(view.getId()==R.id.txt_date_name_title){
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CURRENT_DATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_YES_DATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CURRENT_WEEK));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_WEEK));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CURRENT_MONTH));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_MONTH));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CUSTOMER_DEFINE));
            mOptionsWindow.setOnDismissListener(()->{
                reportDateArrow.setRotation(0);
            });
            reportDateArrow.setRotation(180);
        }else {
            list.add(new OptionsBean(R.drawable.ic_report_date_customer, OptionType.OPTION_REPORT_DATE_AGGREGATION));
            list.add(new OptionsBean(R.drawable.ic_report_date_customer, OptionType.OPTION_REPORT_WEEK_AGGREGATION));
            list.add(new OptionsBean(R.drawable.ic_report_date_customer, OptionType.OPTION_REPORT_MONTH_AGGREGATION));
            list.add(new OptionsBean(R.drawable.ic_report_date_customer, OptionType.OPTION_REPORT_YEAR_AGGREGATION));
            mOptionsWindow.setOnDismissListener(()->{
                reportCustomerDateArrow.setRotation(0);
            });
            reportCustomerDateArrow.setRotation(180);
        }
        mOptionsWindow.refreshList(list);
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }

    private void showExportOptionsWindow(View v) {
        if (mExportOptionsWindow == null) {
            mExportOptionsWindow = new ContextOptionsWindow(this)
                .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option,
                    OptionType.OPTION_EXPORT_DETAIL_INFO)))
                .setListener((adapter, view, position) -> {
                    mExportOptionsWindow.dismiss();
                    export(null);
                });
        }
        mExportOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    class SalesManSalesAchievementAdapter extends BaseQuickAdapter<SalesManSalesAchievement, BaseViewHolder> {

        SalesManSalesAchievementAdapter() {
            super(R.layout.item_report_customer_sales_achievement);
        }

        @Override
        protected void convert(BaseViewHolder helper, SalesManSalesAchievement bean) {
            helper.setText(R.id.txt_saleman_code, bean.getSalesmanCode())
                    .setText(R.id.txt_saleman_name, bean.getSalesmanName())
                    .setText(R.id.txt_valid_order_num, String.valueOf(bean.getValidOrderNum()))
                    .setText(R.id.txt_trade_amount, CommonUtils.formatNumber(bean.getSalesAmount()))
                    .setText(R.id.txt_settle_order_num, String.valueOf(bean.getSettleBillNum()))
                    .setText(R.id.txt_settle_amount, CommonUtils.formatNumber(bean.getSettleAmount()))
                    .setText(R.id.txt_refund_amount, CommonUtils.formatNumber(bean.getRefundAmount()))
                    .itemView.setBackgroundResource(helper.getLayoutPosition() % 2 == 0 ?
                    R.drawable.bg_price_log_content_white : R.drawable.bg_price_log_content_gray);
        }
    }
}
