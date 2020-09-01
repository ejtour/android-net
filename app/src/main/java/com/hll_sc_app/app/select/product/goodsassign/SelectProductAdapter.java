package com.hll_sc_app.app.select.product.goodsassign;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.assign.GoodsAssignType;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/26
 */
class SelectProductAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
    private final GoodsAssignType mType;
    private OnItemClickListener mListener;

    SelectProductAdapter(GoodsAssignType type) {
        super(R.layout.item_goods_assign_detail);
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        helper.setText(R.id.gad_name, item.getProductName());
        ((GlideImageView) helper.getView(R.id.gad_image)).setImageURL(item.getImgUrl());
        ((SpecAdapter) ((RecyclerView) helper.getView(R.id.gad_list_view)).getAdapter()).setNewData(item.getSpecs(), item);
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        RecyclerView listView = helper.getView(R.id.gad_list_view);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) listView.getItemAnimator()).setSupportsChangeAnimations(false);
        listView.setLayoutManager(new LinearLayoutManager(parent.getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        SpecAdapter specAdapter = new SpecAdapter();
        listView.setAdapter(specAdapter);
        specAdapter.setOnItemClickListener(mListener);
        return helper;
    }

    class SpecAdapter extends BaseQuickAdapter<SpecsBean, BaseViewHolder> {

        private GoodsBean mBean;


        public SpecAdapter() {
            super(R.layout.item_goods_assign_detail_spec);
        }

        @Override
        protected void convert(BaseViewHolder helper, SpecsBean item) {
            helper.itemView.setTag(mBean);
            helper.setText(R.id.ads_name, item.getSpecContent())
                    .setText(R.id.ads_price, String.format("¥%s", CommonUtils.formatMoney(CommonUtils.getDouble(item.getProductPrice()))));
            View view = helper.getView(R.id.ads_btn);
            boolean clickable = mType == GoodsAssignType.TARGET_SALE ? item.getBlacklist() == 0 : item.getAppointSellType() == 0;
            view.setEnabled(clickable);
            helper.itemView.setClickable(clickable);
            view.setSelected(item.isSelect());
        }

        public void setNewData(@Nullable List<SpecsBean> data, GoodsBean bean) {
            super.setNewData(data);
            mBean = bean;
        }
    }
}
