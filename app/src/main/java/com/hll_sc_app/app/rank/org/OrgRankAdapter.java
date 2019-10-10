package com.hll_sc_app.app.rank.org;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.rank.RankHelper;
import com.hll_sc_app.bean.rank.OrgRankBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/10
 */

public class OrgRankAdapter extends BaseQuickAdapter<OrgRankBean, BaseViewHolder> {

    OrgRankAdapter() {
        super(R.layout.item_org_rank);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrgRankBean item) {
        int rank = mData.indexOf(item) + 1;
        helper.setGone(R.id.ior_medal, rank <= 3)
                .setVisible(R.id.ior_rank, rank > 3)
                .setText(R.id.ior_rank, String.valueOf(rank))
                .setImageResource(R.id.ior_medal, RankHelper.getMedal(rank))
                .setText(R.id.ior_name, item.getName())
                .setText(R.id.ior_amount, "¥" + CommonUtils.formatMoney(item.getAmount()));
    }
}
