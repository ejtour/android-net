package com.hll_sc_app.widget.analysis;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.hll_sc_app.R;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/1
 */

public class GroupMarker extends MarkerView {
    @BindView(R.id.vmt_label)
    TextView mLabel;
    @BindView(R.id.vmt_item_1)
    TextView mItem1;
    @BindView(R.id.vmt_item_2)
    TextView mItem2;
    @BindView(R.id.vmt_item_3)
    TextView mItem3;

    public GroupMarker(Context context, Chart chart) {
        super(context, R.layout.view_marker_three);
        ButterKnife.bind(this, getChildAt(0));
        setChartView(chart);
        GradientDrawable item1 = (GradientDrawable) mItem1.getCompoundDrawables()[0];
        item1.setColor(0xff69c0ff);
        GradientDrawable item2 = (GradientDrawable) mItem2.getCompoundDrawables()[0];
        item2.setColor(0xffff7875);
        GradientDrawable item3 = (GradientDrawable) mItem3.getCompoundDrawables()[0];
        item3.setColor(0xff95de64);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mLabel.setText(e.getData().toString());
        BarData data = (BarData) getChartView().getData();
        BarDataSet item1 = (BarDataSet) data.getDataSetByIndex(0);
        BarDataSet item2 = (BarDataSet) data.getDataSetByIndex(1);
        BarDataSet item3 = (BarDataSet) data.getDataSetByIndex(2);
        mItem1.setText(String.format("采购商总数：%s", CommonUtils.formatNumber(item1.getEntryForIndex((int) e.getX()).getY())));
        mItem2.setText(String.format("活跃采购商：%s", CommonUtils.formatNumber(item2.getEntryForIndex((int) e.getX()).getY())));
        mItem3.setText(String.format("新增采购商：%s", CommonUtils.formatNumber(item3.getEntryForIndex((int) e.getX()).getY())));
        super.refreshContent(e, highlight);
    }
}
