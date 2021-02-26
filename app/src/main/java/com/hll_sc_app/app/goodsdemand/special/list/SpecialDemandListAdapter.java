package com.hll_sc_app.app.goodsdemand.special.list;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.GoodsDemandHelper;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandItem;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandBean;
import com.hll_sc_app.citymall.util.ViewUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */

public class SpecialDemandListAdapter extends BaseQuickAdapter<SpecialDemandBean, BaseViewHolder> {
    private int mWidth;

    SpecialDemandListAdapter() {
        super(R.layout.item_goods_special_demand_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecialDemandBean item) {
        helper.setText(R.id.sdl_name, item.getProductName());
        ((GlideImageView) helper.getView(R.id.sdl_image)).setImageURL(item.getProductImgUrl());
        LinearLayout tags = helper.getView(R.id.sdl_tags);
        tags.removeAllViews();
        for (GoodsDemandItem bean : item.getDemandList()) {
            tags.addView(createTag(bean.getDemandType(), tags.getContext()));
        }
    }

    private TextView createTag(int type, Context context) {
        TextView textView = new TextView(context);
        int color = GoodsDemandHelper.getColor(type);
        String content = GoodsDemandHelper.getType(type);
        textView.setPadding(UIUtils.dip2px(5), UIUtils.dip2px(3), UIUtils.dip2px(5), UIUtils.dip2px(3));
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.bg_goods_demand_tag);
        if (mWidth == 0) mWidth = ViewUtils.dip2px(context, 0.5f);
        if (drawable != null) drawable.setStroke(mWidth, color);
        textView.setBackground(drawable);
        textView.setText(content);
        textView.setTextColor(color);
        textView.setTextSize(10);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = UIUtils.dip2px(5);
        textView.setLayoutParams(layoutParams);
        return textView;
    }
}
