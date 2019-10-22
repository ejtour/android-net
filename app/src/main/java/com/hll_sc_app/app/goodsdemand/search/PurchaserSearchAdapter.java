package com.hll_sc_app.app.goodsdemand.search;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.window.NameValue;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public class PurchaserSearchAdapter extends BaseQuickAdapter<NameValue, BaseViewHolder> {
    private NameValue mCur;

    PurchaserSearchAdapter() {
        super(R.layout.item_purchaser_search);
    }

    public void select(NameValue value) {
        mCur = value;
    }

    @Override
    protected void convert(BaseViewHolder helper, NameValue item) {
        helper.setText(R.id.ips_label, item.getName())
                .setGone(R.id.ips_check, mCur == item);
    }
}
