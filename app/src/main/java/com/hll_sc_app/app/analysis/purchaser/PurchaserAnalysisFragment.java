package com.hll_sc_app.app.analysis.purchaser;

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
import com.hll_sc_app.R;
import com.hll_sc_app.app.analysis.BaseAnalysisFragment;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.bean.operationanalysis.TopTenCustomerBean;
import com.hll_sc_app.bean.operationanalysis.TopTenResp;
import com.hll_sc_app.citymall.util.CommonUtils;

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
    }

    @Override
    protected void initData() {
        if (mAnalysisEvent != null) {
            AnalysisResp analysisResp = mAnalysisEvent.getAnalysisResp();
            if (analysisResp != null) {
                List<AnalysisBean> records = analysisResp.getRecords();
                if (!CommonUtils.isEmpty(records) && analysisResp.getRecords().size() > 1) {
                    String label = mAnalysisEvent.getTimeType() == 2 ? "周" : "月";
                    mAdapter.setNewData(records, mAnalysisEvent.getTimeType());
                    AnalysisBean cur = records.get(records.size() - 1);
                    AnalysisBean last = records.get(records.size() - 2);
                    mTip1.setText(handleTip1(label, cur.getCoopActiveGroupNum(), cur.getCoopActiveShopNum(),
                            cur.getCoopActiveShopNum() >= last.getCoopActiveShopNum(),
                            getRate(cur.getCoopActiveShopNum(), last.getCoopActiveShopNum())));

                    mTip2.setText(handleTip2(cur.getCoopIncrShopNum(), cur.getCoopIncrShopNum() >= last.getCoopIncrShopNum(),
                            getRate(cur.getCoopIncrShopNum(), last.getCoopIncrShopNum())));

                    TopTenResp topTenResp = mAnalysisEvent.getTopTenResp();
                    if (topTenResp != null) {
                        TopTenCustomerBean amountBean = topTenResp.getMaxAmountActive();
                        if (amountBean == null) amountBean = new TopTenCustomerBean();
                        mTip3.setText(handleTip3Or4(String.format("本%s交易额", label), amountBean.getName(), amountBean.getOrder(), amountBean.getAmount(),
                                ContextCompat.getColor(requireContext(), R.color.color_ff7a45)));

                        TopTenCustomerBean orderBean = topTenResp.getMaxOrderActive();
                        if (orderBean == null) orderBean = new TopTenCustomerBean();
                        mTip4.setText(handleTip3Or4("下单量", orderBean.getName(), orderBean.getOrder(), orderBean.getAmount(),
                                ContextCompat.getColor(requireContext(), R.color.color_fe864f)));
                    }
                }
            }
        }
    }

    private CharSequence handleTip1(String timeLabel, int groupNum, int shopNum, boolean up, double rate) {
        String tip = String.format("本%s活跃合作采购商集团%s家，活跃采购门店%s家，活跃率%s%s",
                timeLabel, groupNum, shopNum, up ? "升高" : "降低", mPercentInstance.format(Math.abs(rate)));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.indexOf("团") + 1, tip.indexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("店") + 1, tip.lastIndexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2)),
                tip.lastIndexOf("率") + 3, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip2(int shopNum, boolean up, double rate) {
        String tip = String.format("新增采购门店%s家，新增%s%s", shopNum, up ? "升高" : "降低", mPercentInstance.format(Math.abs(rate)));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.indexOf("店") + 1, tip.indexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), up ? R.color.color_ed5655 : R.color.color_5cdad2)),
                tip.lastIndexOf("增") + 3, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleTip3Or4(String typeLabel, String name, int orderNum, double amount, int nameColor) {
        String tip = String.format("%s最高的采购商门店：%s，订单：%s笔，交易金额：%s元，单均：%s元", typeLabel, name,
                orderNum, CommonUtils.formatMoney(amount), CommonUtils.formatMoney(CommonUtils.divDouble(amount, orderNum, 2).doubleValue()));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(nameColor), tip.indexOf("：") + 1, tip.lastIndexOf("，订"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("单：") + 2, tip.lastIndexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("额：") + 2, tip.lastIndexOf("元，"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("：") + 1, tip.lastIndexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    public void onDestroyView() {
        mAdapter = null;
        super.onDestroyView();
        unbinder.unbind();
    }
}
