package com.hll_sc_app.app.report.deliveryTime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.RefundReasonStaticsResp;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeBean;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeNearlyItem;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeResp;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmount;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * 日销售汇总
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_DELIVERY_TIME_AGGREGATION)
public class DeliveryTimeAggregationActivity extends BaseLoadActivity implements DeliveryTimeAggregationContract.IDeliveryTimeAggregationView {

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
    private int mX;
    private int mY;

    private EmptyView emptyView;

    private  Map<String,PieChart> pieChartMap = new HashMap<>(3);
    private  Map<String,LinearLayout> layoutMap = new HashMap<>(3);
    private DeliveryTimeAggregationPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_delivery_time_pie);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = DeliveryTimeAggregationPresenter.newInstance();
        initView();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView(){
        pieChartMap.put("0",nearLySevenPieChartView);
        layoutMap.put("0",nearlySevenLayout);
        pieChartMap.put("1",nearLyThirtyPieChartView);
        layoutMap.put("1",nearlyThirtyLayout);
        pieChartMap.put("2",nearLyNinetyPieChartView);
        layoutMap.put("2",nearlyNinetyLayout);
        emptyView = EmptyView.newBuilder(this).setImage(R.drawable.ic_char_empty).setTips("您还没有配送及时率的统计数据").create();
        for(PieChart pieChart:pieChartMap.values()){
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

            //给每个饼图添加事件
            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    PieEntry pData = (PieEntry) e;
                    String toast = pData.getLabel() + ":" + new DecimalFormat("#.##").format(pData.getY() * 100) + "%";
                    Toast mToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
                    mToast.setText(toast);
//                    mToast.setGravity(Gravity.TOP, 0, UIUtils.dip2px(200));
                    mToast.setGravity(Gravity.TOP | Gravity.LEFT, mX , mY);
                    mToast.show();
                }
                @Override
                public void onNothingSelected() {}
            });
        }
    }

    @OnTouch({R.id.nearly_seven_pie,R.id.nearly_thirty_pie,R.id.nearly_ninety_pie})
    public boolean onTouch(MotionEvent event) {
        mX = (int) event.getRawX();
        mY = (int) event.getRawY();
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.img_back,R.id.delivery_time_detail_btn})
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
            DeliveryTimeNearlyItem deliveryTimeNearlyItem = null;
            switch (key){
                case "0":
                    deliveryTimeNearlyItem = deliveryTimeResp.getNearly7Days();
                    break;
                case "1":
                    deliveryTimeNearlyItem = deliveryTimeResp.getNearly30Days();
                    break;
                case "2":
                    deliveryTimeNearlyItem = deliveryTimeResp.getNearly90Days();
                    break;
                default:
                    break;
            }
            if (!isEmptyDeliveryTime(deliveryTimeNearlyItem)) {
                pieChart.setVisibility(View.VISIBLE);
                //饼状图
                ArrayList<PieEntry> entries = new ArrayList<>();
                DeliveryTimeBean deliveryTimeBean = handler(deliveryTimeNearlyItem);
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
            }else {
                LinearLayout linearLayout = layoutMap.get(key);
                pieChart.setVisibility(View.GONE);
                LinearLayout parentLayout = (LinearLayout) emptyView.getParent();
                if(null !=parentLayout){
                   parentLayout.removeAllViews();
                }
                linearLayout.addView(emptyView);
            }
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    /**
     * 处理配送及时率
     * @param item
     * @return
     */
    private DeliveryTimeBean handler(DeliveryTimeNearlyItem item){
        DeliveryTimeBean deliveryTimeBean = new DeliveryTimeBean();
        long totalOrderNum = item.getOnTimeInspectionNum()+item.getBeyond30MinInspectionNum()
                  +item.getWithin15MinInspectionNum()+item.getWithin30MinInspectionNum();
        if(totalOrderNum==0){
            deliveryTimeBean.setBeyond30MinInspectionNumRatio(0.00f);
            deliveryTimeBean.setOnTimeInspectionNumRatio(0.00f);
            deliveryTimeBean.setWithin15MinInspectionNumRatio(0.00f);
            deliveryTimeBean.setWithin30MinInspectionNumRatio(0.00f);
        }else {
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

    /**
     * 判断是否有配送及时率的数据
     * @param deliveryTimeNearlyItem
     * @return
     */
    private boolean isEmptyDeliveryTime(DeliveryTimeNearlyItem deliveryTimeNearlyItem){

        return  (deliveryTimeNearlyItem.getOnTimeInspectionNum()+deliveryTimeNearlyItem.getBeyond30MinInspectionNum()
                +deliveryTimeNearlyItem.getWithin15MinInspectionNum()+deliveryTimeNearlyItem.getWithin30MinInspectionNum())==0;
    }
}
