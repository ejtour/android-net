package com.hll_sc_app.app.orientationsale.product;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.orientation.OrientationDetailBean;

import java.math.BigDecimal;
import java.util.List;


public class ProductAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    List<OrientationDetailBean> mProductList;

    ProductAdapter(List<OrientationDetailBean> mProductList) {
        super(R.layout.item_orientation_product_list);
        this.mProductList = mProductList;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        StringBuffer specName = new StringBuffer();
        StringBuffer price = new StringBuffer();
        for (SpecsBean spec : item.getSpecs()) {
            specName.append(spec.getSpecContent() + "\n");
            price.append(String.format("%.2f", new BigDecimal(spec.getProductPrice())) + "\n");
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
        helper.getView(R.id.img_select).setSelected(item.isSelect());
    }
}
