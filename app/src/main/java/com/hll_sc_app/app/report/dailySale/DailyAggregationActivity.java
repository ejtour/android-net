package com.hll_sc_app.app.report.dailySale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmount;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 日销售汇总
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_DAILY_AGGREGATION)
public class DailyAggregationActivity extends BaseLoadActivity implements DailyAggregationContract.IDailyAggregationView,BaseQuickAdapter.OnItemClickListener {
    private static final String FORMAT_DATE = "yyyy/MM/dd";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_date_name)
    TextView mTxtDateName;
    @BindView(R.id.rl_select_date)
    RelativeLayout mRlSelectDate;
    @BindView(R.id.daily_totalAmount)
    TextView dailyTotalAmount;
    @BindView(R.id.daily_totalnum)
    TextView dailyTotalnum;
    @BindView(R.id.txt_options)
    ImageView txtOptions;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;


    private DailyAggregationListAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    private DailyAggregationPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_daily_sale);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initDefaultTime();
        mPresenter = DailyAggregationPresenter.newInstance();
        mAdapter = new DailyAggregationListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreDailyAggregationList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryDailyAggregationList(false);
            }
        });
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initDefaultTime() {
        Date endDate = new Date();
        Date startDate = CalendarUtils.getDateBeforeMonth(endDate, 1);
        String startStr = CalendarUtils.format(startDate, FORMAT_DATE);
        String endStr = CalendarUtils.format(endDate, FORMAT_DATE);
        mTxtDateName.setText(String.format("%s-%s", startStr, endStr));
        mTxtDateName.setTag(R.id.date_start, CalendarUtils.format(startDate, CalendarUtils.FORMAT_SERVER_DATE));
        mTxtDateName.setTag(R.id.date_end, CalendarUtils.format(endDate, CalendarUtils.FORMAT_SERVER_DATE));
    }

    @OnClick({R.id.img_back, R.id.rl_select_date,R.id.txt_options})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_select_date:
                showDateRangeWindow();
                break;
            case R.id.txt_options:
                showExportOptionsWindow(txtOptions);
                break;
            default:
                break;
        }
    }

    private void showDateRangeWindow() {
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                if (start == null && end == null) {
                    mTxtDateName.setText(null);
                    mTxtDateName.setTag(R.id.date_start, "");
                    mTxtDateName.setTag(R.id.date_end, "");
                    mPresenter.queryDailyAggregationList(true);
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), FORMAT_DATE);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), FORMAT_DATE);
                    mTxtDateName.setText(String.format("%s-%s", startStr, endStr));
                    mTxtDateName.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    mTxtDateName.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(),
                            CalendarUtils.FORMAT_SERVER_DATE));
                    mPresenter.queryDailyAggregationList(true);
                }
            });
        }
        mDateRangeWindow.showAsDropDownFix(mRlSelectDate);
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
    public void showDailyAggregationList(DateSaleAmountResp dateSaleAmountResp, boolean append, int total) {
        if (append) {
            if (!CommonUtils.isEmpty(dateSaleAmountResp.getRecords()))
                mAdapter.addData(dateSaleAmountResp.getRecords());
        } else {
            mAdapter.setNewData(dateSaleAmountResp.getRecords());
        }
        dailyTotalAmount.setText("总交易金额:¥" + CommonUtils.formatMoney(dateSaleAmountResp.getTotalSubtotalAmount()));
        dailyTotalnum.setText("总订单数:" + dateSaleAmountResp.getTotalOrderNum());
    }

    @Override
    public String getStartDate() {
        String startTime = null;
        if (mTxtDateName.getTag(R.id.date_start) != null) {
            startTime = (String) mTxtDateName.getTag(R.id.date_start);
        }
        return startTime;
    }

    @Override
    public String getEndDate() {
        String endTime = null;
        if (mTxtDateName.getTag(R.id.date_end) != null) {
            endTime = (String) mTxtDateName.getTag(R.id.date_end);
        }
        return endTime;
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
        Utils.bindEmail(this, email -> mPresenter.exportDailyReport(email));
    }

    @Override
    public void export(String email) {
        mPresenter.exportDailyReport(email);
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

    class DailyAggregationListAdapter extends BaseQuickAdapter<DateSaleAmount, BaseViewHolder> {

        DailyAggregationListAdapter() {
            super(R.layout.item_report_daily);
        }

        @Override
        protected void convert(BaseViewHolder helper, DateSaleAmount bean) {
            helper.setText(R.id.daily_amount, CommonUtils.formatMoney(bean.getSubtotalAmount()))
                    .setText(R.id.daily_time, CalendarUtils.format(CalendarUtils.parseLocal(bean.getDate() + "", CalendarUtils.FORMAT_LOCAL_DATE), "yyyy/MM/dd"))
                    .setText(R.id.daily_order_num, bean.getOrderNum() + "")
                    .setText(R.id.daily_customer_price, CommonUtils.formatMoney(bean.getAverageShopAmount()))
                    .setText(R.id.daily_avg_price, CommonUtils.formatMoney(bean.getAverageAmount()))
                    .setText(R.id.daily_order_customers, bean.getOrderCustomerNum() + "/" + bean.getOrderCustomerShopNum());
        }
    }


}
