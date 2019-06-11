package com.hll_sc_app.app.goods.list;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * 商品列表适配器
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public class GoodsListAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
    GoodsListAdapter(@Nullable List<GoodsBean> data) {
        super(R.layout.item_goods_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        ((GlideImageView) helper.getView(R.id.img_imgUrl)).setImageURL(item.getImgUrl());
        helper.setText(R.id.txt_productName, item.getProductName())
            .setText(R.id.txt_productCode, getMiddleContent(item))
            .setText(R.id.txt_specsSize, getBottomContent(item))
            .setGone(R.id.txt_nextDayDelivery, TextUtils.equals("1", item.getNextDayDelivery()));
    }

    private String getMiddleContent(GoodsBean item) {
        if (TextUtils.equals(GoodsBean.WAREHOUSE_TYPE, item.getIsWareHourse())) {
            // 代仓商品
            return "货主：" + item.getCargoOwnerName();
        } else {
            return "编码：" + item.getProductCode();
        }
    }

    private String getBottomContent(GoodsBean item) {
        if (TextUtils.equals(GoodsBean.BUNDLING_GOODS_TYPE, item.getBundlingGoodsType())) {
            // 组合商品
            return "包含：" + (CommonUtils.isEmpty(item.getBundlingGoodsDetails()) ? "0" :
                item.getBundlingGoodsDetails().size()) + "种明细商品";
        } else {
            return "规格：" + (CommonUtils.isEmpty(item.getSpecs()) ? "0" : item.getSpecs().size()) + "种";
        }
    }
}
