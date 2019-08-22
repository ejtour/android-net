package com.hll_sc_app.widget.home;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.hll_sc_app.R;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/22
 */

public class TrendMarker extends MarkerView {
    @BindView(R.id.otm_date)
    TextView mDate;
    @BindView(R.id.otm_amount)
    TextView mAmount;
    @BindView(R.id.otm_bill_num)
    TextView mBillNum;

    public TrendMarker(Context context, Chart chart) {
        super(context, R.layout.view_order_trend_marker);
        ButterKnife.bind(this, getChildAt(0));
        setChartView(chart);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mDate.setText(DateUtil.getReadableTime(e.getData().toString(), "MM月dd日"));
        LineDataSet amount = (LineDataSet) getChartView().getData().getDataSetByIndex(0);
        LineDataSet billNum = (LineDataSet) getChartView().getData().getDataSetByIndex(1);
        mAmount.setText(String.format("交易金额：%s", CommonUtils.formatMoney(amount.getEntryForIndex((int) e.getX()).getY())));
        mBillNum.setText(String.format("订单量：%s", CommonUtils.formatNumber(billNum.getEntryForIndex((int) e.getX()).getY())));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() >> 1), -(getHeight() >> 1));
    }
}
