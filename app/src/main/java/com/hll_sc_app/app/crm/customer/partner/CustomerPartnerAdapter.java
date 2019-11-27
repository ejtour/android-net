package com.hll_sc_app.app.crm.customer.partner;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.PurchaserBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public class CustomerPartnerAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {
    CustomerPartnerAdapter() {
        super(R.layout.item_crm_customer_partner);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaserBean item) {
        ((GlideImageView) helper.setText(R.id.ccp_name, item.getPurchaserName())
                .setText(R.id.ccp_contact, String.format("%s/%s", item.getLinkman(), item.getMobile()))
                .setText(R.id.ccp_shop_num, String.format("合作%s个门店", item.getShopCount()))
                .getView(R.id.ccp_image)).setImageURL(item.getLogoUrl());
    }
}
