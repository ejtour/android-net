package com.hll_sc_app.app.analysis.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hll_sc_app.R;
import com.hll_sc_app.app.analysis.BaseAnalysisFragment;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisDataBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.analysis.OrderMarker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class OrderAnalysisFragment extends BaseAnalysisFragment {
    @BindView(R.id.foa_list_view)
    RecyclerView mListView;
    @BindView(R.id.foa_chart)
    CombinedChart mChart;
    @BindView(R.id.foa_tip_1)
    TextView mTip1;
    @BindView(R.id.foa_tip_2)
    TextView mTip2;
    @BindView(R.id.foa_tip_3)
    TextView mTip3;
    @BindView(R.id.foa_tip_4)
    TextView mTip4;
    @BindView(R.id.foa_tip_5)
    TextView mTip5;
    @BindView(R.id.foa_tip_6)
    TextView mTip6;
    @BindView(R.id.foa_tip_7)
    TextView mTip7;
    Unbinder unbinder;
    private OrderAnalysisAdapter mAdapter;
    private List<String> mAxisLabels = new ArrayList<>();

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_analysis, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mAdapter = new OrderAnalysisAdapter();
        mListView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        mListView.setAdapter(mAdapter);
        initChart();
    }

    private void initChart() {
        mChart.getDescription().setEnabled(false);
        mChart.setNoDataText("无数据");
        mChart.setScaleEnabled(false);
        mChart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mChart.setPinchZoom(false);
        mChart.setExtraOffsets(10, 0, 10, 0);

        mChart.setMarker(new OrderMarker(requireContext(), mChart));

        Legend legend = mChart.getLegend();
        legend.setTextSize(10);
        legend.setXEntrySpace(10);
        legend.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_666666));
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(5);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_222222));
        xAxis.setLabelRotationAngle(-20);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return value < 0 || value >= mAxisLabels.size() ? "" : mAxisLabels.get((int) value);
            }
        });

        YAxis axisLeft = mChart.getAxisLeft();
        axisLeft.setDrawAxisLine(false);
        axisLeft.setAxisMinimum(0);
        axisLeft.enableGridDashedLine(4, 4, 0);
        axisLeft.setGridColor(ContextCompat.getColor(requireContext(), R.color.color_dddddd));
        axisLeft.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_222222));
        axisLeft.setTextSize(10);
        axisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return CommonUtils.formatNumber(value);
            }
        });

        YAxis axisRight = mChart.getAxisRight();
        axisRight.setDrawAxisLine(false);
        axisRight.setAxisMinimum(0);
        axisRight.enableGridDashedLine(4, 4, 0);
        axisRight.setGridColor(ContextCompat.getColor(requireContext(), R.color.color_dddddd));
        axisRight.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_222222));
        axisRight.setTextSize(10);
        axisRight.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return CommonUtils.formatNumber(value);
            }
        });
    }

    @Override
    protected void initData() {
        if (mAnalysisEvent != null) {
            AnalysisResp analysisResp = mAnalysisEvent.getAnalysisResp();
            if (analysisResp != null) {
                List<AnalysisBean> records = analysisResp.getRecords();
                mAdapter.setNewData(records, mAnalysisEvent.getTimeType());
                setChartData(records);
                if (!CommonUtils.isEmpty(records) && analysisResp.getRecords().size() > 1) {
                    String label = mAnalysisEvent.getTimeType() == 2 ? "周" : "月";
                    AnalysisBean bean = records.get(records.size() - 1);
                    AnalysisDataBean analysisData = analysisResp.getAnalysisData();
                    if (analysisData != null) {
                        mTip1.setText(handleTip1(label, bean.getShopNum(), analysisData.getDiffShopNum(), analysisData.getDiffShopNumRate()));
                        mTip2.setText(handleTip2(label, analysisData.getAverageShopTradeDiffAmount(), analysisData.getAverageShopTradeDiffAmountRate()));
                        mTip3.setText(handleTip3(label, bean.getValidOrderNum(), analysisData.getDiffOrderNum(), analysisData.getOrderNumRate()));
                        mTip4.setText(handleTip4(label, analysisData.getDailyDiffValidTradeAmount(), analysisData.getDailyDiffValidTradeAmountRate()));
                        mTip5.setText(handleTip5Or6(label, "高", analysisData.getMaxValidOrderNumTime(), analysisData.getMaxValidOrderNum()));
                        mTip6.setText(handleTip5Or6(label, "低", analysisData.getMinValidOrderNumTime(), analysisData.getMinValidOrderNum()));
                        mTip7.setText(handleTip7(label, analysisData.getDailyValidOrderNum()));
                    }
                }
            }
        }
    }

    private void setChartData(List<AnalysisBean> list) {
        List<BarEntry> shopList = new ArrayList<>();
        List<BarEntry> orderList = new ArrayList<>();
        List<Entry> priceList = new ArrayList<>();
        List<Entry> avePriceList = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            mAxisLabels.clear();
            for (int i = 0; i < list.size(); i++) {
                AnalysisBean bean = list.get(i);
                String dateRange = bean.getDateRange(mAnalysisEvent.getTimeType());
                mAxisLabels.add(dateRange);
                shopList.add(new BarEntry(i, bean.getShopNum(), dateRange));
                orderList.add(new BarEntry(i, bean.getValidOrderNum(), dateRange));
                priceList.add(new Entry(i + 0.5f, bean.getAverageShopTradeAmount(), dateRange));
                avePriceList.add(new Entry(i + 0.5f, bean.getAverageTradeAmount(), dateRange));
            }
        }
        BarDataSet shopSet = new BarDataSet(shopList, "买家门店数");
        shopSet.setColor(Color.parseColor("#95DE64"));
        BarDataSet orderSet = new BarDataSet(orderList, "有效订单数");
        orderSet.setColor(Color.parseColor("#5CAAF0"));
        BarData bar = new BarData(shopSet, orderSet);
        bar.setBarWidth(0.2f);
        bar.groupBars(0, 0.5f, 0.05f);
        LineDataSet priceSet = new LineDataSet(priceList, "客单价（元）");
        priceSet.setColor(Color.parseColor("#FF7A45"));
        LineDataSet avePriceSet = new LineDataSet(avePriceList, "单均（元）");
        avePriceSet.setColor(Color.parseColor("#B37FEB"));
        LineData line = new LineData(priceSet, avePriceSet);
        for (ILineDataSet dataSet : line.getDataSets()) {
            LineDataSet lineDataSet = (LineDataSet) dataSet;
            lineDataSet.setForm(Legend.LegendForm.LINE);
            lineDataSet.setFormSize(15);
            lineDataSet.setFormLineWidth(2);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setLineWidth(2);
            lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        }
        CombinedData data = new CombinedData();
        data.setData(bar);
        data.setData(line);
        data.setDrawValues(false);
        mChart.setData(data);
        mChart.invalidate();
    }

    private CharSequence handleTip1(String timeLabel, int num, int diff, String rate) {
        boolean up = diff >= 0;
        String tip = String.format("本%s买家门店数%s家，比上%s%s%s家，%s幅度%s",
                timeLabel, num, timeLabel, up ? "增加" : "降低",
                Math.abs(diff), up ? "升高" : "降低", absRate(rate));
        SpannableString ss = new SpannableString(tip);
        int color = ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.indexOf("数") + 1, tip.indexOf("家，"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.indexOf("上") + 4, tip.lastIndexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.lastIndexOf("度") + 1, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip2(String timeLabel, double diff, String rate) {
        boolean up = diff >= 0;
        String tip = String.format("本%s客单价呈%s趋势，比上%s%s%s元，%s幅度为%s", timeLabel,
                up ? "升高" : "下降", timeLabel, up ? "增加" : "降低", CommonUtils.formatMoney(Math.abs(diff)),
                up ? "升高" : "下降", absRate(rate));
        int color = ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2);
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(color), tip.indexOf("，") + 6, tip.indexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.lastIndexOf("为") + 1, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip3(String timeLabel, int orderNum, int diff, String rate) {
        boolean up = diff >= 0;
        String tip = String.format("本%s订单总量%s笔，比上%s%s%s笔，%s幅度%s", timeLabel,
                CommonUtils.formatNum(orderNum), timeLabel, up ? "增加" : "降低",
                CommonUtils.formatNum(Math.abs(diff)), up ? "升高" : "下降", absRate(rate));
        int color = ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2);
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.indexOf("量") + 1, tip.indexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.indexOf("比") + 5, tip.lastIndexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.lastIndexOf("度") + 1, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip4(String timeLabel, double diff, String rate) {
        boolean up = diff >= 0;
        String tip = String.format("本%s单均与上%s相比%s%s元，%s幅度为%s", timeLabel, timeLabel,
                up ? "升高" : "下降", CommonUtils.formatMoney(Math.abs(diff)),
                up ? "升高" : "下降", absRate(rate));
        int color = ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2);
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(color), tip.indexOf("比") + 3, tip.indexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.lastIndexOf("为") + 1, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip5Or6(String timeLabel, String typeLabel, String date, int orderNum) {
        SimpleDateFormat sdf = new SimpleDateFormat("EE(MM月dd日)", Locale.CHINA);
        String tip = String.format("本%s订单量最%s日为：%s，单量为%s笔", timeLabel, typeLabel, sdf.format(DateUtil.parse(date)), orderNum);
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("为") + 1, tip.lastIndexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip7(String timeLabel, double orderNum) {
        String tip = String.format("本%s订单日均单量为%s笔", timeLabel, CommonUtils.formatNum(orderNum));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_ff7a45)), tip.lastIndexOf("为") + 1, tip.lastIndexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    public void onDestroyView() {
        mAdapter = null;
        super.onDestroyView();
        unbinder.unbind();
    }
}
