package com.hll_sc_app.app.order.place.select;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.place.ProductBean;
import com.hll_sc_app.widget.SimpleDecoration;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/17
 */

public class SelectGoodsAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {

    private final View.OnFocusChangeListener mListener;
    private final OnItemChildClickListener mClickListener;

    SelectGoodsAdapter(View.OnFocusChangeListener listener, OnItemChildClickListener clickListener) {
        super(R.layout.item_order_select_goods);
        mListener = listener;
        mClickListener = clickListener;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        RecyclerView listView = helper.getView(R.id.osg_spec_list);
        listView.setNestedScrollingEnabled(false);
        listView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        listView.setLayoutManager(new LinearLayoutManager(parent.getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        SelectGoodsSpecAdapter adapter = new SelectGoodsSpecAdapter(mListener);
        adapter.setOnItemChildClickListener(mClickListener);
        listView.setAdapter(adapter);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) listView.getItemAnimator()).setSupportsChangeAnimations(false);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {
        ((GlideImageView) helper.setText(R.id.osg_goods_name, item.getProductName())
                .setText(R.id.osg_goods_brief, item.getProductBrief())
                .setGone(R.id.osg_promotion, !TextUtils.isEmpty(item.getDiscountRuleTypeName()))
                .setGone(R.id.osg_self_support, item.getProductType() == 1)
                .setGone(R.id.osg_top, item.getTop() > 0)
                .setGone(R.id.osg_next_day, item.getNextDayDelivery() == 1)
                .setGone(R.id.osg_bundle, item.getBundlingGoodsType() == 1)
                .setText(R.id.osg_promotion, item.getDiscountRuleTypeName())
                .getView(R.id.osg_icon)).setImageURL(item.getImgUrl());
        RecyclerView listView = helper.getView(R.id.osg_spec_list);
        ((SelectGoodsSpecAdapter) listView.getAdapter()).setNewData(item.getSpecs(), item);
    }
}
