package com.hll_sc_app.app.analysis.purchaser;

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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hll_sc_app.R;
import com.hll_sc_app.app.analysis.BaseAnalysisFragment;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisDataBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.bean.operationanalysis.TopTenCustomerBean;
import com.hll_sc_app.bean.operationanalysis.TopTenResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class PurchaserAnalysisFragment extends BaseAnalysisFragment {
    @BindView(R.id.fpa_bar_chart)
    BarChart mBarChart;
    @BindView(R.id.fpa_line_chart)
    LineChart mLineChart;
    @BindView(R.id.fpa_list_view)
    RecyclerView mListView;
    @BindView(R.id.fpa_tip_1)
    TextView mTip1;
    @BindView(R.id.fpa_tip_2)
    TextView mTip2;
    @BindView(R.id.fpa_tip_3)
    TextView mTip3;
    @BindView(R.id.fpa_tip_4)
    TextView mTip4;
    Unbinder unbinder;
    private PurchaserAnalysisAdapter mAdapter;
    private List<String> mAxisLabels = new ArrayList<>();
    private ValueFormatter mValueFormatter = new ValueFormatter() {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return value < 0 || value >= mAxisLabels.size() ? "" : mAxisLabels.get((int) value);
        }
    };

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_purchaser_analysis, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mAdapter = new PurchaserAnalysisAdapter();
        mListView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        mListView.setAdapter(mAdapter);
        initBarChart();
        initLineChart();
    }

    private void initLineChart() {
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setNoDataText("无数据");
        mLineChart.setScaleEnabled(false);
        mLineChart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mLineChart.setPinchZoom(false);

        Legend legend = mLineChart.getLegend();
        legend.setTextSize(10);
        legend.setXEntrySpace(10);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormLineWidth(2);
        legend.setFormSize(10);
        legend.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_666666));
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_222222));
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(-20);
        xAxis.setLabelCount(5);
        xAxis.setSpaceMin(0.5f);
        xAxis.setSpaceMax(0.5f);
        xAxis.setValueFormatter(mValueFormatter);

        YAxis axisLeft = mLineChart.getAxisLeft();
        axisLeft.setDrawAxisLine(false);
        axisLeft.setGridColor(ContextCompat.getColor(requireContext(), R.color.color_dddddd));
        axisLeft.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_222222));
        axisLeft.enableGridDashedLine(1, 1, 1);
        axisLeft.setTextSize(10);
        axisLeft.setAxisMinimum(0);

        mLineChart.getAxisRight().setEnabled(false);
    }

    private void initBarChart() {
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setNoDataText("无数据");
        mBarChart.setScaleEnabled(false);
        mBarChart.setPinchZoom(false);
        mBarChart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBarChart.setExtraOffsets(10, 0, 10, 8);

        Legend legend = mBarChart.getLegend();
        legend.setTextSize(10);
        legend.setXEntrySpace(10);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_666666));
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_222222));
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(-20);
        xAxis.setCenterAxisLabels(true);
        xAxis.setTextSize(9);
        xAxis.setValueFormatter(mValueFormatter);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(5);

        YAxis axisLeft = mBarChart.getAxisLeft();
        axisLeft.setDrawAxisLine(false);
        axisLeft.setAxisMinimum(0);
        axisLeft.enableGridDashedLine(1, 1, 1);
        axisLeft.setGridColor(ContextCompat.getColor(requireContext(), R.color.color_dddddd));
        axisLeft.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_222222));
        axisLeft.setTextSize(10);

        mBarChart.getAxisRight().setEnabled(false);
    }

    @Override
    protected void initData() {
        if (mAnalysisEvent != null) {
            AnalysisResp analysisResp = mAnalysisEvent.getAnalysisResp();
            if (analysisResp != null) {
                List<AnalysisBean> records = analysisResp.getRecords();
                setChartData(records);
                if (!CommonUtils.isEmpty(records) && analysisResp.getRecords().size() > 1) {
                    String label = mAnalysisEvent.getTimeType() == 2 ? "周" : "月";
                    mAdapter.setNewData(records, mAnalysisEvent.getTimeType());
                    AnalysisDataBean analysisData = analysisResp.getAnalysisData();
                    if (analysisData != null) {
                        AnalysisBean cur = records.get(records.size() - 1);
                        mTip1.setText(handleTip1(label, cur.getCoopActiveGroupNum(), cur.getCoopActiveShopNum(), analysisData.getActiveRate()));
                        mTip2.setText(handleTip2(cur.getCoopIncrShopNum(), analysisData.getNewIncreaseRate()));
                    }

                    TopTenResp topTenResp = mAnalysisEvent.getTopTenResp();
                    if (topTenResp != null) {
                        TopTenCustomerBean amountBean = topTenResp.getMaxAmountActive();
                        if (amountBean == null) amountBean = new TopTenCustomerBean();
                        mTip3.setText(handleTip3Or4(String.format("本%s交易额", label), amountBean.getName(),
                                amountBean.getOrder(), amountBean.getAmount(), amountBean.getAverageAmount(),
                                ContextCompat.getColor(requireContext(), R.color.color_ff7a45)));

                        TopTenCustomerBean orderBean = topTenResp.getMaxOrderActive();
                        if (orderBean == null) orderBean = new TopTenCustomerBean();
                        mTip4.setText(handleTip3Or4("下单量", orderBean.getName(),
                                orderBean.getOrder(), orderBean.getAmount(), amountBean.getAverageAmount(),
                                ContextCompat.getColor(requireContext(), R.color.color_fe864f)));
                    }
                }
            }
        }
    }

    private CharSequence handleTip1(String timeLabel, int groupNum, int shopNum, String rate) {
        boolean up = !rate.startsWith("-");
        String tip = String.format("本%s活跃合作采购商集团%s家，活跃采购门店%s家，活跃率%s%s",
                timeLabel, groupNum, shopNum, up ? "升高" : "降低", absRate(rate));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.indexOf("团") + 1, tip.indexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("店") + 1, tip.lastIndexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2)),
                tip.lastIndexOf("率") + 3, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip2(int shopNum, String rate) {
        boolean up = !rate.startsWith("-");
        String tip = String.format("新增采购门店%s家，新增%s%s", shopNum, up ? "升高" : "降低", absRate(rate));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.indexOf("店") + 1, tip.indexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2)),
                tip.lastIndexOf("增") + 3, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip3Or4(String typeLabel, String name, int orderNum, double amount, double avg, int nameColor) {
        String tip = String.format("%s最高的采购商门店：%s，订单：%s笔，交易金额：%s元，单均：%s元", typeLabel, name,
                orderNum, CommonUtils.formatMoney(amount), CommonUtils.formatMoney(avg));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(nameColor), tip.indexOf("：") + 1, tip.lastIndexOf("，订"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("单：") + 2, tip.lastIndexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("额：") + 2, tip.lastIndexOf("元，"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("：") + 1, tip.lastIndexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private void setChartData(List<AnalysisBean> list) {
        List<BarEntry> groupTotalList = new ArrayList<>();
        List<BarEntry> groupActiveList = new ArrayList<>();
        List<BarEntry> groupAddList = new ArrayList<>();

        List<Entry> shopTotalList = new ArrayList<>();
        List<Entry> shopActiveList = new ArrayList<>();
        List<Entry> shopAddList = new ArrayList<>();

        if (!CommonUtils.isEmpty(list)) {
            mAxisLabels.clear();
            for (int i = 0; i < list.size(); i++) {
                AnalysisBean bean = list.get(i);
                String dateRange = bean.getDateRange(mAnalysisEvent.getTimeType());
                mAxisLabels.add(dateRange);
                groupTotalList.add(new BarEntry(i, bean.getCoopGroupNum(), dateRange));
                groupActiveList.add(new BarEntry(i, bean.getCoopActiveGroupNum(), dateRange));
                groupAddList.add(new BarEntry(i, bean.getCoopIncrGroupNum(), dateRange));
                shopTotalList.add(new Entry(i, bean.getCoopShopNum(), dateRange));
                shopActiveList.add(new Entry(i, bean.getCoopActiveShopNum(), dateRange));
                shopAddList.add(new Entry(i, bean.getCoopIncrShopNum(), dateRange));
            }
        }
        BarDataSet groupTotalSet, groupActiveSet, groupAddSet;
        BarData barData = mBarChart.getData();
        if (barData != null) {
            groupTotalSet = (BarDataSet) barData.getDataSetByIndex(0);
            groupTotalSet.setValues(groupTotalList);
            groupTotalSet.notifyDataSetChanged();
            groupActiveSet = (BarDataSet) barData.getDataSetByIndex(1);
            groupActiveSet.setValues(groupActiveList);
            groupActiveSet.notifyDataSetChanged();
            groupAddSet = (BarDataSet) barData.getDataSetByIndex(2);
            groupAddSet.setValues(groupAddList);
            groupAddSet.notifyDataSetChanged();
            barData.notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            groupTotalSet = new BarDataSet(groupTotalList, "采购商总数");
            groupTotalSet.setColor(Color.parseColor("#69c0ff"));
            groupActiveSet = new BarDataSet(groupActiveList, "活跃采购商");
            groupActiveSet.setColor(Color.parseColor("#ff7875"));
            groupAddSet = new BarDataSet(groupAddList, "新增采购商");
            groupAddSet.setColor(Color.parseColor("#95de64"));
            BarData data = new BarData(groupTotalSet, groupActiveSet, groupAddSet);
            data.setBarWidth(0.2f);
            data.groupBars(0, 0.25f, 0.05f);
            for (IBarDataSet dataSet : data.getDataSets()) {
                dataSet.setDrawValues(true);
            }
            mBarChart.setData(data);
        }
        mBarChart.invalidate();

        LineDataSet shopTotalSet, shopActiveSet, shopAddSet;
        LineData lineData = mLineChart.getData();
        if (lineData != null) {
            shopTotalSet = (LineDataSet) lineData.getDataSetByIndex(0);
            shopTotalSet.setValues(shopTotalList);
            shopTotalSet.notifyDataSetChanged();
            shopActiveSet = (LineDataSet) lineData.getDataSetByIndex(1);
            shopActiveSet.setValues(shopActiveList);
            shopActiveSet.notifyDataSetChanged();
            shopAddSet = (LineDataSet) lineData.getDataSetByIndex(2);
            shopAddSet.setValues(shopAddList);
            shopAddSet.notifyDataSetChanged();
        } else {
            shopTotalSet = new LineDataSet(shopTotalList, "门店总数");
            shopTotalSet.setColor(Color.parseColor("#69C0FF"));
            shopActiveSet = new LineDataSet(shopActiveList, "活跃门店数");
            shopActiveSet.setColor(Color.parseColor("#FF7875"));
            shopAddSet = new LineDataSet(shopAddList, "新增门店数");
            shopAddSet.setColor(Color.parseColor("#95DE64"));
            LineData data = new LineData(shopTotalSet, shopActiveSet, shopAddSet);
            for (ILineDataSet dataSet : data.getDataSets()) {
                ((LineDataSet) dataSet).setDrawCircles(false);
//                dataSet.setDrawValues(true);
            }
            mLineChart.setData(data);
        }
        mLineChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        mAdapter = null;
        super.onDestroyView();
        unbinder.unbind();
    }
}
