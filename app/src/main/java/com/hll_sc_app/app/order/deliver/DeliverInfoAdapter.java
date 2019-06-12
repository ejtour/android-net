package com.hll_sc_app.app.order.deliver;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.deliver.DeliverInfoResp;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TriangleView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */

public class DeliverInfoAdapter extends BaseQuickAdapter<DeliverInfoResp, BaseViewHolder> {
    DeliverInfoAdapter() {
        super(R.layout.item_order_deliver_info);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        RecyclerView listView = holder.getView(R.id.odi_list_view);
        listView.setNestedScrollingEnabled(false);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(parent.getContext(), R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(20), 0, UIUtils.dip2px(10), 0);
        listView.addItemDecoration(decor);
        listView.setAdapter(new DeliverShopAdapter());
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, DeliverInfoResp item) {
        helper.setText(R.id.odi_name, item.getProductName())
                .setText(R.id.odi_spec, item.getProductSpec())
                .setText(R.id.odi_unit, String.format("x %s %s", item.getProductNum(), item.getSaleUnitName()));
        ((GlideImageView) helper.getView(R.id.odi_image)).setImageURL(item.getImgUrl());
        TriangleView arrow = helper.getView(R.id.odi_arrow);
        RecyclerView listView = helper.getView(R.id.odi_list_view);
        if (item.isExpanded()) {
            arrow.update(TriangleView.TOP, ContextCompat.getColor(helper.itemView.getContext(), R.color.colorPrimary));
            listView.setVisibility(View.VISIBLE);
            ((DeliverShopAdapter) listView.getAdapter()).setNewData(item.getList());
        } else {
            listView.setVisibility(View.GONE);
            arrow.update(TriangleView.BOTTOM, ContextCompat.getColor(helper.itemView.getContext(), R.color.colorPrimary));
        }
    }
}
