package com.hll_sc_app.app.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.audit.AuditActivity;
import com.hll_sc_app.app.agreementprice.AgreementPriceActivity;
import com.hll_sc_app.app.menu.MenuActivity;
import com.hll_sc_app.app.menu.stratery.ReconcileMenu;
import com.hll_sc_app.app.menu.stratery.ReportMenu;
import com.hll_sc_app.app.message.MessageActivity;
import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.app.order.statistic.OrderStatisticActivity;
import com.hll_sc_app.app.setting.SettingActivity;
import com.hll_sc_app.app.web.WebActivity;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.CountlyMgr;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.common.SalesVolumeResp;
import com.hll_sc_app.bean.common.WeekSalesVolumeBean;
import com.hll_sc_app.bean.event.MessageEvent;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.web.FleaMarketParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IReload;
import com.hll_sc_app.widget.EasingTextView;
import com.hll_sc_app.widget.home.SalesVolumeMarker;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页 fragment
 *
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */
@Route(path = RouterConfig.ROOT_HOME_MAIN)
public class MainHomeFragment extends BaseLoadFragment implements IMainHomeContract.IMainHomeView, IReload, OnTabSelectListener {
    private static final String[] WEEK_ARRAY = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private static final String[] LABEL_ARRAY = {"今日", "本周", "本月"};
    @BindView(R.id.fmh_top_bg)
    ImageView mTopBg;
    @BindView(R.id.fmh_avatar)
    GlideImageView mAvatar;
    @BindView(R.id.fmh_name)
    TextView mName;
    @BindView(R.id.fmh_amount)
    EasingTextView mAmount;
    @BindView(R.id.fmh_amount_label)
    TextView mAmountLabel;
    @BindView(R.id.fmh_tab_layout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.fmh_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.fmh_order_count)
    EasingTextView mOrderCount;
    @BindView(R.id.fmh_order_count_label)
    TextView mOrderCountLabel;
    @BindView(R.id.fmh_customer_num)
    EasingTextView mCustomerNum;
    @BindView(R.id.fmh_warehouse_bill_num)
    EasingTextView mWarehouseBillNum;
    @BindView(R.id.fmh_warehouse_shop_num)
    EasingTextView mWarehouseShopNum;
    @BindView(R.id.fmh_warehouse_delivery_num)
    EasingTextView mWarehouseDeliveryNum;
    @BindView(R.id.fmh_warehouse_amount)
    EasingTextView mWarehouseAmount;
    @BindView(R.id.fmh_warehouse_group)
    ConstraintLayout mWarehouseGroup;
    @BindView(R.id.fmh_pending_receive)
    EasingTextView mPendingReceive;
    @BindView(R.id.fmh_pending_delivery)
    EasingTextView mPendingDelivery;
    @BindView(R.id.fmh_delivered)
    EasingTextView mDelivered;
    @BindView(R.id.fmh_pending_settle)
    EasingTextView mPendingSettle;
    @BindView(R.id.fmh_customer_service)
    EasingTextView mCustomerService;
    @BindView(R.id.fmh_driver)
    EasingTextView mDriver;
    @BindView(R.id.fmh_warehouse)
    EasingTextView mWarehouseIn;
    @BindView(R.id.fmh_finance)
    EasingTextView mFinance;
    @BindViews({R.id.fmh_warehouse_bill_num, R.id.fmh_warehouse_shop_num, R.id.fmh_warehouse_delivery_num,
            R.id.fmh_pending_receive, R.id.fmh_pending_delivery, R.id.fmh_delivered, R.id.fmh_pending_settle,
            R.id.fmh_customer_service, R.id.fmh_driver, R.id.fmh_warehouse, R.id.fmh_finance})
    List<EasingTextView> mCountViews;
    @BindView(R.id.fmh_chart_view)
    LineChart mChartView;
    @BindView(R.id.fmh_title_bar)
    View mTitleBar;
    @BindView(R.id.fmh_flea_market)
    ImageView mFleaMarketGroup;
    @BindView(R.id.fmh_scroll_view)
    NestedScrollView mScrollView;
    @BindView(R.id.fmh_message_count)
    TextView mMessageCount;
    private int mTitleBarHeight;
    private int mTopBgHeight;
    Unbinder unbinder;
    @IMainHomeContract.DateType
    private int mDateType = IMainHomeContract.DateType.TYPE_DAY;
    private IMainHomeContract.IMainHomePresenter mPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mTitleBarHeight = UIUtils.dip2px(64);
        mTopBgHeight = UIUtils.dip2px(220);
        EventBus.getDefault().register(this);
        initView();
        initData();
        return rootView;
    }

    private void initData() {
        mPresenter = MainHomePresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        showStatusBar();
        UserBean user = GreenDaoUtils.getUser();
        if (user != null) {
            mAvatar.setImageURL(user.getGroupLogoUrl());
            mName.setText(user.getEmployeeName());
        }
        ButterKnife.apply(mCountViews, (view, index) -> view.setProcessor(rawText -> {
            int end = rawText.indexOf("\n");
            if (end == 0) return rawText;
            SpannableString ss = new SpannableString(rawText);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_222222))
                    , 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(1.6f), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
        }));
        mWarehouseAmount.setProcessor(rawText -> {
            int end = rawText.indexOf("\n");
            if (end == 0) return rawText;
            SpannableString ss = new SpannableString(rawText);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_222222))
                    , 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(1.3f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(1.6f), 1, rawText.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(1.3f), rawText.indexOf("."), end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
        });
        updateTodo(null);
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
                if (mTopBg == null) return;
                mTopBg.setTranslationY(scrollY <= mTopBgHeight ? -scrollY : -mTopBgHeight);
            }
        });
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                if (mTopBg == null) return;
                mTopBg.setScaleX(1 + percent * 0.7f);
                mTopBg.setScaleY(1 + percent * 0.7f);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.start();
            }
        });
        initTab();
        mTabLayout.setOnTabSelectListener(this);
        if (BuildConfig.isOdm) {
            mFleaMarketGroup.setVisibility(View.GONE);
        }
        initChart();
    }

    private void initTab() {
        ArrayList<CustomTabEntity> arrayList = new ArrayList<>();
        for (String bean : LABEL_ARRAY) {
            arrayList.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return bean;
                }

                @Override
                public int getTabSelectedIcon() {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon() {
                    return 0;
                }
            });
        }
        mTabLayout.setTabData(arrayList);
    }

    private void showStatusBar() {
        StatusBarUtil.fitSystemWindowsWithMarginTop(mRefreshLayout);
        StatusBarUtil.fitSystemWindowsWithPaddingTop(mTitleBar);
    }

    private void initChart() {
        Legend legend = mChartView.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_999999));
        legend.setXEntrySpace(10);
        legend.setTextSize(10);

        mChartView.getDescription().setEnabled(false);
        mChartView.setNoDataText("无数据");
        mChartView.setScaleEnabled(false);
        mChartView.setPinchZoom(false);
        mChartView.setExtraOffsets(0, 15, 0, 20);

        mChartView.setMarker(new SalesVolumeMarker(requireContext(), mChartView));

        XAxis xAxis = mChartView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceMin(0.2f);
        xAxis.setSpaceMax(0.2f);
        xAxis.setTextSize(9);
        xAxis.setYOffset(10);
        xAxis.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_666666));
        xAxis.enableGridDashedLine(10, 1000, 0);
        xAxis.setGridLineWidth(1);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return WEEK_ARRAY[(int) value];
            }
        });

        YAxis axisLeft = mChartView.getAxisLeft();
        axisLeft.setDrawAxisLine(false);
        axisLeft.setLabelCount(5, true);
        axisLeft.setGranularity(1);
        axisLeft.setAxisMinimum(0);
        axisLeft.setTextSize(10);
        axisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return CommonUtils.formatNumber(value);
            }
        });
        axisLeft.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_666666));
        axisLeft.setGridColor(ContextCompat.getColor(requireContext(), R.color.color_dddddd));
        axisLeft.enableGridDashedLine(4, 4, 0);

        mChartView.getAxisRight().setEnabled(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void handleMessageEvent(MessageEvent event) {
        UIUtils.setTextWithVisibility(mMessageCount, event.getCount(), true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.start();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isHidden()) {
            mPresenter.start();
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void updateSalesVolume(SalesVolumeResp resp) {
        updateSummary(resp);
        updateWarehouse(resp);
        updateTodo(resp);
    }

    private void updateTodo(SalesVolumeResp resp) {
        mPendingReceive.easingText(resp == null ? 0 : resp.getUnReceiveBillNum(), EasingTextView.INTEGER);
        mPendingDelivery.easingText(resp == null ? 0 : resp.getUnDeliveryBillNum(), EasingTextView.INTEGER);
        mDelivered.easingText(resp == null ? 0 : resp.getDeliveryBillNum(), EasingTextView.INTEGER);
        mPendingSettle.easingText(resp == null ? 0 : resp.getUnSettlementBillNum(), EasingTextView.INTEGER);
        mCustomerService.easingText(resp == null ? 0 : resp.getCustomServicedRefundNum(), EasingTextView.INTEGER);
        mDriver.easingText(resp == null ? 0 : resp.getDrivedRefundNum(), EasingTextView.INTEGER);
        mWarehouseIn.easingText(resp == null ? 0 : resp.getWareHousedRefundNum(), EasingTextView.INTEGER);
        mFinance.easingText(resp == null ? 0 : resp.getFinanceReviewRefundNum(), EasingTextView.INTEGER);
    }

    private void updateWarehouse(SalesVolumeResp resp) {
        if (resp.isWareHouse()) {
            mWarehouseGroup.setVisibility(View.VISIBLE);
            mWarehouseBillNum.easingText(resp.getWareHouseBillNum(), EasingTextView.INTEGER);
            mWarehouseShopNum.easingText(resp.getWareHouseShopNum(), EasingTextView.INTEGER);
            mWarehouseDeliveryNum.easingText(resp.getWareHouseDeliveryGoodsNum(), EasingTextView.FLOAT);
            mWarehouseAmount.easingText(resp.getWareHouseDeliveryGoodsAmount(), EasingTextView.MONEY);
        } else mWarehouseGroup.setVisibility(View.GONE);
    }

    private void updateSummary(SalesVolumeResp resp) {
        mAmount.easingText(resp.getSales(), EasingTextView.MONEY);
        mOrderCount.easingText(resp.getBillNum(), EasingTextView.INTEGER);
        mCustomerNum.easingText(resp.getPurchaserShopNum(), EasingTextView.INTEGER);
    }

    private SpannableString processText(String value, String label) {
        SpannableString ss = new SpannableString(String.format("%s\n%s", value, label));
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_222222))
                , 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(1.6f), 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @IMainHomeContract.DateType
    @Override
    public int getDateType() {
        return mDateType;
    }

    @Override
    public void updateChartData(List<WeekSalesVolumeBean> list) {
        mChartView.highlightValue(null);
        List<Entry> lastList = new ArrayList<>();
        List<Entry> curList = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            float max = 0;
            for (int i = 0; i < list.size(); i++) {
                WeekSalesVolumeBean bean = list.get(i);
                max = Math.max(max, bean.getLastWeekTotalAmount());
                max = Math.max(max, bean.getTotalAmount());
                lastList.add(new Entry(i, bean.getLastWeekTotalAmount(), bean.getDayOfWeek()));
                curList.add(new Entry(i, bean.getTotalAmount(), bean.getDayOfWeek()));
            }
            int step = max % 4 == 0 ? 1 : 0;
            mChartView.getAxisLeft().setAxisMaximum(Math.max(4, (long) (Math.ceil(max / 4) + step) * 4));
        }
        LineDataSet lastSet, curSet;
        if (mChartView.getData() != null && mChartView.getData().getDataSetCount() == 2) {
            lastSet = (LineDataSet) mChartView.getData().getDataSetByIndex(0);
            lastSet.setValues(lastList);
            lastSet.notifyDataSetChanged();
            curSet = (LineDataSet) mChartView.getData().getDataSetByIndex(1);
            curSet.setValues(curList);
            curSet.notifyDataSetChanged();
            mChartView.getData().notifyDataChanged();
            mChartView.notifyDataSetChanged();
        } else {
            lastSet = new LineDataSet(lastList, "上周");
            int lastColor = ContextCompat.getColor(requireContext(), R.color.color_597ef7);
            lastSet.setColor(lastColor);
            lastSet.setFillColor(lastColor);
            lastSet.setCircleColor(lastColor);
            curSet = new LineDataSet(curList, "本周");
            int curColor = ContextCompat.getColor(requireContext(), R.color.color_36cfc9);
            curSet.setColor(curColor);
            curSet.setFillColor(curColor);
            curSet.setCircleColor(curColor);
            LineData lineData = new LineData(lastSet, curSet);
            for (ILineDataSet lineDataSet : lineData.getDataSets()) {
                LineDataSet dataSet = (LineDataSet) lineDataSet;
                dataSet.setDrawValues(false);
                dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                dataSet.setDrawFilled(true);
                dataSet.setLineWidth(2);
                dataSet.setDrawCircleHole(false);
            }
            mChartView.setData(lineData);
            mChartView.invalidate();
        }
    }

    @OnClick({R.id.fmh_pending_receive, R.id.fmh_pending_delivery, R.id.fmh_delivered, R.id.fmh_pending_settle})
    public void gotoOrderManager(View view) {
        OrderType type;
        switch (view.getId()) {
            case R.id.fmh_pending_receive:
                type = OrderType.PENDING_RECEIVE;
                break;
            case R.id.fmh_pending_delivery:
                type = OrderType.PENDING_DELIVER;
                break;
            case R.id.fmh_delivered:
                type = OrderType.DELIVERED;
                break;
            case R.id.fmh_pending_settle:
                type = OrderType.PENDING_SETTLE;
                break;
            default:
                return;
        }
        EventBus.getDefault().postSticky(new OrderEvent(OrderEvent.SELECT_STATUS, type.getStatus()));
    }

    @OnClick({R.id.fmh_customer_service, R.id.fmh_driver, R.id.fmh_warehouse, R.id.fmh_finance})
    public void gotoAfterSales(View view) {
        int position;
        switch (view.getId()) {
            case R.id.fmh_customer_service:
                position = 1;
                break;
            case R.id.fmh_driver:
                position = 2;
                break;
            case R.id.fmh_warehouse:
                position = 3;
                break;
            case R.id.fmh_finance:
                position = 4;
                break;
            default:
                return;
        }
        AuditActivity.start(position);
    }

    @OnClick({R.id.fmh_entry_quote, R.id.fmh_entry_reconciliation, R.id.fmh_entry_export, R.id.fmh_entry_report})
    public void shortcut(View view) {
        switch (view.getId()) {
            case R.id.fmh_entry_quote:
                AgreementPriceActivity.start(this);
                break;
            case R.id.fmh_entry_reconciliation:
                MenuActivity.start(ReconcileMenu.class.getSimpleName());
                break;
            case R.id.fmh_entry_export:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_EXPORT);
                break;
            case R.id.fmh_entry_report:
                MenuActivity.start(ReportMenu.class.getSimpleName());
                break;
        }
    }

    @OnClick(R.id.fmh_flea_market)
    public void fleaMarket() {
        CountlyMgr.recordView("二手市场");
        String params = Base64.encodeToString(JsonUtil.toJson(new FleaMarketParam()).getBytes(), Base64.DEFAULT);
        WebActivity.start(null, HttpConfig.getWebHost() + "/?sourceData=" + params);
    }

    @OnClick(R.id.fmh_invite_code)
    public void inviteCode() {
        RouterUtil.goToActivity(RouterConfig.INFO_INVITE_CODE);
    }

    @OnClick(R.id.fmh_message)
    public void message() {
        MessageActivity.start();
    }

    @OnClick(R.id.fmh_setting)
    public void setting() {
        SettingActivity.start();
    }

    @OnClick(R.id.fmh_customer_statistic)
    public void customerStatistic() {
        OrderStatisticActivity.start(mTabLayout.getCurrentTab());
    }

    @Override
    public void reload() {
        mPresenter.start();
    }

    @Override
    public void onTabSelect(int position) {
        mDateType = position;
        mAmountLabel.setText(String.format("%s订货金额(元)", LABEL_ARRAY[position]));
        mOrderCountLabel.setText(String.format("%s订单数", LABEL_ARRAY[position]));
        mPresenter.start();
    }

    @Override
    public void onTabReselect(int position) {
        // no-op
    }
}