package com.hll_sc_app.app.setting.tax.goodsselect;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.citymall.util.LogUtil;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/1
 */

public class GoodsSelectAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private List<String> mIds;

    GoodsSelectAdapter() {
        super(R.layout.item_goods_select);
    }

    void setCanNotSelectIds(List<String> ids) {
        mIds = ids;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        helper.setText(R.id.igs_name, item.getProductName())
                .setText(R.id.igs_tip, "规格：" + item.getSpecs().size() + "种");
        ((GlideImageView) helper.getView(R.id.igs_image)).setImageURL(item.getImgUrl());
        boolean enabled = !mIds.contains(item.getProductID());
        View view = helper.getView(R.id.igs_check);
        view.setSelected(item.isCheck());
        view.setEnabled(enabled);
        helper.itemView.setEnabled(enabled);
    }
}
