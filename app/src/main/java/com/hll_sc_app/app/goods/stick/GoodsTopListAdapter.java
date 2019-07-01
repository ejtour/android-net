package com.hll_sc_app.app.goods.stick;

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
 * @date 2019-07-01
 */
public class GoodsTopListAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
    GoodsTopListAdapter() {
        super(R.layout.item_goods_top_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        helper.setText(R.id.txt_productName, item.getProductName())
            .setText(R.id.txt_productCode, getSpecContent(item))
            .setBackgroundRes(R.id.txt_top, getBackGroundRes(item))
            .setTextColor(R.id.txt_top, getTextColor(item))
            .setText(R.id.txt_top, item.isCheck() ? "取消置顶" : "置顶")
            .addOnClickListener(R.id.txt_top)
            .addOnClickListener(R.id.content);
        ((GlideImageView) helper.getView(R.id.img_imgUrl)).setImageURL(item.getImgUrl());
    }

    private String getSpecContent(GoodsBean item) {
        StringBuilder stringBuilder = new StringBuilder();
        List<SpecsBean> list = item.getSpecs();
        if (!CommonUtils.isEmpty(list)) {
            int count = list.size();
            for (int i = 0; i < count; i++) {
                SpecsBean bean = list.get(i);
                if (!TextUtils.isEmpty(bean.getSpecContent())) {
                    stringBuilder.append(bean.getSpecContent());
                    if (i != count - 1) {
                        stringBuilder.append("\n");
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private int getBackGroundRes(GoodsBean item) {
        int backgroundRes = R.drawable.bg_button_large_stroke_primary;
        if (item.isCheck()) {
            backgroundRes = R.drawable.bg_button_large_solid_primary;
        }
        return backgroundRes;
    }

    private int getTextColor(GoodsBean item) {
        int textColor = ContextCompat.getColor(mContext, R.color.base_colorPrimary);
        if (item.isCheck()) {
            textColor = ContextCompat.getColor(mContext, R.color.base_white);
        }
        return textColor;
    }
}
