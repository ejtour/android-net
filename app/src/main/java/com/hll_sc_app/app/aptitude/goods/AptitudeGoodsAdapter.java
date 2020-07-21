package com.hll_sc_app.app.aptitude.goods;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.goods.edit.AptitudeGoodsEditActivity;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.GoodsBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/10
 */

class AptitudeGoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
    public AptitudeGoodsAdapter() {
        super(R.layout.item_aptitude_goods);
        setOnItemClickListener((adapter, view, position) -> AptitudeGoodsEditActivity.start(((Activity) view.getContext()), getItem(position)));
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        ((GlideImageView) helper.setText(R.id.iag_name, item.getProductName())
                .setText(R.id.iag_spec, "规格：" + item.getSaleSpecNum() + "种")
                .setText(R.id.iag_code, "编码：" + item.getProductCode())
                .getView(R.id.iag_image)).setImageURL(item.getImgUrl());
    }
}
