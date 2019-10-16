package com.hll_sc_app.app.analysis.order;

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
import com.hll_sc_app.R;
import com.hll_sc_app.app.analysis.BaseAnalysisFragment;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisDataBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;

import java.text.SimpleDateFormat;
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
    }

    @Override
    protected void initData() {
        if (mAnalysisEvent != null) {
            AnalysisResp analysisResp = mAnalysisEvent.getAnalysisResp();
            if (analysisResp != null) {
                List<AnalysisBean> records = analysisResp.getRecords();
                mAdapter.setNewData(records, mAnalysisEvent.getTimeType());
                if (!CommonUtils.isEmpty(records) && analysisResp.getRecords().size() > 1) {
                    String label = mAnalysisEvent.getTimeType() == 2 ? "周" : "月";
                    AnalysisBean cur = records.get(records.size() - 1);
                    AnalysisBean last = records.get(records.size() - 2);

                    mTip1.setText(handleTip1(label, cur.getShopNum(), cur.getShopNum() - last.getShopNum(),
                            getRate(cur.getShopNum(), last.getShopNum())));

                    mTip2.setText(handleTip2(label, cur.getAverageShopTradeAmount() - last.getAverageShopTradeAmount(),
                            getRate(cur.getAverageShopTradeAmount(), last.getAverageShopTradeAmount())));

                    mTip3.setText(handleTip3(label, cur.getValidOrderNum(),
                            cur.getValidOrderNum() - last.getValidOrderNum(),
                            getRate(cur.getValidOrderNum(), last.getValidOrderNum())));

                    mTip4.setText(handleTip4(label, cur.getAverageTradeAmount() - last.getAverageTradeAmount(),
                            getRate(cur.getAverageTradeAmount(), last.getAverageTradeAmount())));

                    AnalysisDataBean analysisData = analysisResp.getAnalysisData();
                    if (analysisData != null) {
                        mTip5.setText(handleTip5Or6(label, "高", analysisData.getMaxValidOrderNumTime(), analysisData.getMaxValidOrderNum()));
                        mTip6.setText(handleTip5Or6(label, "低", analysisData.getMinValidOrderNumTime(), analysisData.getMinValidOrderNum()));
                        mTip7.setText(handleTip7(label, analysisData.getDailyValidOrderNum()));
                    }
                }
            }
        }
    }


    private CharSequence handleTip1(String timeLabel, int num, int diff, double rate) {
        boolean up = diff >= 0;
        String tip = String.format("本%s买家门店数%s家，比上%s%s%s家，%s幅度%s",
                timeLabel, num, timeLabel, up ? "增加" : "降低",
                diff, up ? "升高" : "降低", mPercentInstance.format(rate));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.indexOf("数") + 1, tip.indexOf("家，"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.indexOf("上") + 4, tip.lastIndexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2)),
                tip.lastIndexOf("度") + 1, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip2(String timeLabel, double diff, double rate) {
        boolean up = diff >= 0;
        String tip = String.format("本%s客单价呈%s趋势，比上%s%s%s元，%s幅度为%s", timeLabel,
                up ? "升高" : "下降", timeLabel, up ? "增加" : "降低", CommonUtils.formatMoney(diff),
                up ? "升高" : "下降", mPercentInstance.format(rate));
        int color = ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2);
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(color), tip.indexOf("，") + 6, tip.indexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.lastIndexOf("为") + 1, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip3(String timeLabel, int orderNum, int diff, double rate) {
        boolean up = diff >= 0;
        String tip = String.format("本%s订单总量%s笔，比上%s%s%s笔，%s幅度%s", timeLabel,
                CommonUtils.formatNum(orderNum), timeLabel, up ? "增加" : "降低",
                CommonUtils.formatNum(diff), up ? "升高" : "下降", mPercentInstance.format(rate));
        int color = ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2);
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.indexOf("量") + 1, tip.indexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.indexOf("比") + 5, tip.lastIndexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.lastIndexOf("度") + 1, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip4(String timeLabel, double diff, double rate) {
        boolean up = diff >= 0;
        String tip = String.format("本%s单均与上%s相比%s%s元，%s幅度为%s", timeLabel, timeLabel,
                up ? "升高" : "下降", CommonUtils.formatMoney(diff),
                up ? "升高" : "下降", mPercentInstance.format(rate));
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
