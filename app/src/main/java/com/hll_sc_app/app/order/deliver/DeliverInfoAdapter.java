package com.hll_sc_app.app.order.deliver;

import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.deliver.DeliverInfoResp;
import com.hll_sc_app.citymall.util.CommonUtils;
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
        listView.setAdapter(new DeliverShopAdapter());
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, DeliverInfoResp item) {
        helper.setText(R.id.odi_name, item.getProductName())
                .setText(R.id.odi_spec, "规格：" + item.getProductSpec())
                .setGone(R.id.odi_tag, item.getShipperType() > 0)
                .setText(R.id.odi_unit, String.format("x %s %s", CommonUtils.formatNum(item.getProductNum()), item.getSaleUnitName()));
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
