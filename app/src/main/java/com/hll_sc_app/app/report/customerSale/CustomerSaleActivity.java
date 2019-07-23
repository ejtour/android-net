package com.hll_sc_app.app.report.customerSale;

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
import com.hll_sc_app.app.report.dailySale.DailyAggregationContract;
import com.hll_sc_app.app.report.dailySale.DailyAggregationPresenter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmount;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

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
public class CustomerSaleActivity extends BaseLoadActivity implements CustomerSaleContract.ICustomerSaleView {


    @Override
    public void showCustomerSaleGather(CustomerSalesResp customerSalesResp) {

    }

    @Override
    public String getStartDate() {
        return null;
    }

    @Override
    public String getEndDate() {
        return null;
    }

    @Override
    public void exportSuccess(String email) {

    }

    @Override
    public void exportFailure(String tip) {

    }

    @Override
    public void bindEmail() {

    }
}
