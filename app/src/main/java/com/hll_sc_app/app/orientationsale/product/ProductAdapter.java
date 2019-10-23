package com.hll_sc_app.app.orientationsale.product;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.orientation.OrientationDetailBean;

import java.util.List;


public class ProductAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private OnSpecClickListener mListener;
    ProductAdapter() {
        super(R.layout.item_orientation_product_list);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        RecyclerView recyclerView = holder.getView(R.id.list_view_spec);
        SpecAdapter specAdapter = new SpecAdapter(SpecAdapter.FLAG.CHECK);
        specAdapter.setOnItemClickListener((adapter, view, position) -> {
            SpecsBean specsBean = specAdapter.getItem(position);
            if (specsBean == null) {
                return;
            }
            //更新appointSellType的值
            int type = specsBean.getAppointSellType();
            specsBean.setAppointSellType(type == 1 ? 0 : 1);
            specAdapter.notifyDataSetChanged();

            GoodsBean goodsBean = getItem(holder.getAdapterPosition());
            if (goodsBean == null) {
                return;
            }
            goodsBean.setCheck(false);
            for (int i = 0; i < goodsBean.getSpecs().size(); i++) {
                if (goodsBean.getSpecs().get(i).getAppointSellType() == 1) {
                    goodsBean.setCheck(true);
                    break;
                }
            }
            if (mListener != null) {
                mListener.onClick(goodsBean);
            }
        });
        recyclerView.setAdapter(specAdapter);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        ((GlideImageView) helper.getView(R.id.img_logoUrl)).setImageURL(item.getImgUrl());
        helper.addOnClickListener(R.id.img_delete)
                .setText(R.id.txt_product_name, item.getProductName());
        RecyclerView recyclerView = helper.getView(R.id.list_view_spec);
        ((SpecAdapter) recyclerView.getAdapter()).setNewData(item.getSpecs());
    }

    public void setOnSpecClickListener(OnSpecClickListener listener) {
        mListener = listener;
    }

    public interface OnSpecClickListener {
        void onClick(GoodsBean goodsBean);
    }
}
