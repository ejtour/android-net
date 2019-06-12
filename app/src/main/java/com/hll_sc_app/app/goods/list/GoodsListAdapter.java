package com.hll_sc_app.app.goods.list;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
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
        if (TextUtils.equals(item.getProductStatus(), GoodsBean.PRODUCT_STATUS_DISABLE)) {
            ((GlideImageView) helper
                .setGone(R.id.txt_disableTips, true)
                .setTextColor(R.id.txt_productName, ContextCompat.getColor(mContext, R.color.color_aeaeae))
                .setTextColor(R.id.txt_productCode, ContextCompat.getColor(mContext, R.color.color_aeaeae))
                .setTextColor(R.id.txt_specsSize, ContextCompat.getColor(mContext, R.color.color_aeaeae))
                .getView(R.id.img_imgUrl)).setDisableImageUrl(item.getImgUrl());
        } else {
            ((GlideImageView) helper
                .setGone(R.id.txt_disableTips, false)
                .setTextColor(R.id.txt_productName, ContextCompat.getColor(mContext, R.color.color_222222))
                .setTextColor(R.id.txt_productCode, ContextCompat.getColor(mContext, R.color.color_999999))
                .setTextColor(R.id.txt_specsSize, ContextCompat.getColor(mContext, R.color.color_999999))
                .getView(R.id.img_imgUrl)).setImageURL(item.getImgUrl());
        }
        helper.setText(R.id.txt_productName, item.getProductName())
            .setText(R.id.txt_productCode, getMiddleContent(item))
            .setText(R.id.txt_specsSize, getBottomContent(item))
            .setGone(R.id.txt_nextDayDelivery, TextUtils.equals("1", item.getNextDayDelivery()))
            .addOnClickListener(R.id.txt_specStatus)
            .setText(R.id.txt_specStatus, isUp(item) ? "下架\n商品" : "上架\n商品");
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

    public static boolean isUp(GoodsBean item) {
        if (!CommonUtils.isEmpty(item.getSpecs())) {
            return TextUtils.equals(item.getSpecs().get(0).getSpecStatus(), SpecsBean.SPEC_STATUS_UP);
        }
        return false;
    }
}
