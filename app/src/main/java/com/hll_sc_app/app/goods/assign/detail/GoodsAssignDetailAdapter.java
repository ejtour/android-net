package com.hll_sc_app.app.goods.assign.detail;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.GoodsAssignDetailBean;
import com.hll_sc_app.bean.goods.GoodsAssignSpecBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/26
 */
public class GoodsAssignDetailAdapter extends BaseQuickAdapter<GoodsAssignDetailBean, BaseViewHolder> {

    public GoodsAssignDetailAdapter() {
        super(R.layout.item_goods_assign_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsAssignDetailBean item) {
        helper.setText(R.id.gad_name, item.getProductName());
        ((GlideImageView) helper.getView(R.id.gad_image)).setImageURL(item.getImgUrl());
        ((SpecAdapter) ((RecyclerView) helper.getView(R.id.gad_list_view)).getAdapter()).setNewData(item.getSpecs());
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        RecyclerView listView = helper.getView(R.id.gad_list_view);
        listView.setLayoutManager(new LinearLayoutManager(parent.getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        SpecAdapter specAdapter = new SpecAdapter();
        listView.setAdapter(specAdapter);
        specAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (adapter.getItemCount() == 1) {
                remove(helper.getAdapterPosition());
            } else {
                adapter.remove(position);
            }
        });
        return helper;
    }

    private static class SpecAdapter extends BaseQuickAdapter<GoodsAssignSpecBean, BaseViewHolder> {

        SpecAdapter() {
            super(R.layout.item_goods_assign_detail_spec);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsAssignSpecBean item) {
            helper.setText(R.id.ads_name, item.getSpecContent())
                    .setText(R.id.ads_price, String.format("¥%s", CommonUtils.formatMoney(CommonUtils.getDouble(item.getProductPrice()))));
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
            holder.setImageResource(R.id.ads_btn, R.drawable.ic_delete);
            return holder;
        }
    }
}
