package com.hll_sc_app.app.complainmanage.detail;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

public class ProductAdapter extends BaseQuickAdapter<SkuGoodsBean, BaseViewHolder> {

    public ProductAdapter(@Nullable List<SkuGoodsBean> data) {
        super(R.layout.list_item_complain_product, data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.setGone(R.id.check_view, false)
                .setGone(R.id.img_del, false);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, SkuGoodsBean item) {
        ((GlideImageView) helper.setText(R.id.txt_product_name, item.getProductName())
                .setText(R.id.txt_group_name, item.getSupplierName())
                .setText(R.id.txt_spec, item.getSpecContent())
                .setText(R.id.txt_price, getPrice(item.getProductPrice(), item.getSaleUnitName()))
                .getView(R.id.img_product))
                .setImageURL(item.getImgUrl());
    }

    private SpannableString getPrice(String price, String unit) {
        if (price == null) {
            price = "";
        }
        if (unit == null) {
            unit = "";
        }
        String out = "¥" + CommonUtils.formatMoney(Double.parseDouble(price)) + "/" + unit;
        SpannableString spannableString = new SpannableString(out);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), 1, out.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
