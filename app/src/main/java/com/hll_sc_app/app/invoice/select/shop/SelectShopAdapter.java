package com.hll_sc_app.app.invoice.select.shop;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/12
 */

public class SelectShopAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {
    public SelectShopAdapter() {
        super(R.layout.item_invoice_select_shop);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
        ((GlideImageView) helper.setText(R.id.iss_shop_name, item.getShopName())
                .setText(R.id.iss_group_name, item.getPurchaserName())
                .setText(R.id.iss_contact, String.format("联系人：%s/%s", item.getShopAdmin(), item.getShopPhone()))
                .getView(R.id.iss_image)).setImageURL(item.getImagePath());
    }
}
