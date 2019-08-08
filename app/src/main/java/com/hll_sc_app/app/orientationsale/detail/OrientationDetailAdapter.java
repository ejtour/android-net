package com.hll_sc_app.app.orientationsale.detail;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.orientation.OrientationDetailBean;
import com.hll_sc_app.bean.orientation.OrientationProductSpecBean;

import java.math.BigDecimal;

public class OrientationDetailAdapter extends BaseQuickAdapter<OrientationDetailBean, BaseViewHolder> {

    public OrientationDetailAdapter() {
        super(R.layout.item_orientation_detail_product);
    }
    @Override
    protected void convert(BaseViewHolder helper, OrientationDetailBean item) {
        StringBuffer specName = new StringBuffer();
        StringBuffer price = new StringBuffer();
        for (OrientationProductSpecBean spec : item.getSpecs()) {
            specName.append(spec.getSpecContent() + "\n");
            price.append(String.format("%.2f", spec.getProductPrice()) + "\n");
        }
        if(specName != null && specName.length() > 1) {
            specName.deleteCharAt(specName.length() - 1);
        }
        if(price != null && price.length() > 1) {
            price.deleteCharAt(price.length() - 1);
        }
        ((GlideImageView) helper.getView(R.id.img_logoUrl)).setImageURL(item.getImgUrl());
        helper.addOnClickListener(R.id.img_delete)
                .setText(R.id.txt_product_name, item.getProductName())
                .setText(R.id.txt_spec_name, specName)
                .setText(R.id.txt_price, price);
    }
}
