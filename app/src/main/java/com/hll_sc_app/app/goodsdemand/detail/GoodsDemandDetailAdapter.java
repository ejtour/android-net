package com.hll_sc_app.app.goodsdemand.detail;

import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.GoodsDemandHelper;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandItem;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.ThumbnailView;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

public class GoodsDemandDetailAdapter extends BaseQuickAdapter<GoodsDemandItem, BaseViewHolder> {
    private int mWidth;
    GoodsDemandDetailAdapter(List<GoodsDemandItem> data) {
        super(R.layout.item_goods_demand_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDemandItem item) {
        int color = GoodsDemandHelper.getColor(item.getDemandType());
        ThumbnailView thumbnailView = helper.setText(R.id.gdd_type, GoodsDemandHelper.getType(item.getDemandType()))
                .setTextColor(R.id.gdd_type, color)
                .setText(R.id.gdd_content, item.getDemandContent())
                .setGone(R.id.gdd_pic_group, !TextUtils.isEmpty(item.getDemandUrl()))
                .getView(R.id.gdd_pic_group);
        if (item.getDemandUrl() != null) thumbnailView.setData(item.getDemandUrl().split(","));
        GradientDrawable drawable = (GradientDrawable) helper.getView(R.id.gdd_type).getBackground();
        if (mWidth == 0) mWidth = ViewUtils.dip2px(helper.itemView.getContext(), 0.5f);
        drawable.setStroke(mWidth, color);
    }
}
