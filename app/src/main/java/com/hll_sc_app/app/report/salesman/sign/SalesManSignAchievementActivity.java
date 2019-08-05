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
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.OrderSearch;
import com.hll_sc_app.app.search.stratery.SalesManSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.enums.TimeFlagEnum;
import com.hll_sc_app.bean.enums.TimeTypeEnum;
import com.hll_sc_app.bean.event.GoodsSearchEvent;
import com.hll_sc_app.bean.event.SalesManSearchEvent;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.group.PurchaserGroupBean;
import com.hll_sc_app.bean.report.salesman.SalesManAchievementReq;
import com.hll_sc_app.bean.report.salesman.SalesManSignAchievement;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;
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
@Route(path = RouterConfig.REPORT_SALESMAN_SIGN_ACHIEVEMENT)
public class SalesManSignAchievementActivity extends BaseLoadActivity implements BaseQuickAdapter.OnItemClickListener, SalesManSignAchievementContract.ISalesManSignAchievementView {
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
    private ContextOptionsWindow mOptionsWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    SalesManSignAchievementPresenter mPresenter;
    SalesManSignAchievementAdapter mAdapter;
    private EmptyView mEmptyView;

    String serverDate = "";
    String localDate = "";
    int timeType = TimeTypeEnum.DAY.getCode();
    int timeFlag = TimeFlagEnum.TODAY.getCode();
    private SalesManAchievementReq params = new SalesManAchievementReq();

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
        params.setDate(date);
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
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CURRENT_DATE)) {
            serverDate = DateUtil.currentTimeHllDT8() + "";
            localDate = CalendarUtils.format(new Date(), FORMAT_DATE);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_YES_DATE)) {
            serverDate = CalendarUtils.format(CalendarUtils.getDateBefore(new Date(), 1), CalendarUtils.FORMAT_LOCAL_DATE);
            localDate = CalendarUtils.format(CalendarUtils.getDateBefore(new Date(), 1), FORMAT_DATE);
            timeFlag = TimeFlagEnum.YESTERDAY.getCode();
            dateText = TimeFlagEnum.YESTERDAY.getDesc();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CURRENT_WEEK)) {
            serverDate = DateUtil.getWeekFirstDay(0) + "";
            String endDate = DateUtil.getWeekLastDay(0) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                    + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.WEEK.getCode();
            timeFlag = TimeFlagEnum.CURRENTWEEK.getCode();
            dateText = TimeFlagEnum.CURRENTWEEK.getDesc();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_WEEK)) {
            serverDate = DateUtil.getWeekFirstDay(-1) + "";
            String endDate = DateUtil.getWeekLastDay(-1) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                    + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.WEEK.getCode();
            timeFlag = TimeFlagEnum.LASTWEEK.getCode();
            dateText = TimeFlagEnum.LASTWEEK.getDesc();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CURRENT_MONTH)) {
            serverDate = DateUtil.getMonthFirstDay(0) + "";
            String endDate = DateUtil.getMonthLastDay(0) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                    + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.MONTH.getCode();
            timeFlag = TimeFlagEnum.CURRENTMONTH.getCode();
            dateText = TimeFlagEnum.CURRENTMONTH.getDesc();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_MONTH)) {
            serverDate = DateUtil.getMonthFirstDay(-1) + "";
            String endDate = DateUtil.getMonthLastDay(-1) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE)
                    + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, FORMAT_DATE);
            timeType = TimeTypeEnum.MONTH.getCode();
            timeFlag = TimeFlagEnum.LASTMONTH.getCode();
            dateText = TimeFlagEnum.CURRENTMONTH.getDesc();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CUSTOMER_DEFINE)) {

        } else {
            //导出
            isExport = true;
        }
        params.setDate(serverDate);
        params.setTimeType(timeType);
        params.setTimeFlag(timeFlag);
        if (!isExport) {
            dateTextView.setText(String.format("%s", localDate));
            textDate.setText(dateText);
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
        totalTxt.setText("--");
        totalIntentCustomerNum.setText(String.valueOf(salesManSignResp.getTotalIntentCustomerNum()));
        totalSignCustomerNum.setText(String.valueOf(salesManSignResp.getTotalSignCustomerNum()));
        totalSignShopNum.setText(String.valueOf(salesManSignResp.getTotalSignShopNum()));
        totalIncrIntentCustomer.setText(String.valueOf(salesManSignResp.getTotalAddIntentCustomerNum()));
        totalIncrSignCustomer.setText(String.valueOf(salesManSignResp.getTotalAddSignCustomerNum()));
        totalIncrSignShop.setText(String.valueOf(salesManSignResp.getTotalSignShopNum()));
    }

    private void showExportOptionsWindow(View v) {
        if (mExportOptionsWindow == null) {
            mExportOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mExportOptionsWindow.dismiss();
                        export(null);
                    });
        }
        mExportOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    @OnClick({R.id.txt_date_name_title, R.id.img_back, R.id.txt_options,R.id.edt_search,R.id.img_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_date_name_title:
                showOptionsWindow(textDate);
                break;
            case R.id.txt_options:
                showExportOptionsWindow(exportView);
            case R.id.edt_search:
                SearchActivity.start("",SalesManSearch.class.getSimpleName());
                break;
            case R.id.img_clear:
                params.setKeyWords("");
                edtSearch.setText("");
                mPresenter.querySalesManSignAchievementList(true);
                imgClear.setVisibility(View.GONE);
            default:
                break;
        }
    }

    private   void showOptionsWindow(View view){
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CURRENT_DATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_YES_DATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CURRENT_WEEK));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_WEEK));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CURRENT_MONTH));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_MONTH));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CUSTOMER_DEFINE));
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this).refreshList(list);
        }
        mOptionsWindow.setOnDismissListener(()->{
            reportDateArrow.setRotation(0);
        });
        reportDateArrow.setRotation(180);
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public String getSearchParam() {
        return null;
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
        Utils.bindEmail(this, (email) -> mPresenter.exportSalesManSignAchievement(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(params);
        mPresenter.exportSalesManSignAchievement(email, reqParams);
    }

    @Subscribe
    public void onEvent(SalesManSearchEvent event) {
        String name = event.getSearchWord();
        if (!TextUtils.isEmpty(name)) {
            edtSearch.setText(name);
            params.setKeyWords(name);
            imgClear.setVisibility(View.VISIBLE);
        }else{
            params.setKeyWords("");
        }
        mPresenter.querySalesManSignAchievementList(true);
    }

    class SalesManSignAchievementAdapter extends BaseQuickAdapter<SalesManSignAchievement, BaseViewHolder> {

        SalesManSignAchievementAdapter() {
            super(R.layout.item_report_customer_sign_achievement);
        }

        @Override
        protected void convert(BaseViewHolder helper, SalesManSignAchievement bean) {
            helper.setText(R.id.txt_saleman_code, bean.getSalesmanCode())
                    .setText(R.id.txt_saleman_name, bean.getSalesmanName())
                    .setText(R.id.txt_intent_customer, CommonUtils.formatNumber(bean.getIntentCustomerNum()))
                    .setText(R.id.txt_sign_customer, CommonUtils.formatNumber(bean.getSignCustomerNum()))
                    .setText(R.id.txt_sign_shop, CommonUtils.formatMoney(bean.getSignShopNum()))
                    .setText(R.id.txt_incr_intent_customer, CommonUtils.formatMoney(bean.getAddIntentCustomerNum()))
                    .setText(R.id.txt_incr_sign_customer, CommonUtils.formatNumber(bean.getAddSignCustomerNum()))
                    .setText(R.id.txt_incr_sign_shop, CommonUtils.formatMoney(bean.getAddSignShopNum()))
                    .itemView.setBackgroundResource(helper.getLayoutPosition() % 2 == 0 ?
                    R.drawable.bg_price_log_content_white : R.drawable.bg_price_log_content_gray);
        }
    }
}
