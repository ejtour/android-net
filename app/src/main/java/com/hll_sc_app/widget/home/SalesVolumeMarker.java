package com.hll_sc_app.widget.home;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.hll_sc_app.R;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

public class SalesVolumeMarker extends MarkerView {
    @BindView(R.id.msv_cur_week)
    TextView mCurWeek;
    @BindView(R.id.msv_last_week)
    TextView mLastWeek;

    public SalesVolumeMarker(Context context, Chart chart) {
        super(context, R.layout.view_marker_sales_volume);
        ButterKnife.bind(this, getChildAt(0));
        setChartView(chart);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        LineData data = (LineData) getChartView().getData();
        LineDataSet lastSet = (LineDataSet) data.getDataSetByIndex(0);
        LineDataSet curSet = (LineDataSet) data.getDataSetByIndex(1);
        mLastWeek.setText(String.format("¥%s", CommonUtils.formatMoney(lastSet.getEntryForIndex((int) e.getX()).getY())));
        mCurWeek.setText(String.format("¥%s", CommonUtils.formatMoney(curSet.getEntryForIndex((int) e.getX()).getY())));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() >> 1), -(getHeight() >> 1));
    }
}
