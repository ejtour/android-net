package com.hll_sc_app.app.goodsdemand.entry;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public class GoodsDemandEntryAdapter extends BaseQuickAdapter<GoodsDemandBean, BaseViewHolder> {
    GoodsDemandEntryAdapter() {
        super(R.layout.item_goods_demand_entry);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDemandBean item) {
        ((TextView) helper.setText(R.id.gde_goods_name, item.getProductName())
                .setText(R.id.gde_name, item.getSupplyName())
                .setText(R.id.gde_status, getStatus(item.getStatus()))
                .setTextColor(R.id.gde_status, ContextCompat.getColor(helper.itemView.getContext(),
                        item.getStatus() == 3 ? R.color.colorPrimary : R.color.color_f6bb42))
                .getView(R.id.gde_status))
                .setCompoundDrawablesWithIntrinsicBounds(getIcon(item.getStatus()), 0, 0, 0);
    }

    private String getStatus(int status) {
        switch (status) {
            case 1:
                return "待回复";
            case 2:
                return "已回复";
            default:
                return "已上架";
        }
    }

    private int getIcon(int status) {
        switch (status) {
            case 1:
                return R.drawable.ic_warn;
            case 2:
                return R.drawable.ic_yellow_ok;
            default:
                return R.drawable.ic_blue_ok;
        }
    }
}
