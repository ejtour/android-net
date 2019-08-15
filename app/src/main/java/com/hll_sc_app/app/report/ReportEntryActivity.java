package com.hll_sc_app.app.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.ReportIcon;
import com.hll_sc_app.bean.report.ReportItem;
import com.hll_sc_app.bean.report.ReportLabel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterConfig.REPORT_ENTRY)
public class ReportEntryActivity extends BaseLoadActivity {

    @BindView(R.id.are_list_view)
    RecyclerView mListView;

    public static void start() {
        RouterUtil.goToActivity(RouterConfig.REPORT_ENTRY);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_entry);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mListView.setAdapter(new ReportEntryAdapter(prepareMenu()));
    }

    private List<ReportItem> prepareMenu() {
        List<ReportItem> list = new ArrayList<>();
        list.add(new ReportItem(ReportIcon.SALES_STATISTICS, ReportLabel.SALES_STATISTICS, RouterConfig.REPORT_PRODUCT_SALES_STATISTICS));
        list.add(new ReportItem(ReportIcon.ORDER_GOODS_DETAILS, ReportLabel.ORDER_GOODS_DETAILS, RouterConfig.REPORT_ORDER_GOODS, true));
        list.add(new ReportItem(ReportIcon.DAILY_SALES_VOLUME, ReportLabel.DAILY_SALES_VOLUME, RouterConfig.REPORT_DAILY_AGGREGATION));
        list.add(new ReportItem(ReportIcon.CUSTOMER_SALES, ReportLabel.CUSTOMER_SALES, RouterConfig.CUSTOMER_SALE_AGGREGATION, true));
        list.add(new ReportItem(ReportIcon.SALES_MAN_SIGN, ReportLabel.SALES_MAN_SIGN_ACHIEVEMENT,
            RouterConfig.REPORT_SALESMAN_SIGN_ACHIEVEMENT));
        list.add(new ReportItem(ReportIcon.SALES_PERFORMANCE, ReportLabel.SALES_MAN_SALES_ACHIEVEMENT,
            RouterConfig.REPORT_SALESMAN_SALES_ACHIEVEMENT));
        list.add(new ReportItem(ReportIcon.STOCKOUT_DIFFERENCES, ReportLabel.DELIVERY_LACK_GATHER,
            RouterConfig.REPORT_DELIVERY_LACK_GATHER));
        list.add(new ReportItem(ReportIcon.STOCKOUT_STATISTICS,ReportLabel.CUSTOMER_LACK_AGGREGATION,RouterConfig.REPORT_CUSTOMER_LACK_SUMMARY));
        list.add(new ReportItem(ReportIcon.REFUND_REASONS, ReportLabel.REFUND_REASONS, RouterConfig.REFUND_REASON_STATICS));

        return list;
    }
}
