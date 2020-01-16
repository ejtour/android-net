package com.hll_sc_app.app.purchasetemplate;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.user.PurchaseTemplateBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.zs.border.view.BorderTextView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/4
 */

public class PurchaseTemplateAdapter extends BaseQuickAdapter<PurchaseTemplateBean, BaseViewHolder> {
    PurchaseTemplateAdapter() {
        super(R.layout.item_purchase_template);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaseTemplateBean item) {
        ((GlideImageView) helper.setText(R.id.ipt_name, item.getProductName())
                .setText(R.id.ipt_code, "编码：" + item.getSkuCode())
                .setText(R.id.ipt_spec, "规格：" + item.getSpecContent())
                .setText(R.id.ipt_price, "售价：" + CommonUtils.formatMoney(item.getProductPrice()))
                .setText(R.id.ipt_agreement_price,
                        "协议价：" + (0 == item.getPremiumPrice() ? "- -" : CommonUtils.formatMoney(item.getPremiumPrice())))
                .setText(R.id.ipt_cost_price, "成本价：¥" + (0 == item.getCostPrice() ? "- -" : CommonUtils.formatMoney(item.getCostPrice())))
                .setVisible(R.id.txt_status, true)
                .setVisible(R.id.txt_status_4, item.getSpecStatus() == 4)
                .setVisible(R.id.txt_status_5, item.getSpecStatus() == 5)
                .getView(R.id.ipt_image)).setImageURL(item.getImgUrl());
    }

}
