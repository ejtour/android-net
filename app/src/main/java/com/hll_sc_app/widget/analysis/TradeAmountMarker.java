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

public class TradeAmountMarker extends MarkerView {
    @BindView(R.id.otm_date)
    TextView mLabel;
    @BindView(R.id.otm_amount)
    TextView mAmount;
    @BindView(R.id.otm_bill_num)
    TextView mRate;

    public TradeAmountMarker(Context context, Chart chart) {
        super(context, R.layout.view_order_trend_marker);
        ButterKnife.bind(this, getChildAt(0));
        setChartView(chart);
        GradientDrawable amount = (GradientDrawable) mAmount.getCompoundDrawables()[0];
        amount.setColor(0xff69c0ff);
        GradientDrawable rate = (GradientDrawable) mRate.getCompoundDrawables()[0];
        rate.setColor(0xffff7a45);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mLabel.setText(e.getData().toString());
        CombinedData data = (CombinedData) getChartView().getData();
        BarDataSet amount = (BarDataSet) data.getBarData().getDataSetByIndex(0);
        LineDataSet rate = (LineDataSet) data.getLineData().getDataSetByIndex(0);
        mAmount.setText(String.format("交易金额(元)：%s", CommonUtils.formatMoney(amount.getEntryForIndex((int) e.getX()).getY())));
        mRate.setText(String.format("环比增长：%s%%", CommonUtils.formatNumber(rate.getEntryForIndex((int) e.getX()).getY())));
        super.refreshContent(e, highlight);
    }
}
