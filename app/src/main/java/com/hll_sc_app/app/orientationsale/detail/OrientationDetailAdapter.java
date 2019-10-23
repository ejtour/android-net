package com.hll_sc_app.app.orientationsale.detail;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.orientationsale.product.SpecAdapter;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.orientation.OrientationDetailBean;
import com.hll_sc_app.bean.orientation.OrientationProductSpecBean;

import java.util.ArrayList;
import java.util.List;

public class OrientationDetailAdapter extends BaseQuickAdapter<OrientationDetailBean, BaseViewHolder> {
    private OnDelClickListener mListener;
    public OrientationDetailAdapter() {
        super(R.layout.item_orientation_detail_product);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        RecyclerView recyclerView = holder.getView(R.id.list_view_spec);
        SpecAdapter specAdapter = new SpecAdapter(SpecAdapter.FLAG.DELETE);
        recyclerView.setAdapter(specAdapter);
        specAdapter.setOnItemClickListener((adapter, view, position) -> {
            specAdapter.getData().remove(position);
            specAdapter.notifyDataSetChanged();
            if (mListener != null) {
                mListener.remove(holder.getAdapterPosition(), position);
            }
        });
        return holder;
    }


    @Override
    protected void convert(BaseViewHolder helper, OrientationDetailBean item) {

        ((GlideImageView) helper.getView(R.id.img_logoUrl)).setImageURL(item.getImgUrl());
        helper.addOnClickListener(R.id.img_delete)
                .setText(R.id.txt_product_name, item.getProductName());
        RecyclerView recyclerView = helper.getView(R.id.list_view_spec);
        /*转类型*/
        List<SpecsBean> specsBeans = new ArrayList<>();
        for (OrientationProductSpecBean orientationProductSpecBean : item.getSpecs()) {
            SpecsBean specsBean = new SpecsBean();
            specsBean.setProductPrice(orientationProductSpecBean.getProductPrice().toString());
            specsBean.setSpecContent(orientationProductSpecBean.getSpecContent());
            specsBeans.add(specsBean);
        }
        ((SpecAdapter) recyclerView.getAdapter()).setNewData(specsBeans);
    }


    public void setOnDelClickListener(OnDelClickListener listener) {
        mListener = listener;
    }

    public interface OnDelClickListener {
        void remove(int productIndex, int specIndex);
    }
}
