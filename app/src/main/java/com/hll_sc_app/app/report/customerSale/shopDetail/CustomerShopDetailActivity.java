package com.hll_sc_app.app.report.customerSale.shopDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.enums.TimeFlagEnum;
import com.hll_sc_app.bean.enums.TimeTypeEnum;
import com.hll_sc_app.bean.event.GoodsRelevanceListSearchEvent;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesRecords;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SyncHorizontalScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 客户销售汇总明细
 *
 * @author 初坤
 * @date 20190723
 */
@Route(path = RouterConfig.CUSTOMER_SALE_SHOP_DETAILS)
public class CustomerShopDetailActivity extends BaseLoadActivity implements CustomerShopDetailContract.ICustomerShopDetailView, BaseQuickAdapter.OnItemClickListener {
    private static final String FORMAT_DATE = "yyyy/MM/dd";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_date_name)
    TextView mTxtDateName;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.txt_date_name_title)
    TextView dateFlagTextView;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.total_txt)
    TextView totalTxt;
    @BindView(R.id.total_order_num)
    TextView totalOrderNum;
    @BindView(R.id.total_valid_order_num)
    TextView totalValidOrderNum;
    @BindView(R.id.total_trade_amount)
    TextView totalTradeAmount;
    @BindView(R.id.total_avg_amount)
    TextView totalAvgAmount;
    @BindView(R.id.total_refund_num)
    TextView totalRefundNum;
    @BindView(R.id.total_refund_amount)
    TextView totalRefundAmount;
    @BindView(R.id.total_total_amount)
    TextView totalTotalAmount;
    @BindView(R.id.customer_shop_content_detail)
    SyncHorizontalScrollView syncHorizontalScrollView;
    @BindView(R.id.customer_shop_total_detail)
    SyncHorizontalScrollView footSyncHorizontalScrollView;
    private CustomerShopListAdapter mAdapter;
    private CustomerShopDetailPresenter mPresenter;
    private ContextOptionsWindow mOptionsWindow;
    private EmptyView mEmptyView;
    private CustomerSaleReq params = new CustomerSaleReq();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_shop_detail);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initDefaultTime();
        mPresenter = CustomerShopDetailPresenter.newInstance();
        mAdapter = new CustomerShopListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        View headView = LayoutInflater.from(this).inflate(R.layout.item_customer_shop_detail_title, mRecyclerView, false);
        mAdapter.addHeaderView(headView);
        mEmptyView = EmptyView.newBuilder(this).setTipsTitle("当前日期下没有数据").create();
        syncHorizontalScrollView.setLinkageViews(footSyncHorizontalScrollView);
        footSyncHorizontalScrollView.setLinkageViews(syncHorizontalScrollView);
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void initDefaultTime() {
        Date currentDate = new Date();
        String date = CalendarUtils.format(currentDate, CalendarUtils.FORMAT_LOCAL_DATE);
        params.setDate(date);
        String displayDate = CalendarUtils.format(currentDate, FORMAT_DATE);
        mTxtDateName.setText(String.format("%s", displayDate));
    }

    @Subscribe
    public void onEvent(GoodsRelevanceListSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.txt_date_name_title)
    public void onViewClicked() {
        showOptionsWindow(dateFlagTextView);
    }

    private void showOptionsWindow(View view) {
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
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }

    @Override
    public void showCustomerShopDetailList(List<CustomerSalesRecords> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() - 1 != total);
    }

    @Override
    public void showCustomerShopGatherDatas(CustomerSalesResp customerSalesResp) {
        txtTotal.setText("合计");
        totalTxt.setText("--");
        totalOrderNum.setText(String.valueOf(customerSalesResp.getTotalOrderNum()));
        totalValidOrderNum.setText(String.valueOf(customerSalesResp.getTotalValidBillNum()));
        totalValidOrderNum.setText(String.valueOf(customerSalesResp.getTotalValidBillNum()));
        totalTradeAmount.setText(CommonUtils.formatMoney(customerSalesResp.getTotalSalesAmount()));
        totalAvgAmount.setText(CommonUtils.formatMoney(customerSalesResp.getTotalAverageAmount()));
        totalRefundNum.setText(String.valueOf(customerSalesResp.getTotalRefundBillNum()));
        totalRefundAmount.setText(CommonUtils.formatMoney(customerSalesResp.getTotalRefundAmount()));
        totalTotalAmount.setText(CommonUtils.formatMoney(customerSalesResp.getTotalAmount()));
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    @Override
    public CustomerSaleReq getParams() {
        params.setOrder(1);
        params.setSortBy(1);
        params.setActionType((byte) 1);
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
        Utils.bindEmail(this, email -> mPresenter.exportCustomerShopDetail(email));
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
        String serverDate = "";
        String localDate = "";
        int timeType = TimeTypeEnum.DAY.getCode();
        int timeFlag = TimeFlagEnum.TODAY.getCode();
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

        }
        params.setDate(serverDate);
        params.setTimeType(timeType);
        params.setTimeFlag(timeFlag);
        mTxtDateName.setText(String.format("%s", localDate));
        dateFlagTextView.setText(dateText);
        mPresenter.queryCustomerShopDetailList(true);
        mOptionsWindow.dismiss();
    }


    class CustomerShopListAdapter extends BaseQuickAdapter<CustomerSalesRecords, BaseViewHolder> {

        CustomerShopListAdapter() {
            super(R.layout.item_customer_shop_detail);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomerSalesRecords bean) {
            helper.setText(R.id.txt_purchaser_name, bean.getPurchaserName())
                    .setText(R.id.txt_coop_shop_name, bean.getShopName())
                    .setText(R.id.txt_order_num, CommonUtils.formatNumber(bean.getOrderNum()))
                    .setText(R.id.txt_valid_order_num, CommonUtils.formatNumber(bean.getValidOrderNum()))
                    .setText(R.id.txt_trade_amount, CommonUtils.formatMoney(bean.getTradeAmount()))
                    .setText(R.id.txt_avg_amount, CommonUtils.formatMoney(bean.getAverageAmount()))
                    .setText(R.id.txt_refund_num, CommonUtils.formatNumber(bean.getRefundBillNum()))
                    .setText(R.id.txt_refund_amount, CommonUtils.formatMoney(bean.getRefundAmount()))
                    .setText(R.id.txt_total_amount, CommonUtils.formatMoney(bean.getSubtotalAmount()))
                    .itemView.setBackgroundResource(helper.getLayoutPosition() % 2 == 0 ?
                    R.drawable.bg_price_log_content_gray : R.drawable.bg_price_log_content_white);
        }
    }
}
