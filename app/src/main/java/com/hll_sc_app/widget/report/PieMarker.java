package com.hll_sc_app.widget.report;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.hll_sc_app.R;

import java.text.NumberFormat;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/6
 */

public class PieMarker extends MarkerView {
    private NumberFormat mPercentInstance;

    {
        mPercentInstance = NumberFormat.getPercentInstance();
        mPercentInstance.setMaximumFractionDigits(2);
    }

    public PieMarker(Context context, Chart chart) {
        super(context, R.layout.view_chart_toast);
        setChartView(chart);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        ((TextView) getChildAt(0)).setText(String.format("%s：%s", ((PieEntry) e).getLabel(),
                mPercentInstance.format(highlight.getY() / ((PieData) getChartView().getData()).getYValueSum())));
        super.refreshContent(e, highlight);
    }
}
