package com.hll_sc_app.app.report.dailySale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmount;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;

import org.greenrobot.eventbus.EventBus;

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
public class DailyAggregationActivity extends BaseLoadActivity implements DailyAggregationContract.IDailyAggregationView {
    private static final String FORMAT_DATE = "yyyy/MM/dd";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_date_name)
    TextView mTxtDateName;
    @BindView(R.id.rl_select_date)
    RelativeLayout mRlSelectDate;
    private DailyAggregationListAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
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

    @OnClick({R.id.img_back,  R.id.rl_select_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_select_date:
                showDateRangeWindow();
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

    @Override
    public void showDailyAggregationList(List<DateSaleAmount> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
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
    public void hideLoading() {
        super.hideLoading();
    }

    class DailyAggregationListAdapter extends BaseQuickAdapter<DateSaleAmount, BaseViewHolder> {

        DailyAggregationListAdapter() {
            super(R.layout.item_report_daily);
        }

        @Override
        protected void convert(BaseViewHolder helper, DateSaleAmount bean) {
            helper.setText(R.id.daily_amount, CommonUtils.format(bean.getSubtotalAmount(),2))
                    .setText(R.id.daily_time,CalendarUtils.format(CalendarUtils.parseLocal(bean.getDate()+"",CalendarUtils.FORMAT_LOCAL_DATE),"yyyy/MM/dd"))
                .setText(R.id.daily_order_num, bean.getOrderNum()+"")
                .setText(R.id.daily_customer_price, CommonUtils.format(bean.getAverageShopAmount(),2))
                .setText(R.id.daily_avg_price, CommonUtils.format(bean.getAverageAmount(),2))
                .setText(R.id.daily_order_customers, bean.getOrderCustomerNum()+"/"+bean.getOrderCustomerShopNum());
        }
    }
}
