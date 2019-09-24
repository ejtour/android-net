package com.hll_sc_app.app.order.place.confirm.details;


import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.place.DiscountPlanBean;
import com.hll_sc_app.bean.order.place.ProductBean;
import com.hll_sc_app.bean.order.place.ProductSpecBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.order.OrderDepositList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/24
 */

public class PlaceOrderDetailsAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {
    PlaceOrderDetailsAdapter(@Nullable List<ProductBean> data) {
        super(R.layout.item_order_place_details, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {
        ((GlideImageView) helper.setText(R.id.opd_product_name, item.getProductName())
                .getView(R.id.opd_icon)).setImageURL(item.getImgUrl());
        if (!CommonUtils.isEmpty(item.getSpecs())) {
            ProductSpecBean bean = item.getSpecs().get(0);
            helper.setGone(R.id.opd_promotion_group, bean.getDiscount() != null)
                    .setText(R.id.opd_product_spec, bean.getSpecContent())
                    .setText(R.id.opd_buy_num, String.format("x%s", CommonUtils.formatNum(bean.getShopcartNum())))
                    .setGone(R.id.opd_deposit_group, !CommonUtils.isEmpty(bean.getDepositProducts()))
                    .setText(R.id.opd_unit_price, processUnitPrice(bean.getProductPrice(), bean.getSaleUnitName()));
            ((OrderDepositList) helper.getView(R.id.opd_deposit_list)).setData(bean.getDepositProducts());
            if (bean.getDiscount() != null) {
                List<String> list = new ArrayList<>();
                if (!CommonUtils.isEmpty(bean.getDiscount().getRuleList())) {
                    for (DiscountPlanBean.DiscountRuleBean ruleBean : bean.getDiscount().getRuleList()) {
                        list.add(ruleBean.getRuleName());
                    }
                }
                helper.setText(R.id.opd_promotion, bean.getDiscount().getRuleName())
                        .setText(R.id.opd_promotion_flag, bean.getDiscount().getRuleName())
                        .setText(R.id.opd_promotion_desc, TextUtils.join("\n", list));
            }
        }
    }

    private SpannableString processUnitPrice(double price, String unit) {
        StringBuilder source = new StringBuilder("¥").append(CommonUtils.formatMoney(price));
        if (!TextUtils.isEmpty(unit))
            source.append(" / ").append(unit);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.3f), 1, source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
