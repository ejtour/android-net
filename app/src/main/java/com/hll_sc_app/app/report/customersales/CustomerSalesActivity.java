package com.hll_sc_app.app.report.customersales;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.search.CustomerSearchActivity;
import com.hll_sc_app.app.report.customersales.detail.CustomerSalesDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.report.DateFilterView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.CUSTOMER_SALE_AGGREGATION)
public class CustomerSalesActivity extends BaseLoadActivity implements CustomerSalesContract.ICustomerSaleView, DateFilterView.DateFilterCallback {
    @BindView(R.id.rcs_back)
    ImageView mBack;
    @BindView(R.id.rcs_search_view)
    SearchView mSearchView;
    @BindView(R.id.rcs_date_filter)
    DateFilterView mDateFilter;
    @BindView(R.id.rcs_active_order)
    TextView mActiveOrder;
    @BindView(R.id.rcs_return_order)
    TextView mReturnOrder;
    @BindView(R.id.rcs_order_customer)
    TextView mOrderCustomer;
    @BindView(R.id.rcs_return_customer)
    TextView mReturnCustomer;
    @BindView(R.id.rcs_sales_amount)
    TextView mSalesAmount;
    @BindView(R.id.rcs_refund_amount)
    TextView mRefundAmount;
    @BindView(R.id.rcs_total_amount)
    TextView mTotalAmount;
    private CustomerSalesPresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setTranslucent(getWindow(), true);
        setContentView(R.layout.activity_report_customer_sales);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("actionType", "0");
        mReq.put("date", CalendarUtils.toLocalDate(new Date()));
        mReq.put("groupID", UserConfig.getGroupID());
        mReq.put("timeFlag", "0");
        mPresenter = CustomerSalesPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        showStatusBar();
        mSearchView.setTextColorWhite();
        mSearchView.setSearchTextLeft();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                CustomerSearchActivity.start(CustomerSalesActivity.this, searchContent);
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
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mBack.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(this);
        }
    }

    @Override
    public void setData(CustomerSalesResp customerSalesResp) {
        mActiveOrder.setText(CommonUtils.formatNum(customerSalesResp.getTotalValidBillNum()));
        mReturnOrder.setText(CommonUtils.formatNum(customerSalesResp.getTotalRefundBillNum()));
        mOrderCustomer.setText(String.format("%s/%s", CommonUtils.formatNum(customerSalesResp.getTotalOrderCustomerNum()),
                CommonUtils.formatNum(customerSalesResp.getTotalOrderCustomerShopNum())));
        mReturnCustomer.setText(String.format("%s/%s", CommonUtils.formatNum(customerSalesResp.getTotalRefundCustomerNum()),
                CommonUtils.formatNum(customerSalesResp.getTotalRefundCustomerShopNum())));
        mSalesAmount.setText(CommonUtils.formatMoney(customerSalesResp.getTotalSalesAmount()));
        mRefundAmount.setText(CommonUtils.formatMoney(customerSalesResp.getTotalRefundAmount()));
        mTotalAmount.setText(CommonUtils.formatMoney(customerSalesResp.getTotalAmount()));
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
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

    @OnClick(R.id.rcs_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.rcs_customer_sales_btn)
    public void customerSales() {
        CustomerSalesDetailActivity.start(0);
    }

    @OnClick(R.id.rcs_shop_sales_btn)
    public void shopSales() {
        CustomerSalesDetailActivity.start(1);
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
}
