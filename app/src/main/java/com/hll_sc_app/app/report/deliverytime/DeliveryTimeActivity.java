package com.hll_sc_app.app.report.deliverytime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeBean;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeNearlyBean;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeResp;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.report.PieMarker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 日销售汇总
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_DELIVERY_TIME)
public class DeliveryTimeActivity extends BaseLoadActivity implements DeliveryTimeContract.IDeliveryTimeView {

    @BindView(R.id.nearly_seven_pie)
    PieChart nearLySevenPieChartView;
    @BindView(R.id.nearly_thirty_pie)
    PieChart nearLyThirtyPieChartView;
    @BindView(R.id.nearly_ninety_pie)
    PieChart nearLyNinetyPieChartView;
    @BindView(R.id.nearly_seven_layout)
    LinearLayout nearlySevenLayout;
    @BindView(R.id.nearly_thirty_layout)
    LinearLayout nearlyThirtyLayout;
    @BindView(R.id.nearly_ninety_layout)
    LinearLayout nearlyNinetyLayout;

    private EmptyView emptyView;

    private Map<String, PieChart> pieChartMap = new HashMap<>(3);
    private Map<String, LinearLayout> layoutMap = new HashMap<>(3);
    private DeliveryTimePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_delivery_time_pie);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = DeliveryTimePresenter.newInstance();
        initView();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        pieChartMap.put("0", nearLySevenPieChartView);
        layoutMap.put("0", nearlySevenLayout);
        pieChartMap.put("1", nearLyThirtyPieChartView);
        layoutMap.put("1", nearlyThirtyLayout);
        pieChartMap.put("2", nearLyNinetyPieChartView);
        layoutMap.put("2", nearlyNinetyLayout);
        emptyView = EmptyView.newBuilder(this).setImage(R.drawable.ic_char_empty).setTips("您还没有配送及时率的统计数据").create();
        for (PieChart pieChart : pieChartMap.values()) {
            //设置饼状图
            //画统计图
            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setExtraOffsets(5, 5, 5, 5);
            pieChart.setDragDecelerationFrictionCoef(0.5f);
            pieChart.setRotationEnabled(true);
            pieChart.animateY(1400, Easing.EaseInOutQuad);
            //设置饼状图里的文字大小
            pieChart.setEntryLabelTextSize(0f);
            //不绘画中间
            pieChart.setDrawHoleEnabled(false);
            //设置统计维度显示
            Legend l = pieChart.getLegend();
            l.setDrawInside(false);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setWordWrapEnabled(true);
            l.setEnabled(true);
            pieChart.setMarker(new PieMarker(this, pieChart));
        }
    }

    @OnClick({R.id.img_back, R.id.delivery_time_detail_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.delivery_time_detail_btn:
                RouterUtil.goToActivity(RouterConfig.REPORT_DELIVERY_TIME_DETAIL);
                break;
            default:
                break;
        }
    }

    @Override
    public void showDeliveryTimePieCharts(DeliveryTimeResp deliveryTimeResp) {
        for (Map.Entry<String, PieChart> pieChartEntry : pieChartMap.entrySet()) {
            String key = pieChartEntry.getKey();
            PieChart pieChart = pieChartEntry.getValue();
            DeliveryTimeNearlyBean deliveryTimeNearlyBean = null;
            switch (key) {
                case "0":
                    deliveryTimeNearlyBean = deliveryTimeResp.getNearly7Days();
                    break;
                case "1":
                    deliveryTimeNearlyBean = deliveryTimeResp.getNearly30Days();
                    break;
                case "2":
                    deliveryTimeNearlyBean = deliveryTimeResp.getNearly90Days();
                    break;
                default:
                    break;
            }
            if (deliveryTimeNearlyBean != null && !isEmptyDeliveryTime(deliveryTimeNearlyBean)) {
                pieChart.setVisibility(View.VISIBLE);
                //饼状图
                ArrayList<PieEntry> entries = new ArrayList<>();
                DeliveryTimeBean deliveryTimeBean = handler(deliveryTimeNearlyBean);
                entries.add(new PieEntry(deliveryTimeBean.getOnTimeInspectionNumRatio(), deliveryTimeBean.getOnTimeInspectionNumDesc()));
                entries.add(new PieEntry(deliveryTimeBean.getWithin15MinInspectionNumRatio(), deliveryTimeBean.getWithin15MinInspectionNumDesc()));
                entries.add(new PieEntry(deliveryTimeBean.getWithin30MinInspectionNumRatio(), deliveryTimeBean.getWithin30MinInspectionNumDesc()));
                entries.add(new PieEntry(deliveryTimeBean.getBeyond30MinInspectionNumRatio(), deliveryTimeBean.getBeyond30MinInspectionNumDesc()));

                if (entries.size() > 0) {
                    PieDataSet dataSet = new PieDataSet(entries, "");
                    //颜色
                    List<Integer> colors = Arrays.asList(ColorStr.CHART_COLOR_ARRAY);
                    //设置
                    dataSet.setColors(colors);
                    //横线
                    dataSet.setValueTextSize(0f);
                    PieData data = new PieData(dataSet);
                    pieChart.setData(data);
                    pieChart.invalidate();
                }
            } else {
                LinearLayout linearLayout = layoutMap.get(key);
                pieChart.setVisibility(View.GONE);
                LinearLayout parentLayout = (LinearLayout) emptyView.getParent();
                if (null != parentLayout) {
                    parentLayout.removeAllViews();
                }
                linearLayout.addView(emptyView);
            }
        }
    }

    /**
     * 判断是否有配送及时率的数据
     *
     * @param deliveryTimeNearlyBean
     * @return
     */
    private boolean isEmptyDeliveryTime(DeliveryTimeNearlyBean deliveryTimeNearlyBean) {

        return (deliveryTimeNearlyBean.getOnTimeInspectionNum() + deliveryTimeNearlyBean.getBeyond30MinInspectionNum()
                + deliveryTimeNearlyBean.getWithin15MinInspectionNum() + deliveryTimeNearlyBean.getWithin30MinInspectionNum()) == 0;
    }

    /**
     * 处理配送及时率
     *
     * @param item
     * @return
     */
    private DeliveryTimeBean handler(DeliveryTimeNearlyBean item) {
        DeliveryTimeBean deliveryTimeBean = new DeliveryTimeBean();
        long totalOrderNum = item.getOnTimeInspectionNum() + item.getBeyond30MinInspectionNum()
                + item.getWithin15MinInspectionNum() + item.getWithin30MinInspectionNum();
        if (totalOrderNum == 0) {
            deliveryTimeBean.setBeyond30MinInspectionNumRatio(0.00f);
            deliveryTimeBean.setOnTimeInspectionNumRatio(0.00f);
            deliveryTimeBean.setWithin15MinInspectionNumRatio(0.00f);
            deliveryTimeBean.setWithin30MinInspectionNumRatio(0.00f);
        } else {
            deliveryTimeBean.setBeyond30MinInspectionNumRatio(new BigDecimal(item.getBeyond30MinInspectionNum()).divide(new BigDecimal(totalOrderNum), 2, BigDecimal.ROUND_HALF_UP).floatValue());
            deliveryTimeBean.setOnTimeInspectionNumRatio(new BigDecimal(item.getOnTimeInspectionNum()).divide(new BigDecimal(totalOrderNum), 2, BigDecimal.ROUND_HALF_UP).floatValue());
            deliveryTimeBean.setWithin15MinInspectionNumRatio(new BigDecimal(item.getWithin15MinInspectionNum()).divide(new BigDecimal(totalOrderNum), 2, BigDecimal.ROUND_HALF_UP).floatValue());
            deliveryTimeBean.setWithin30MinInspectionNumRatio(new BigDecimal(item.getWithin30MinInspectionNum()).divide(new BigDecimal(totalOrderNum), 2, BigDecimal.ROUND_HALF_UP).floatValue());
        }
        deliveryTimeBean.setBeyond30MinInspectionNumDesc("差异30分钟以上");
        deliveryTimeBean.setOnTimeInspectionNumDesc("要求时间内");
        deliveryTimeBean.setWithin15MinInspectionNumDesc("差异15分钟内");
        deliveryTimeBean.setWithin30MinInspectionNumDesc("差异30分钟内");
        return deliveryTimeBean;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

}
