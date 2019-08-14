package com.hll_sc_app.app.marketingsetting.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.SkuGoodsBean;

import java.util.List;

/**
 * 营销中心 活动商品展示 删除的适配器
 */
public class MarketingProductAdapter extends BaseQuickAdapter<SkuGoodsBean, BaseViewHolder> {
    private boolean isEdit;

    public MarketingProductAdapter(@Nullable List<SkuGoodsBean> data, boolean isEdit) {
        super(R.layout.list_item_marketing_product, data);
        this.isEdit = isEdit;
    }

    @Override
    protected void convert(BaseViewHolder helper, SkuGoodsBean item) {
        helper.setVisible(R.id.img_delete, isEdit)
                .setText(R.id.txt_product_name, item.getProductName())
                .setText(R.id.txt_spec_content, item.getSpecContent())
                .setText(R.id.txt_product_code, "编码：" + item.getProductCode())
                .setText(R.id.txt_product_price, "¥" + item.getProductPrice());

        GlideImageView productImage = helper.getView(R.id.img_product);
        productImage.setImageURL(item.getImgUrl());
        helper.addOnClickListener(R.id.img_delete);
    }
}

