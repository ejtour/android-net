package com.hll_sc_app.app.report.customerSale;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.enums.TimeFlagEnum;
import com.hll_sc_app.bean.enums.TimeTypeEnum;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.report.resp.group.PurchaserGroupBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ContextOptionsWindow;

import java.util.ArrayList;
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
@Route(path = RouterConfig.CUSTOMER_SALE_AGGREGATION)
public class CustomerSaleActivity extends BaseLoadActivity implements CustomerSaleContract.ICustomerSaleView, BaseQuickAdapter.OnItemClickListener {

    private static final int CUSTOMER_SALE_CODE = 10001;
    String dateFormate = "yyyy/MM/dd";
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.txt_order)
    TextView txtOrder;
    @BindView(R.id.txt_refund)
    TextView txtRefund;
    @BindView(R.id.txt_customer_num)
    TextView txtCustomerNum;
    @BindView(R.id.txt_refund_customers)
    TextView txtRefundCustomers;
    @BindView(R.id.txt_amount)
    TextView txtAmount;
    @BindView(R.id.txt_refund_amount)
    TextView txtRefundAmount;
    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;
    @BindView(R.id.customer_sale_agg_link)
    LinearLayout customerSaleAggLink;
    @BindView(R.id.customer_sale_shop_link)
    LinearLayout customerSaleShopLink;
    @BindView(R.id.date_flag)
    TextView dateFlag;
    @BindView(R.id.order_statics)
    LinearLayout orderStatics;
    @BindView(R.id.customer_statics)
    LinearLayout customerStatics;
    @BindView(R.id.amount_statics)
    LinearLayout amountStatics;
    @BindView(R.id.amount_total)
    LinearLayout amountTotal;
    @BindView(R.id.txt_customer_agg)
    TextView txtCustomerAgg;
    @BindView(R.id.txt_customer_agg_shop)
    TextView txtCustomerAggShop;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.report_date_arrow)
    ImageView reportDateArrow;


    private CustomerSalesPresenter mPresenter;

    private ContextOptionsWindow mOptionsWindow;
    String serverDate = "";
    int timeType = TimeTypeEnum.DAY.getCode();
    int timeFlag = TimeFlagEnum.TODAY.getCode();
    private CustomerSaleReq params = new CustomerSaleReq();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sales_gather);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initDefaultTime();
        mPresenter = CustomerSalesPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    /**
     * 初始化时间
     */
    private void initDefaultTime() {
        Date currentDate = new Date();
        String date = CalendarUtils.format(currentDate, CalendarUtils.FORMAT_LOCAL_DATE);
        date = CalendarUtils.getDateFormatString(date, CalendarUtils.FORMAT_LOCAL_DATE, dateFormate);
        textDate.setText(String.format("%s", date));
        params.setDate(date);
    }

    @Override
    public void showCustomerSaleGather(CustomerSalesResp customerSalesResp) {
        txtOrder.setText(customerSalesResp.getTotalOrderNum() + "");
        txtRefund.setText(customerSalesResp.getTotalRefundBillNum() + "");
        txtCustomerNum.setText(customerSalesResp.getTotalOrderCustomerNum() + "/" + customerSalesResp.getTotalOrderCustomerShopNum());
        txtRefundCustomers.setText(customerSalesResp.getTotalRefundCustomerNum() + "/" + customerSalesResp.getTotalRefundCustomerShopNum());
        txtAmount.setText(CommonUtils.formatMoney(customerSalesResp.getTotalSalesAmount()));
        txtRefundAmount.setText(CommonUtils.formatMoney(customerSalesResp.getTotalRefundAmount()));
        txtTotalAmount.setText(CommonUtils.formatMoney(customerSalesResp.getTotalAmount()));
    }

    @Override
    public CustomerSaleReq getParams() {
        params.setOrder(1);
        params.setSortBy(1);
        params.setActionType((byte) 0);
        return params;
    }

    @OnClick({R.id.img_back, R.id.customer_sale_agg_link, R.id.customer_sale_shop_link, R.id.edt_search, R.id.img_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.customer_sale_agg_link:
                RouterUtil.goToActivity(RouterConfig.CUSTOMER_SALE_DETAILS);
                break;
            case R.id.customer_sale_shop_link:
                RouterUtil.goToActivity(RouterConfig.CUSTOMER_SALE_SHOP_DETAILS);
                break;
            case R.id.edt_search:
                RouterUtil.goToActivity(RouterConfig.CUSTOMER_SALE_SEARCH, this, CUSTOMER_SALE_CODE);
                break;
            case R.id.img_clear:
                edtSearch.setText(null);
                CustomerSaleReq params = getParams();
                params.setDate(serverDate);
                params.setTimeType(timeType);
                params.setTimeFlag(timeFlag);
                params.setPurchaserID("");
                mPresenter.queryCustomerSaleGather(true);
                imgClear.setVisibility(View.GONE);
                break;
            default:
                break;
        }
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
        mOptionsWindow.setOnDismissListener(()->{
            reportDateArrow.setRotation(0);
        });
        reportDateArrow.setRotation(180);
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }


    @OnClick(R.id.date_flag)
    public void onViewClicked() {
        showOptionsWindow(dateFlag);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        String localDate = "";
        String dateText = TimeFlagEnum.TODAY.getDesc();
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CURRENT_DATE)) {
            serverDate = DateUtil.currentTimeHllDT8() + "";
            localDate = CalendarUtils.format(new Date(), dateFormate);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_YES_DATE)) {
            serverDate = CalendarUtils.format(CalendarUtils.getDateBefore(new Date(), 1), CalendarUtils.FORMAT_LOCAL_DATE);
            localDate = CalendarUtils.format(CalendarUtils.getDateBefore(new Date(), 1), dateFormate);
            timeFlag = TimeFlagEnum.YESTERDAY.getCode();
            dateText = TimeFlagEnum.YESTERDAY.getDesc();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CURRENT_WEEK)) {
            serverDate = DateUtil.getWeekFirstDay(0) + "";
            String endDate = DateUtil.getWeekLastDay(0) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, dateFormate)
                    + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, dateFormate);
            timeType = TimeTypeEnum.WEEK.getCode();
            timeFlag = TimeFlagEnum.CURRENTWEEK.getCode();
            dateText = TimeFlagEnum.CURRENTWEEK.getDesc();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_WEEK)) {
            serverDate = DateUtil.getWeekFirstDay(-1) + "";
            String endDate = DateUtil.getWeekLastDay(-1) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, dateFormate)
                    + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, dateFormate);
            timeType = TimeTypeEnum.WEEK.getCode();
            timeFlag = TimeFlagEnum.LASTWEEK.getCode();
            dateText = TimeFlagEnum.LASTWEEK.getDesc();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CURRENT_MONTH)) {
            serverDate = DateUtil.getMonthFirstDay(0) + "";
            String endDate = DateUtil.getMonthLastDay(0) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, dateFormate)
                    + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, dateFormate);
            timeType = TimeTypeEnum.MONTH.getCode();
            timeFlag = TimeFlagEnum.CURRENTMONTH.getCode();
            dateText = TimeFlagEnum.CURRENTMONTH.getDesc();
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_MONTH)) {
            serverDate = DateUtil.getMonthFirstDay(-1) + "";
            String endDate = DateUtil.getMonthLastDay(-1) + "";
            localDate = CalendarUtils.getDateFormatString(serverDate, CalendarUtils.FORMAT_LOCAL_DATE, dateFormate)
                    + " - " + CalendarUtils.getDateFormatString(endDate, CalendarUtils.FORMAT_LOCAL_DATE, dateFormate);
            timeType = TimeTypeEnum.MONTH.getCode();
            timeFlag = TimeFlagEnum.LASTMONTH.getCode();
            dateText = TimeFlagEnum.CURRENTMONTH.getDesc();
        }
        params.setDate(serverDate);
        params.setTimeType(timeType);
        params.setTimeFlag(timeFlag);
        textDate.setText(String.format("%s", localDate));
        dateFlag.setText(dateText);
        mPresenter.queryCustomerSaleGather(true);
        mOptionsWindow.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CUSTOMER_SALE_CODE && resultCode == RESULT_OK) {
            PurchaserGroupBean bean = data.getParcelableExtra("result");
            CustomerSaleReq params = getParams();
            params.setDate(serverDate);
            params.setTimeType(timeType);
            params.setTimeFlag(timeFlag);
            params.setPurchaserID(String.valueOf(bean.getPurchaserID()));
            mPresenter.queryCustomerSaleGather(true);
            edtSearch.setText(bean.getPurchaserName());
            imgClear.setVisibility(View.VISIBLE);
        }
    }
}
