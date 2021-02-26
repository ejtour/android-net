package com.hll_sc_app.app.stockmanage.depot;

import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.stockmanage.DepotResp;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/13
 */
public class DepotAdapter extends BaseQuickAdapter<DepotResp, BaseViewHolder> {
    public DepotAdapter() {
        super(R.layout.item_depot);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.id_root)
                .addOnClickListener(R.id.id_default)
                .addOnClickListener(R.id.id_toggle);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, DepotResp item) {
        helper.setText(R.id.id_name, item.getHouseName())
                .setText(R.id.id_no, String.format("编号：%s", item.getHouseCode()))
                .setGone(R.id.id_tag, item.getIsDefault() == 1)
                .setGone(R.id.id_default, item.getIsDefault() != 1 && item.getIsActive() == 1)
                .setGone(R.id.id_toggle, item.getIsDefault() != 1)
                .setImageResource(R.id.id_toggle, item.getIsActive() == 1 ? R.drawable.ic_depot_disable : R.drawable.ic_depot_enable)
                .setText(R.id.id_status, item.getIsActive() == 1 ? "启用" : "停用")
                .setTextColor(R.id.id_status, ContextCompat.getColor(helper.itemView.getContext(),
                        item.getIsActive() == 1 ? R.color.color_7ed321 : R.color.color_f5a623))
                .setText(R.id.id_range, String.format("配送范围：%s", item.getWarehouseDeliveryRangeSummary()))
                .setText(R.id.id_category, String.format("存储分类：%s", item.getWarehouseStoreCategorySummary()))
                .setText(R.id.id_num, String.format("存储单品：%s 种", CommonUtils.formatNum(item.getWarehouseStoreProductNum())));
    }
}
