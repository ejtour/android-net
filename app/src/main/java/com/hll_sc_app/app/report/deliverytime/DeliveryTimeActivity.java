package com.hll_sc_app.app.report.deliverytime;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeBean;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeResp;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.widget.report.PieMarker;
import com.hll_sc_app.widget.report.ReportEmptyView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.ViewCollections;

/**
 * 日销售汇总
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_DELIVERY_TIME)
public class DeliveryTimeActivity extends BaseLoadActivity implements IDeliveryTimeContract.IDeliveryTimeView {

    @BindViews({R.id.rdt_seven_chart, R.id.rdt_thirty_chart, R.id.rdt_ninety_chart})
    List<PieChart> mCharts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_delivery_time);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        DeliveryTimePresenter presenter = DeliveryTimePresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }

    private void initView() {
        ViewCollections.run(mCharts, (chart, index) -> {
            chart.setUsePercentValues(true);
            chart.getDescription().setEnabled(false);
            chart.setExtraOffsets(5, 5, 5, 5);
            chart.setDragDecelerationFrictionCoef(0.5f);
            chart.setRotationEnabled(true);
            chart.animateY(1400, Easing.EaseInOutQuad);
            //设置饼状图里的文字大小
            chart.setEntryLabelTextSize(0f);
            //不绘画中间
            chart.setDrawHoleEnabled(false);
            //设置统计维度显示
            Legend l = chart.getLegend();
            l.setDrawInside(false);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setWordWrapEnabled(true);
            l.setEnabled(true);
            chart.setMarker(new PieMarker(this, chart));
        });
    }

    @OnClick(R.id.rdt_detail)
    public void viewDetail() {
        RouterUtil.goToActivity(RouterConfig.REPORT_DELIVERY_TIME_DETAIL);
    }

    @Override
    public void setData(DeliveryTimeResp deliveryTimeResp) {
        for (int i = 0; i < mCharts.size(); i++) {
            PieChart chart = mCharts.get(i);
            DeliveryTimeBean bean = null;
            switch (i) {
                case 0:
                    bean = deliveryTimeResp.getNearly7Days();
                    break;
                case 1:
                    bean = deliveryTimeResp.getNearly30Days();
                    break;
                case 2:
                    bean = deliveryTimeResp.getNearly90Days();
                    break;
            }
            assert bean != null;
            if (bean.hasValue()) {
                chart.setVisibility(View.VISIBLE);
                List<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(bean.getOnTimeInspectionNum(), "要求时间内"));
                entries.add(new PieEntry(bean.getWithin15MinInspectionNum(), "差异15分钟内"));
                entries.add(new PieEntry(bean.getWithin30MinInspectionNum(), "差异30分钟内"));
                entries.add(new PieEntry(bean.getBeyond30MinInspectionNum(), "差异30分钟以上"));
                PieDataSet dataSet = new PieDataSet(entries, "");
                //颜色
                List<Integer> colors = Arrays.asList(ColorStr.CHART_COLOR_ARRAY);
                //设置
                dataSet.setColors(colors);
                //横线
                dataSet.setValueTextSize(0f);
                PieData data = new PieData(dataSet);
                chart.setData(data);
                chart.invalidate();
            } else {
                chart.setVisibility(View.GONE);
                ReportEmptyView emptyView = new ReportEmptyView(this);
                emptyView.setTip("您还没有配送及时率的统计数据");
                emptyView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(200)));
                ((LinearLayout) chart.getParent()).addView(emptyView);
            }
        }
    }
}
