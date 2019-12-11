package com.hll_sc_app.app.report.salesman.sign;

import android.content.Intent;
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
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateWeekWindow;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.DateYearMonthWindow;
import com.hll_sc_app.base.widget.DateYearWindow;
import com.hll_sc_app.bean.enums.TimeFlagEnum;
import com.hll_sc_app.bean.enums.TimeTypeEnum;
import com.hll_sc_app.bean.report.salesman.SalesManAchievementReq;
import com.hll_sc_app.bean.report.salesman.SalesManSignAchievement;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SyncHorizontalScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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
 * 业务员签约绩效明细
 *
 * @author 初坤
 * @date 20190723
 */
@Route(path = RouterConfig.REPORT_SALESMAN_SIGN_ACHIEVEMENT)
public class SalesManSignAchievementActivity extends BaseLoadActivity implements BaseQuickAdapter.OnItemClickListener
    , SalesManSignAchievementContract.ISalesManSignAchievementView {
    private static final int SALES_MAN_SIGN_CODE = 20001;
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
    @BindView(R.id.salesman_sign_content_detail)
    SyncHorizontalScrollView syncHorizontalScrollView;
    @BindView(R.id.salesman_sign_total_detail)
    SyncHorizontalScrollView footSyncHorizontalScrollView;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.total_txt)
    TextView totalTxt;
    @BindView(R.id.total_intent_customer_num)
    TextView totalIntentCustomerNum;
    @BindView(R.id.total_sign_customer_num)
    TextView totalSignCustomerNum;
    @BindView(R.id.total_sign_shop_num)
    TextView totalSignShopNum;
    @BindView(R.id.total_incr_intent_customer)
    TextView totalIncrIntentCustomer;
    @BindView(R.id.total_incr_sign_customer)
    TextView totalIncrSignCustomer;
    @BindView(R.id.total_incr_sign_shop)
    TextView totalIncrSignShop;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.report_time_arrow)
    ImageView dateArrow;
    @BindView(R.id.report_date_customer_arrow)
    ImageView reportCustomerDateArrow;
    @BindView(R.id.date_customer)
    TextView dateCustomer;
    @BindView(R.id.date_customer_display)
    LinearLayout linearLayout;
    SalesManSignAchievementPresenter mPresenter;
    SalesManSignAchievementAdapter mAdapter;
    String serverDate = "";
    String localDate = "";
    int timeType = TimeTypeEnum.DAY.getCode();
    int timeFlag = TimeFlagEnum.TODAY.getCode();
    private ContextOptionsWindow mOptionsWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    private EmptyView mEmptyView;
    private SalesManAchievementReq params = new SalesManAchievementReq();
    boolean isClickCustomer = false;

    //0 - 日统计  1 - 周统计 2 - 月统计 3 - 年统计
    int isClickCustomerDateAggregation = 0;

    DateYearMonthWindow dateYearMonthWindow;
    DateWindow dateWindow;
    DateWeekWindow weekWindow;
    DateYearWindow dateYearWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_salesman_sign_achievement);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initDefaultTime();
        mPresenter = new SalesManSignAchievementPresenter();
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreSalesManSignAchievementList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.querySalesManSignAchievementList(false);
            }
        });
        mAdapter = new SalesManSignAchievementAdapter();
        View headView = LayoutInflater.from(this).inflate(R.layout.item_salesman_sign_title, recyclerView, false);
        mAdapter.addHeaderView(headView);
        recyclerView.setAdapter(mAdapter);
        mEmptyView = EmptyView.newBuilder(this).setTipsTitle("当前日期下没有数据").create();
        syncHorizontalScrollView.setLinkageViews(footSyncHorizontalScrollView);
        footSyncHorizontalScrollView.setLinkageViews(syncHorizontalScrollView);
        mPresenter.register(this);
        mPresenter.start();
    }


    /**
     * 初始化时间
     */
    private void initDefaultTime() {
        Date currentDate = new Date();
        String dateStr = CalendarUtils.format(currentDate, CalendarUtils.FORMAT_LOCAL_DATE);
        String date = CalendarUtils.getDateFormatString(dateStr, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
        dateTextView.setText(String.format("%s", date));
        params.setDate(dateStr);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        boolean isExport = false;
        // -1 代表没点击 日 周 月 年 汇总
        isClickCustomerDateAggregation = -1;
        String dateText = TimeFlagEnum.TODAY.getDesc();
        String dateCustomerText=OptionType.OPTION_STATISTIC_DATE;
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_CURRENT_DATE)) {
            serverDate = DateUtil.currentTimeHllDT8() + "";
            localDate = CalendarUtils.format(new Date(), FORMAT_DATE);
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_YES_DATE)) {
            serverDate = CalendarUtils.format(CalendarUtils.getDateBefore(new Date(), 1),
                CalendarUtils.FORMAT_LOCAL_DATE);
            localDate = CalendarUtils.format(CalendarUtils.getDateBefore(new Date(), 1), FORMAT_DATE);
            timeFlag = TimeFlagEnum.YESTERDAY.getCode();
            dateText = TimeFlagEnum.YESTERDAY.getDesc();
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_CURRENT_WEEK)) {
            serverDate = DateUtil.getWeekFirstDay(0) + "";
            String endDate = DateUtil.getWeekLastDay(0) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.WEEK.getCode();
            timeFlag = TimeFlagEnum.CURRENTWEEK.getCode();
            dateText = TimeFlagEnum.CURRENTWEEK.getDesc();
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_WEEK)) {
            serverDate = DateUtil.getWeekFirstDay(-1) + "";
            String endDate = DateUtil.getWeekLastDay(-1) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.WEEK.getCode();
            timeFlag = TimeFlagEnum.LASTWEEK.getCode();
            dateText = TimeFlagEnum.LASTWEEK.getDesc();
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_CURRENT_MONTH)) {
            serverDate = DateUtil.getMonthFirstDay(0) + "";
            String endDate = DateUtil.getMonthLastDay(0) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.MONTH.getCode();
            timeFlag = TimeFlagEnum.CURRENTMONTH.getCode();
            dateText = TimeFlagEnum.CURRENTMONTH.getDesc();
            isClickCustomer = false;
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_MONTH)) {
            serverDate = DateUtil.getMonthFirstDay(-1) + "";
            String endDate = DateUtil.getMonthLastDay(-1) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
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
            serverDate = DateUtil.currentTimeHllDT8() + "";
            localDate = CalendarUtils.format(new Date(), FORMAT_DATE);
            dateText = TimeFlagEnum.CUSTOMDEFINE.getDesc();
            dateCustomerText = OptionType.OPTION_STATISTIC_DATE;
            isClickCustomerDateAggregation = 0;
            if(TextUtils.equals(optionsBean.getLabel(),OptionType.OPTION_STATISTIC_WEEK)){
                serverDate = DateUtil.getWeekFirstDay(0) + "";
                String endDate = DateUtil.getWeekLastDay(0) + "";
                localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                        + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
                timeType = TimeTypeEnum.WEEK.getCode();
                dateCustomerText = OptionType.OPTION_STATISTIC_WEEK;
                isClickCustomerDateAggregation = 1;
            }else if(TextUtils.equals(optionsBean.getLabel(),OptionType.OPTION_STATISTIC_MONTH)){
                serverDate = DateUtil.getMonthFirstDay(0) + "";
                localDate = serverDate.substring(0,4)+"年"+"-"+serverDate.substring(4,6)+"月";
                timeType = TimeTypeEnum.MONTH.getCode();
                dateCustomerText = OptionType.OPTION_STATISTIC_MONTH;
                isClickCustomerDateAggregation = 2;
            }else if(TextUtils.equals(optionsBean.getLabel(),OptionType.OPTION_STATISTIC_YEAR)){
                serverDate = (DateUtil.currentTimeHllDT8()+"").substring(0,4)+"0101";
                localDate = serverDate.substring(0,4)+"年";
                timeType = TimeTypeEnum.YEAR.getCode();
                dateCustomerText = OptionType.OPTION_STATISTIC_YEAR;
                isClickCustomerDateAggregation = 3;
            }
        } else {
            //导出
            isExport = true;
        }
        if(!isClickCustomer){
            linearLayout.setVisibility(View.GONE);
        }
        params.setDate(serverDate);
        params.setTimeType(timeType);
        params.setTimeFlag(timeFlag);
        if (!isExport) {
            dateTextView.setText(String.format("%s", localDate));
            textDate.setText(dateText);
            dateCustomer.setText(dateCustomerText);
            mPresenter.querySalesManSignAchievementList(true);
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

    @Override
    public void showSalesManSignAchievementList(List<SalesManSignAchievement> records, boolean append, int total) {
        if (append) {
            if (!CommonUtils.isEmpty(records))
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
    public void showSalesManSignTotalDatas(SalesManSignResp salesManSignResp) {
        txtTotal.setText("合计");
        totalTxt.setText(String.valueOf(salesManSignResp.getTotalSize()));
        totalIntentCustomerNum.setText(String.valueOf(salesManSignResp.getTotalIntentCustomerNum()));
        totalSignCustomerNum.setText(String.valueOf(salesManSignResp.getTotalSignCustomerNum()));
        totalSignShopNum.setText(String.valueOf(salesManSignResp.getTotalSignShopNum()));
        totalIncrIntentCustomer.setText(String.valueOf(salesManSignResp.getTotalAddIntentCustomerNum()));
        totalIncrSignCustomer.setText(String.valueOf(salesManSignResp.getTotalAddSignCustomerNum()));
        totalIncrSignShop.setText(String.valueOf(salesManSignResp.getTotalAddSignShopNum()));
    }

    @Override
    public SalesManAchievementReq getParams() {
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
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void bindEmail() {
        Gson gson = new Gson();
        String reqParams = gson.toJson(params);
        Utils.bindEmail(this, (email) -> mPresenter.exportSalesManSignAchievement(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(params);
        mPresenter.exportSalesManSignAchievement(email, reqParams);
    }

    @OnClick({R.id.txt_date_name_title, R.id.img_back, R.id.txt_options, R.id.edt_search, R.id.img_clear,R.id.date_customer,R.id.txt_date_name})
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
                break;
            case R.id.edt_search:
                SearchActivity.start(SalesManSignAchievementActivity.this,
                        "", CommonSearch.class.getSimpleName());
                break;
            case R.id.img_clear:
                params.setKeyWords("");
                edtSearch.setText("");
                mPresenter.querySalesManSignAchievementList(true);
                imgClear.setVisibility(View.GONE);
                break;
            case R.id.txt_date_name:
                showCustomerDate(dateTextView);
                break;
            default:
                break;
        }
    }


    //点击自定义事件
    private void showCustomerDate(TextView dateText){
        if(isClickCustomer){
            if(isClickCustomerDateAggregation==0){
                dateWindow =dateWindow==null? new DateWindow(this):dateWindow;
                dateWindow.setSelectListener(date -> {
                    serverDate = CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
                    localDate = CalendarUtils.format(date, FORMAT_DATE);
                    timeType = TimeTypeEnum.DAY.getCode();
                    setDateSelect(dateText);
                });
                dateWindow.setOnDismissListener(()->{
                    dateArrow.setRotation(0);
                });
                dateArrow.setRotation(180);
                dateWindow.showAtLocation(getCurrentFocus(),Gravity.BOTTOM,0,0);
            }else if(isClickCustomerDateAggregation==1){
                //周的
                weekWindow = weekWindow==null? new DateWeekWindow(this):weekWindow;
                weekWindow.setCalendar(new Date());
                weekWindow.setSelectListener(date->{
                    serverDate =  CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
                    serverDate = DateUtil.getWeekFirstDay(0,Long.valueOf(serverDate))+"";
                    String endDate = DateUtil.getWeekLastDay(0,Long.valueOf(serverDate))+"";
                    localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                            + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
                    timeType = TimeTypeEnum.WEEK.getCode();
                    setDateSelect(dateText);
                });
                weekWindow.setOnDismissListener(()->{
                    dateArrow.setRotation(0);
                });
                dateArrow.setRotation(180);
                weekWindow.showAtLocation(getCurrentFocus(),Gravity.BOTTOM,0,0);
            }else if(isClickCustomerDateAggregation==2){
                //月
                dateYearMonthWindow =dateYearMonthWindow==null? new DateYearMonthWindow(this):dateYearMonthWindow;
                dateYearMonthWindow.setCalendar(new Date());
                dateYearMonthWindow.setSelectListener(date -> {
                    serverDate = DateUtil.getMonthFirstDay(0, Long.valueOf(CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE)))+"";
                    localDate = serverDate.substring(0,4)+"年"+"-"+serverDate.substring(4,6)+"月";
                    timeType = TimeTypeEnum.MONTH.getCode();
                    setDateSelect(dateText);
                });
                dateYearMonthWindow.setOnDismissListener(()->{
                    dateArrow.setRotation(0);
                });
                dateArrow.setRotation(180);
                dateYearMonthWindow.showAtLocation(getCurrentFocus(),Gravity.BOTTOM,0,0);
            }else if(isClickCustomerDateAggregation==3){
                //年的
                dateYearWindow = dateYearWindow==null? new DateYearWindow(this):dateYearWindow;
                dateYearWindow.setCalendar(new Date());
                dateYearWindow.setSelectListener(date -> {
                    serverDate = CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE).substring(0,4)+"0101";
                    localDate = serverDate.substring(0,4)+"年";
                    timeType = TimeTypeEnum.YEAR.getCode();
                    setDateSelect(dateText);
                });
                dateYearWindow.setOnDismissListener(()->{
                    dateArrow.setRotation(0);
                });
                dateArrow.setRotation(180);
                dateYearWindow.showAtLocation(getCurrentFocus(),Gravity.BOTTOM,0,0);
            }
        }
    }

    /**
     * 设置自定义的时间参数
     * @param dateText
     */
    private void setDateSelect(TextView dateText){
        dateText.setText(localDate);
        params.setTimeType(timeType);
        params.setTimeFlag(timeFlag);
        params.setDate(serverDate);
        mPresenter.querySalesManSignAchievementList(true);
    }


    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        if(view.getId()==R.id.txt_date_name_title){
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_CURRENT_DATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_YES_DATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_CURRENT_WEEK));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_WEEK));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_CURRENT_MONTH));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_MONTH));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CUSTOMER_DEFINE));
            mOptionsWindow.setOnDismissListener(()->{
                reportDateArrow.setRotation(0);
            });
            reportDateArrow.setRotation(180);
        }else {
            list.add(new OptionsBean(R.drawable.ic_report_date_customer, OptionType.OPTION_STATISTIC_DATE));
            list.add(new OptionsBean(R.drawable.ic_report_date_customer, OptionType.OPTION_STATISTIC_WEEK));
            list.add(new OptionsBean(R.drawable.ic_report_date_customer, OptionType.OPTION_STATISTIC_MONTH));
            list.add(new OptionsBean(R.drawable.ic_report_date_customer, OptionType.OPTION_STATISTIC_YEAR));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name)) {
                edtSearch.setText(name);
                params.setKeyWords(name);
                imgClear.setVisibility(View.VISIBLE);
            } else {
                params.setKeyWords("");
            }
            mPresenter.querySalesManSignAchievementList(true);
        }
    }

    class SalesManSignAchievementAdapter extends BaseQuickAdapter<SalesManSignAchievement, BaseViewHolder> {

        SalesManSignAchievementAdapter() {
            super(R.layout.item_report_customer_sign_achievement);
        }

        @Override
        protected void convert(BaseViewHolder helper, SalesManSignAchievement bean) {
            helper.setText(R.id.txt_saleman_code, bean.getSalesmanCode())
                    .setText(R.id.txt_saleman_name, bean.getSalesmanName())
                    .setText(R.id.txt_intent_customer, String.valueOf(bean.getIntentCustomerNum()))
                    .setText(R.id.txt_sign_customer, String.valueOf(bean.getSignCustomerNum()))
                    .setText(R.id.txt_sign_shop, String.valueOf(bean.getSignShopNum()))
                    .setText(R.id.txt_incr_intent_customer, String.valueOf(bean.getAddIntentCustomerNum()))
                    .setText(R.id.txt_incr_sign_customer, String.valueOf(bean.getAddSignCustomerNum()))
                    .setText(R.id.txt_incr_sign_shop, String.valueOf(bean.getAddSignShopNum()))
                    .itemView.setBackgroundResource(helper.getLayoutPosition() % 2 == 0 ?
                    R.drawable.bg_price_log_content_white : R.drawable.bg_price_log_content_gray);
        }
    }
}
