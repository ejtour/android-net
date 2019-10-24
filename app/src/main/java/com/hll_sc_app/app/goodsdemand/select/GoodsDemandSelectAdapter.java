package com.hll_sc_app.app.goodsdemand.select;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.GoodsBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/24
 */

public class GoodsDemandSelectAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private GoodsBean mCurBean;

    GoodsDemandSelectAdapter() {
        super(R.layout.item_goods_demand_select);
        setOnItemClickListener((adapter, view, position) -> {
            select(getItem(position));
        });
    }

    public void select(GoodsBean bean) {
        mCurBean = bean;
        notifyDataSetChanged();
    }

    GoodsBean getCurBean() {
        return mCurBean;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        ((GlideImageView) helper.setText(R.id.gds_name, item.getProductName())
                .setText(R.id.gds_code, String.format("编码：%s", item.getProductCode()))
                .setText(R.id.gds_spec, String.format("规格：%s种", item.getSpecs().size()))
                .setGone(R.id.gds_check, item.equals(mCurBean))
                .getView(R.id.gds_icon)).setImageURL(item.getImgUrl());
    }
}
