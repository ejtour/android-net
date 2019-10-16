package com.hll_sc_app.app.analysis.toptean;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.analysis.BaseAnalysisFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.bean.operationanalysis.LostResp;
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

public class TopTenFragment extends BaseAnalysisFragment {
    @BindView(R.id.stt_list_view)
    RecyclerView mListView;
    @BindView(R.id.stt_add_tip_1)
    TextView mAddTip1;
    @BindView(R.id.stt_add_tip_2)
    TextView mAddTip2;
    @BindView(R.id.stt_add_tip_3)
    TextView mAddTip3;
    @BindView(R.id.stt_loss_tip_1)
    TextView mLossTip1;
    @BindView(R.id.stt_loss_tip_2)
    TextView mLossTip2;
    @BindView(R.id.stt_loss_tip_3)
    TextView mLossTip3;
    Unbinder unbinder;
    private TopTenAdapter mAdapter;
    private TextView mEmpty;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shop_top_ten, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mAdapter = new TopTenAdapter();
        mEmpty = new TextView(requireContext());
        mEmpty.setText("暂无数据");
        mEmpty.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_666666));
        mEmpty.setPadding(0, UIUtils.dip2px(10), 0, UIUtils.dip2px(10));
        mEmpty.setTextSize(10);
        mEmpty.setGravity(Gravity.CENTER);
        mListView.setLayoutManager(new LinearLayoutManager(requireContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter.addHeaderView(View.inflate(requireContext(), R.layout.view_shop_top_ten_header, null));
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        if (mAnalysisEvent != null) {
            TopTenResp topTenResp = mAnalysisEvent.getTopTenResp();
            if (topTenResp != null) {
                String label = mAnalysisEvent.getTimeType() == 2 ? "周" : "月";
                if (CommonUtils.isEmpty(topTenResp.getRecords()))
                    mAdapter.addHeaderView(mEmpty);
                else mAdapter.removeHeaderView(mEmpty);
                mAdapter.setNewData(topTenResp.getRecords());

                List<AnalysisBean> analysisBeans = mAnalysisEvent.getAnalysisResp().getRecords();
                AnalysisBean bean = analysisBeans.get(analysisBeans.size() - 1);

                mAddTip1.setText(handleAddTip1(label, bean.getCoopIncrGroupNum(), bean.getCoopIncrShopNum()));
                TopTenCustomerBean amountBean = topTenResp.getMaxAmountIncr();
                if (amountBean == null) amountBean = new TopTenCustomerBean();
                mAddTip2.setText(handleAddTip2Or3("交易金额最高的合作采购商", amountBean.getName(), amountBean.getOrder(),
                        amountBean.getAmount(), ContextCompat.getColor(requireContext(), R.color.color_ff7a45)));
                TopTenCustomerBean orderBean = topTenResp.getMaxOrderIncr();
                if (orderBean == null) orderBean = new TopTenCustomerBean();
                mAddTip3.setText(handleAddTip2Or3("订单笔数最多的合作", orderBean.getName(), orderBean.getOrder(),
                        orderBean.getAmount(), ContextCompat.getColor(requireContext(), R.color.color_fe864f)));

                LostResp lostResp = mAnalysisEvent.getLostResp();
                mLossTip1.setText(handleLossTip1(label, lostResp.getLostShopNum(), lostResp.getLoseRate()));
                mLossTip2.setText(handleLossTip2Or3(label, "交易额金额最高", lostResp.getLostAmountShopName(),
                        lostResp.getLostTradeMostOrderNum(), lostResp.getLostTradeMostOrderAmount(),
                        ContextCompat.getColor(requireContext(), R.color.color_ff7a45)));
                mLossTip3.setText(handleLossTip2Or3(label, "订单笔数最多", lostResp.getLostBillShopName(),
                        lostResp.getPreBillOrderNum(), lostResp.getPreBillHighestAmount(),
                        ContextCompat.getColor(requireContext(), R.color.color_fe864f)));
            }
        }
    }

    private CharSequence handleAddTip1(String timeLabel, int groupNum, int shopNum) {
        String tip = String.format("本%s新增采购商%s家，采购门店%s个", timeLabel, groupNum, shopNum);
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.indexOf("商") + 1, tip.indexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("店") + 1, tip.lastIndexOf("个"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleAddTip2Or3(String prefix, String name, int orderNum, double amount, int nameColor) {
        String tip = String.format("%s门店为：%s，订单：%s笔，交易金额：%s元", prefix,
                name, orderNum, CommonUtils.formatMoney(amount));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(nameColor), tip.indexOf("：") + 1, tip.lastIndexOf("，订"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("单：") + 2, tip.lastIndexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("：") + 1, tip.lastIndexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleLossTip1(String timeLabel, int shopNum, String rate) {
        String tip = String.format("本%s与上%s相比流失采购门店%s家，流失率%s", timeLabel, timeLabel, shopNum, rate);
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("店") + 1, tip.lastIndexOf("家"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("率") + 1, tip.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private CharSequence handleLossTip2Or3(String timeLabel, String typeLabel, String shopName, int orderNum, double amount, int nameColor) {
        String tip = String.format("流失采购商门店上%s%s的门店为：%s，订单：%s笔，交易金额：%s元",
                timeLabel, typeLabel, shopName, orderNum, CommonUtils.formatMoney(amount));
        SpannableString ss = new SpannableString(tip);
        ss.setSpan(new ForegroundColorSpan(nameColor), tip.indexOf("：") + 1, tip.lastIndexOf("，订"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("单：") + 2, tip.lastIndexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mHighlightColor), tip.lastIndexOf("：") + 1, tip.lastIndexOf("元"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    public void onDestroyView() {
        mAdapter = null;
        mEmpty = null;
        super.onDestroyView();
        unbinder.unbind();
    }
}
