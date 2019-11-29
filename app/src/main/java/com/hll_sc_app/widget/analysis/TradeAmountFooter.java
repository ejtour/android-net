package com.hll_sc_app.widget.analysis;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/15
 */

public class TradeAmountFooter extends ConstraintLayout {
    @BindView(R.id.taf_chart)
    CombinedChart mChart;
    @BindView(R.id.taf_tip_1)
    TextView mTip1;
    @BindView(R.id.taf_tip_2)
    TextView mTip2;
    @BindView(R.id.taf_tip_3)
    TextView mTip3;
    @BindView(R.id.taf_tip_4)
    TextView mTip4;
    @BindView(R.id.taf_tip_5)
    TextView mTip5;
    private List<String> mAxisList = new ArrayList<>();

    public TradeAmountFooter(Context context) {
        this(context, null);
    }

    public TradeAmountFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TradeAmountFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View v = View.inflate(context, R.layout.view_trade_amount_footer, this);
        ButterKnife.bind(this, v);
        setPadding(0, UIUtils.dip2px(20), 0, UIUtils.dip2px(20));
        initChart();
    }

    private void initChart() {
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE});
        mChart.getDescription().setEnabled(false);
        mChart.setNoDataText("无数据");
        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(false);
        mChart.setExtraOffsets(10, 0, 10, 0);

        Legend legend = mChart.getLegend();
        legend.setTextSize(10);
        legend.setXEntrySpace(20);
        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.color_666666));
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));
        xAxis.setLabelRotationAngle(-20);
        xAxis.setGranularity(1);
        xAxis.setLabelCount(5);
        xAxis.setSpaceMin(0.5f);
        xAxis.setSpaceMax(0.5f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return value < 0 || value >= mAxisList.size() ? "" : mAxisList.get((int) value);
            }
        });

        YAxis axisLeft = mChart.getAxisLeft();
        axisLeft.setDrawAxisLine(false);
        axisLeft.setAxisMinimum(0);
        axisLeft.setGridColor(ContextCompat.getColor(getContext(), R.color.color_dddddd));
        axisLeft.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));
        axisLeft.setTextSize(10);

        YAxis axisRight = mChart.getAxisRight();
        axisRight.setDrawAxisLine(false);
        axisRight.setGridColor(ContextCompat.getColor(getContext(), R.color.color_dddddd));
        axisRight.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));
        axisRight.setTextSize(10);
        axisRight.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.format("%.2f", value) + "%";
            }
        });
    }

    public void setData(CharSequence... s) {
        mTip1.setText(s[0]);
        mTip2.setText(s[1]);
        mTip3.setText(s[2]);
        mTip4.setText(s[3]);
        mTip5.setText(s[4]);
    }

    public void setData(List<AnalysisBean> list, int timeType) {
        List<BarEntry> amountList = new ArrayList<>();
        List<Entry> rateList = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            mAxisList.clear();
            float maxAmount = 0;
            float maxRate = 0;
            for (int i = 0; i < list.size(); i++) {
                AnalysisBean bean = list.get(i);
                maxAmount = Math.max(maxAmount, bean.getValidTradeAmount());
                maxRate = Math.max(maxRate, bean.getRelativeRatio());
                String dateRange = bean.getDateRange(timeType);
                mAxisList.add(dateRange);
                amountList.add(new BarEntry(i, bean.getValidTradeAmount(), dateRange));
                rateList.add(new Entry(i, bean.getRelativeRatio(), dateRange));
            }
            mChart.getAxisLeft().setAxisMaximum((float) Math.ceil(maxAmount));
            mChart.getAxisRight().setAxisMaximum((float) Math.ceil(maxRate));
        }
        BarDataSet amountSet;
        LineDataSet rateSet;
        if (mChart.getData() != null) {
            LineData line = mChart.getData().getLineData();
            rateSet = (LineDataSet) line.getDataSetByIndex(0);
            rateSet.setValues(rateList);
            rateSet.notifyDataSetChanged();
            line.notifyDataChanged();
            BarData bar = mChart.getData().getBarData();
            amountSet = (BarDataSet) bar.getDataSetByIndex(0);
            amountSet.setValues(amountList);
            amountSet.notifyDataSetChanged();
            bar.notifyDataChanged();
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            CombinedData data = new CombinedData();
            amountSet = new BarDataSet(amountList, "交易金额（万）");
            amountSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            amountSet.setForm(Legend.LegendForm.SQUARE);
            amountSet.setColor(Color.parseColor("#69c0ff"));
            amountSet.setDrawValues(false);
            BarData bar = new BarData(amountSet);
            bar.setBarWidth(0.3f);
            data.setData(bar);

            rateSet = new LineDataSet(rateList, "环比增长");
            rateSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            rateSet.setForm(Legend.LegendForm.LINE);
            rateSet.setFormLineWidth(2);
            rateSet.setFormSize(15);
            rateSet.setColor(Color.parseColor("#ff7a45"));
            rateSet.setLineWidth(2);
            rateSet.setDrawValues(false);
            rateSet.setMode(LineDataSet.Mode.LINEAR);
            rateSet.setDrawCircles(false);
            LineData line = new LineData(rateSet);
            data.setData(line);
            mChart.setData(data);
        }
        mChart.invalidate();
    }
}
