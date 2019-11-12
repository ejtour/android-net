package com.hll_sc_app.app.goodsdemand.special;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandEntryBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */

public class SpecialDemandEntryAdapter extends BaseQuickAdapter<SpecialDemandEntryBean, BaseViewHolder> {
    SpecialDemandEntryAdapter() {
        super(R.layout.item_goods_special_demand_entry);
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecialDemandEntryBean item) {
        ((GlideImageView) helper.setText(R.id.sde_name, item.getPurchaserName())
                .setText(R.id.sde_num, String.format("已设置%s个特殊需求商品", item.getProductNum()))
                .getView(R.id.sde_image)).setImageURL(item.getPurchaserLogo());
    }
}
