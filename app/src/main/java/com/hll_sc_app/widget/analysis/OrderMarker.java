package com.hll_sc_app.widget.analysis;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.hll_sc_app.R;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/1
 */

public class OrderMarker extends MarkerView {
    @BindView(R.id.vmf_label)
    TextView mLabel;
    @BindView(R.id.vmf_item_1)
    TextView mItem1;
    @BindView(R.id.vmf_item_2)
    TextView mItem2;
    @BindView(R.id.vmf_item_3)
    TextView mItem3;
    @BindView(R.id.vmf_item_4)
    TextView mItem4;

    public OrderMarker(Context context, Chart chart) {
        super(context, R.layout.view_marker_four);
        ButterKnife.bind(this, getChildAt(0));
        setChartView(chart);
        GradientDrawable item1 = (GradientDrawable) mItem1.getCompoundDrawables()[0];
        item1.setColor(0xff95DE64);
        GradientDrawable item2 = (GradientDrawable) mItem2.getCompoundDrawables()[0];
        item2.setColor(0xff5CAAF0);
        GradientDrawable item3 = (GradientDrawable) mItem3.getCompoundDrawables()[0];
        item3.setColor(0xffFF7A45);
        GradientDrawable item4 = (GradientDrawable) mItem4.getCompoundDrawables()[0];
        item4.setColor(0xffB37FEB);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mLabel.setText(e.getData().toString());
        CombinedData data = (CombinedData) getChartView().getData();
        BarDataSet item1 = (BarDataSet) data.getBarData().getDataSetByIndex(0);
        BarDataSet item2 = (BarDataSet) data.getBarData().getDataSetByIndex(1);
        LineDataSet item3 = (LineDataSet) data.getLineData().getDataSetByIndex(0);
        LineDataSet item4 = (LineDataSet) data.getLineData().getDataSetByIndex(1);
        mItem1.setText(String.format("买家门店数：%s", CommonUtils.formatNumber(item1.getEntryForIndex((int) e.getX()).getY())));
        mItem2.setText(String.format("有效订单数：%s", CommonUtils.formatNumber(item2.getEntryForIndex((int) e.getX()).getY())));
        mItem3.setText(String.format("客单价(元)：%s", CommonUtils.formatNumber(item3.getEntryForIndex((int) e.getX()).getY())));
        mItem4.setText(String.format("单均(元)：%s", CommonUtils.formatNumber(item4.getEntryForIndex((int) e.getX()).getY())));
        super.refreshContent(e, highlight);
    }
}
