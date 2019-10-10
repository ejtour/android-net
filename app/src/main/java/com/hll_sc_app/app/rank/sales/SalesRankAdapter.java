package com.hll_sc_app.app.rank.sales;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.rank.RankHelper;
import com.hll_sc_app.bean.rank.SalesRankBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/10
 */

public class SalesRankAdapter extends BaseQuickAdapter<SalesRankBean, BaseViewHolder> {
    SalesRankAdapter() {
        super(R.layout.item_sales_rank);
    }

    @Override
    protected void convert(BaseViewHolder helper, SalesRankBean item) {
        int rank = mData.indexOf(item) + 1;
        helper.setGone(R.id.isr_medal, rank <= 3)
                .setVisible(R.id.isr_rank, rank > 3)
                .setText(R.id.isr_rank, String.valueOf(rank))
                .setImageResource(R.id.isr_medal, RankHelper.getMedal(rank))
                .setText(R.id.isr_number, item.getSalesManCode())
                .setText(R.id.isr_name, item.getSalesManName())
                .setText(R.id.isr_amount, "¥" + CommonUtils.formatMoney(item.getValidTradeAmount()));
    }
}
