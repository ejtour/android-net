package com.hll_sc_app.app.wallet.details.list;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.wallet.details.DetailsRecord;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/6
 */
public class DetailsListAdapter extends BaseQuickAdapter<DetailsRecord, BaseViewHolder> {
    DetailsListAdapter() {
        super(R.layout.item_wallet_details);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailsRecord item) {
        helper.setText(R.id.iwd_time, DateUtil.getReadableTime(item.getAccountTime()))
                .setText(R.id.iwd_delta, item.getDelta())
                .setText(R.id.iwd_balance, "余额：" + item.getTransAfterBalance())
                .setText(R.id.iwd_label, item.getTransTypeDesc());
    }
}
