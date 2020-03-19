package com.hll_sc_app.app.marketingsetting.check.groups;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.marketingsetting.CouponSendReq;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/4
 */

public class CheckGroupsAdapter extends BaseQuickAdapter<CouponSendReq.GroupandShopsBean, BaseViewHolder> {
    CheckGroupsAdapter(@Nullable List<CouponSendReq.GroupandShopsBean> data) {
        super(R.layout.item_purchaser_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponSendReq.GroupandShopsBean item) {
        ((TextView) helper.itemView).setText(item.getPurchaserName());
    }
}
