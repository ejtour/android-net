package com.hll_sc_app.app.analysis.trade;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.analysis.BaseAnalysisFragment;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisDataBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.analysis.TradeAmountFooter;

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

public class TradeAmountFragment extends BaseAnalysisFragment {
    @BindView(R.id.fta_list_view)
    RecyclerView mListView;
    Unbinder unbinder;
    private TradeAmountAdapter mAdapter;
    private TradeAmountFooter mFooter;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trade_amount, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mAdapter = new TradeAmountAdapter();
        mAdapter.addHeaderView(View.inflate(requireContext(), R.layout.view_trade_amount_header, null));
        mFooter = new TradeAmountFooter(requireContext());
        mAdapter.addFooterView(mFooter);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        if (mAnalysisEvent != null) {
            AnalysisResp analysisResp = mAnalysisEvent.getAnalysisResp();
            if (analysisResp != null) {
                List<AnalysisBean> records = analysisResp.getRecords();
                mAdapter.setNewData(records, mAnalysisEvent.getTimeType());
                if (!CommonUtils.isEmpty(records) && records.size() > 1) {
                    String label = mAnalysisEvent.getTimeType() == 2 ? "周" : "月";
                    AnalysisBean cur = records.get(records.size() - 1);
                    AnalysisDataBean analysisData = analysisResp.getAnalysisData();
                    if (analysisData != null) {
                        mFooter.setData(
                                handleTip1(label, analysisData.getDiffAmount(), analysisData.getAmountRate()),
                                handleTip2(mAnalysisEvent.getTimeType(), analysisData.getCompareAmount(), analysisData.getCompareRate()),
                                handleTip3Or4(label, "高", analysisData.getMaxValidTradeAmountTime(), analysisData.getMaxValidTradeAmount()),
                                handleTip3Or4(label, "低", analysisData.getMinValidTradeAmountTime(), analysisData.getMinValidTradeAmount()),
                                handleTip5(label, analysisData.getDailyValidTradeAmount())
                        );
                    }
                    mFooter.setData(records, mAnalysisEvent.getTimeType());
                }
            }
        }
    }

    private CharSequence handleTip1(String timeLabel, double diff, String rate) {
        boolean up = diff >= 0;
        String diffLabel = up ? "升高" : "降低";
        String tip = String.format("本%s订单交易金额比上%s%s%s元，%s%s", timeLabel, timeLabel,
                diffLabel, CommonUtils.formatMoney(Math.abs(diff)), diffLabel, absRate(rate));
        SpannableString ss = new SpannableString(tip);
        int color = ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2);
        ss.setSpan(new ForegroundColorSpan(color), tip.lastIndexOf("上") + 4, tip.lastIndexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.lastIndexOf("，") + 3, +tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip2(int timeType, double diff, String rate) {
        boolean up = diff >= 0;
        String diffLabel = up ? "升高" : "降低";
        String tip = String.format("本%s与上%s同期相比%s%s元，%s%s", timeType == 2 ? "周" : "月", timeType == 2 ? "月" : "年",
                diffLabel, CommonUtils.formatMoney(Math.abs(diff)), diffLabel, absRate(rate));
        int color = ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2);
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(color), tip.indexOf("比") + 3, tip.lastIndexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), tip.lastIndexOf("，") + 3, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip3Or4(String timeLabel, String typeLabel, String date, double amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("EE(MM月dd日)", Locale.CHINA);
        String tip = String.format("本%s订单交易额最%s日为：%s，金额为%s元", timeLabel, typeLabel,
                sdf.format(DateUtil.parse(date)), CommonUtils.formatMoney(amount));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("为") + 1, tip.lastIndexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip5(String timeLabel, double amount) {
        String tip = String.format("本%s日均交易额为%s元", timeLabel, CommonUtils.formatMoney(amount));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_ff7a45)), tip.lastIndexOf("为") + 1, tip.lastIndexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    public void onDestroyView() {
        mAdapter = null;
        mFooter = null;
        super.onDestroyView();
        unbinder.unbind();
    }
}
