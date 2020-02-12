package com.hll_sc_app.app.goodsdemand.entry;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.GoodsDemandHelper;
import com.hll_sc_app.app.goodsdemand.detail.GoodsDemandDetailActivity;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public class GoodsDemandEntryAdapter extends BaseQuickAdapter<GoodsDemandBean, BaseViewHolder> {
    GoodsDemandEntryAdapter() {
        super(R.layout.item_goods_demand_entry);
        setOnItemClickListener((adapter, view, position) -> {
            GoodsDemandBean item = getItem(position);
            if (item != null) GoodsDemandDetailActivity.start(item);
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDemandBean item) {
        ((TextView) helper.setText(R.id.gde_goods_name, item.getProductName())
                .setText(R.id.gde_name, item.getSupplyName())
                .setText(R.id.gde_status, GoodsDemandHelper.getStatus(item.getStatus()))
                .setTextColor(R.id.gde_status, ContextCompat.getColor(helper.itemView.getContext(), GoodsDemandHelper.getStatusColor(item.getStatus())))
                .getView(R.id.gde_status))
                .setCompoundDrawablesWithIntrinsicBounds(GoodsDemandHelper.getIcon(item.getStatus()), 0, 0, 0);
    }
}
