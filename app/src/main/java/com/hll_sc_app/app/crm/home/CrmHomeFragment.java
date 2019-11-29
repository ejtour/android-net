package com.hll_sc_app.app.crm.home;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hll_sc_app.R;
import com.hll_sc_app.app.message.MessageActivity;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.home.ManagementShopResp;
import com.hll_sc_app.bean.home.StatisticResp;
import com.hll_sc_app.bean.home.TrendBean;
import com.hll_sc_app.bean.home.VisitResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.impl.IMessageCount;
import com.hll_sc_app.widget.home.TrendMarker;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

@Route(path = RouterConfig.CRM_HOME)
public class CrmHomeFragment extends BaseLoadFragment implements ICrmHomeContract.ICrmHomeView, IMessageCount {
    private final SimpleDateFormat FORMAT = new SimpleDateFormat("MM.dd", Locale.getDefault());
    @BindView(R.id.fch_top_bg)
    ImageView mTopBg;
    @BindView(R.id.fch_today_amount)
    TextView mTodayAmount;
    @BindView(R.id.fch_today_shop_num)
    TextView mTodayShopNum;
    @BindView(R.id.fch_today_order_num)
    TextView mTodayOrderNum;
    @BindView(R.id.fch_warehouse_shop)
    TextView mWarehouseShop;
    @BindView(R.id.fch_warehouse_order)
    TextView mWarehouseOrder;
    @BindView(R.id.fch_warehouse_amount)
    TextView mWarehouseAmount;
    @BindView(R.id.fch_warehouse_group)
    Group mWarehouseGroup;
    @BindView(R.id.fch_settlement_shop)
    TextView mSettlementShop;
    @BindView(R.id.fch_settlement_order)
    TextView mSettlementOrder;
    @BindView(R.id.fch_settlement_amount)
    TextView mSettlementAmount;
    @BindView(R.id.fch_pending_visit)
    TextView mPendingVisit;
    @BindView(R.id.fch_remain_visit)
    TextView mRemainVisit;
    @BindView(R.id.fch_visited)
    TextView mVisited;
    @BindView(R.id.fch_valid_visit)
    TextView mValidVisit;
    @BindView(R.id.fch_expire_shop)
    TextView mExpireShop;
    @BindView(R.id.fch_expire_order)
    TextView mExpireOrder;
    @BindView(R.id.fch_expire_amount)
    TextView mExpireAmount;
    @BindView(R.id.fch_customer_service)
    TextView mCustomerService;
    @BindView(R.id.fch_driver)
    TextView mDriver;
    @BindView(R.id.fch_warehouse_receive)
    TextView mWarehouseReceive;
    @BindView(R.id.fch_finance)
    TextView mFinance;
    @BindView(R.id.fch_trend_chart)
    LineChart mTrendChart;
    @BindView(R.id.fch_management_shop)
    TextView mManagementShop;
    @BindView(R.id.fch_added_month)
    TextView mAddedMonth;
    @BindView(R.id.fch_will_fall)
    TextView mWillFall;
    @BindView(R.id.fch_scroll_view)
    NestedScrollView mScrollView;
    @BindView(R.id.fch_refresh_view)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.fch_title_bar)
    View mTitleBar;
    @BindView(R.id.fch_message_icon)
    ImageView mMessageIcon;
    @BindView(R.id.fch_message_count)
    TextView mMessageCount;
    @BindDimen(R.dimen.title_bar_height)
    int mTitleBarHeight;
    Unbinder unbinder;
    private ICrmHomeContract.ICrmHomePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crm_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        mPresenter = CrmHomePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        showStatusBar();
        mTitleBar.getBackground().mutate().setAlpha(0);
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int alpha = 0;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (mTitleBar == null) return;
                if (scrollY <= mTitleBarHeight) {
                    alpha = (int) (255 * (float) scrollY / mTitleBarHeight);
                    mTitleBar.getBackground().mutate().setAlpha(alpha);
                } else if (alpha < 255) {
                    alpha = 255;
                    mTitleBar.getBackground().mutate().setAlpha(alpha);
                }
            }
        });
        mRefreshView.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                if (mTopBg == null) return;
                mTopBg.setScaleX(1 + percent * 0.7f);
                mTopBg.setScaleY(1 + percent * 0.7f);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        initChart();
    }

    private void initChart() {
        Legend legend = mTrendChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setYOffset(10);
        legend.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_666666));
        legend.setTextSize(10);

        mTrendChart.getDescription().setEnabled(false);
        mTrendChart.setNoDataText("无数据");
        mTrendChart.setScaleEnabled(false);
        mTrendChart.setPinchZoom(false);
        mTrendChart.setExtraOffsets(14, 0, 14, 8);

        mTrendChart.setMarker(new TrendMarker(requireContext(), mTrendChart));

        XAxis xAxis = mTrendChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return FORMAT.format(CalendarUtils.getDateBefore(new Date(),
                        (int) (mTrendChart.getXAxis().getAxisMaximum() - value)));
            }
        });
        xAxis.setDrawGridLines(false);
        xAxis.setGridColor(ContextCompat.getColor(requireContext(), R.color.color_999999));
        xAxis.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_999999));
        xAxis.setTextSize(10);

        YAxis axisLeft = mTrendChart.getAxisLeft();
        axisLeft.setDrawAxisLine(false);
        axisLeft.setLabelCount(7, true);
        axisLeft.setAxisMinimum(0);
        axisLeft.setGridColor(ContextCompat.getColor(requireContext(), R.color.color_999999));
        axisLeft.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_999999));
        axisLeft.setTextSize(10);

        YAxis axisRight = mTrendChart.getAxisRight();
        axisRight.setDrawAxisLine(false);
        axisRight.setLabelCount(8, true);
        axisRight.setAxisMinimum(0);
        axisRight.setGridColor(ContextCompat.getColor(requireContext(), R.color.color_999999));
        axisRight.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_999999));
        axisRight.setTextSize(10);
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mMessageIcon.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(requireContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fch_pending_visit_btn, R.id.fch_remain_visit_btn, R.id.fch_visited_btn, R.id.fch_valid_visit_btn})
    public void visit(View view) {
        showToast("拜访待添加");
        switch (view.getId()) {
            case R.id.fch_pending_visit_btn:
                break;
            case R.id.fch_remain_visit_btn:
                break;
            case R.id.fch_visited_btn:
                break;
            case R.id.fch_valid_visit_btn:
                break;
        }
    }

    @OnClick({R.id.fch_customer_service_btn, R.id.fch_driver_btn, R.id.fch_warehouse_btn, R.id.fch_finance_btn})
    public void afterSales(View view) {
        showToast("售后待添加");
        switch (view.getId()) {
            case R.id.fch_customer_service_btn:
                break;
            case R.id.fch_driver_btn:
                break;
            case R.id.fch_warehouse_btn:
                break;
            case R.id.fch_finance_btn:
                break;
        }
    }

    @OnClick(R.id.fch_message_icon)
    public void message() {
        MessageActivity.start();
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick(R.id.fch_management_btn)
    public void shopManagement() {
        showToast("门店管理待添加");
    }

    @OnClick(R.id.fch_summary_bg)
    public void orderManagement() {
        showToast("订单管理待添加");
    }

    @Override
    public void updateHomeStatistic(StatisticResp resp) {
        mTodayAmount.setText(CommonUtils.formatMoney(resp.getAmount()));
        mTodayShopNum.setText(String.valueOf(resp.getShopNum()));
        mTodayOrderNum.setText(String.valueOf(resp.getBillNum()));
        mSettlementShop.setText(String.valueOf(resp.getSettlementShopNum()));
        mSettlementOrder.setText(String.valueOf(resp.getSettlementBillNum()));
        mSettlementAmount.setText(CommonUtils.formatMoney(resp.getSettlementAmount()));
        mExpireShop.setText(String.valueOf(resp.getSettlementDateShopNum()));
        mExpireOrder.setText(String.valueOf(resp.getSettlementDateBillNum()));
        mExpireAmount.setText(CommonUtils.formatMoney(resp.getSettlementDateUnsettleAmount()));
        mCustomerService.setText(String.valueOf(resp.getCustomServicedRefundNum()));
        mDriver.setText(String.valueOf(resp.getDrivedRefundNum()));
        mWarehouseReceive.setText(String.valueOf(resp.getWareHousedRefundNum()));
        mFinance.setText(String.valueOf(resp.getFinanceReviewRefundNum()));
        if (resp.isWareHouse()) {
            mWarehouseGroup.setVisibility(View.VISIBLE);
            mWarehouseShop.setText(String.valueOf(resp.getWareHouseShopNum()));
            mWarehouseOrder.setText(String.valueOf(resp.getWareHouseBillNum()));
            mWarehouseAmount.setText(CommonUtils.formatMoney(resp.getWareHouseDeliveryGoodsAmount()));
        } else mWarehouseGroup.setVisibility(View.GONE);
    }

    @Override
    public void updateTrend(List<TrendBean> list) {
        List<Entry> amountList = new ArrayList<>();
        List<Entry> billNumList = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            double maxAmount = 0;
            double maxBill = 0;
            for (int i = 0; i < list.size(); i++) {
                TrendBean trendBean = list.get(i);
                maxAmount = Math.max(maxAmount, trendBean.getAmount());
                maxBill = Math.max(maxBill, trendBean.getBillNum());
                amountList.add(new Entry(i, trendBean.getAmount(), trendBean.getDate()));
                billNumList.add(new Entry(i, trendBean.getBillNum(), trendBean.getDate()));
            }
            mTrendChart.getAxisLeft().setAxisMaximum(Math.max(6, (long) Math.ceil(maxAmount / 6) * 6));
            mTrendChart.getAxisRight().setAxisMaximum(Math.max(7, (long) Math.ceil(maxBill / 7) * 7)); // Math.max(7, (float) Math.ceil(maxBill))
        }
        LineDataSet amountSet, billNumSet;
        if (mTrendChart.getData() != null && mTrendChart.getData().getDataSetCount() == 2) {
            amountSet = (LineDataSet) mTrendChart.getData().getDataSetByIndex(0);
            amountSet.setValues(amountList);
            amountSet.notifyDataSetChanged();
            billNumSet = (LineDataSet) mTrendChart.getData().getDataSetByIndex(1);
            billNumSet.setValues(billNumList);
            billNumSet.notifyDataSetChanged();
            mTrendChart.getData().notifyDataChanged();
            mTrendChart.notifyDataSetChanged();
        } else {
            amountSet = new LineDataSet(amountList, "交易金额");
            amountSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            int amountColor = ContextCompat.getColor(requireContext(), R.color.color_5aaaf1);
            amountSet.setColor(amountColor);
            amountSet.setFillColor(amountColor);
            amountSet.setCircleColor(amountColor);
            billNumSet = new LineDataSet(billNumList, "订单量");
            billNumSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            int billNumColor = ContextCompat.getColor(requireContext(), R.color.color_f6874f);
            billNumSet.setColor(billNumColor);
            billNumSet.setFillColor(billNumColor);
            billNumSet.setCircleColor(billNumColor);
            LineData lineData = new LineData(amountSet, billNumSet);
            for (ILineDataSet lineDataSet : lineData.getDataSets()) {
                LineDataSet dataSet = (LineDataSet) lineDataSet;
                dataSet.setDrawCircleHole(false);
                dataSet.setDrawValues(false);
                dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                dataSet.setDrawFilled(true);
            }
            mTrendChart.setData(lineData);
            mTrendChart.invalidate();
        }
    }

    @Override
    public void updateVisitPlan(VisitResp resp) {
        mVisited.setText(String.valueOf(resp.getVisitRecordCount()));
        mValidVisit.setText(String.valueOf(resp.getActiveVisitRecordCount()));
        mRemainVisit.setText(String.valueOf(Math.max(resp.getVisitPlanCount() - resp.getActiveVisitPlanCount(), 0)));
        mPendingVisit.setText(String.valueOf(resp.getVisitPlanCount()));
    }

    @Override
    public void updateManagementShop(ManagementShopResp resp) {
        mManagementShop.setText(String.valueOf(resp.getShopNum()));
        mAddedMonth.setText(String.valueOf(resp.getShopNumIncr()));
        mWillFall.setText(String.valueOf(resp.getShopNumLost()));
    }

    @Override
    public void setMessageCount(String count) {
        UIUtils.setTextWithVisibility(mMessageCount, count);
    }
}
