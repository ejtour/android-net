package com.hll_sc_app.app.aptitude.goods.detail;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.aptitude.AptitudeProductBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 11/30/20.
 */
class AptitudeGoodsDetailAdapter extends BaseQuickAdapter<AptitudeProductBean, BaseViewHolder> {
    private boolean mEditable;

    AptitudeGoodsDetailAdapter() {
        super(R.layout.item_aptitude_goods_detail);
    }

    public void setEditable(boolean editable) {
        mEditable = editable;
        notifyDataSetChanged();
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        if (mEditable) {
            ((TextView) helper.getView(R.id.agd_batch)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    AptitudeProductBean item = getItem(helper.getAdapterPosition());
                    if (item == null) return;
                    item.setProductBatch(s.toString());
                }
            });
        }
        helper.addOnClickListener(R.id.agd_remove);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, AptitudeProductBean item) {
        AptitudeProductBean.ProductInfoBean info = item.getAptitudeProduct();
        helper.setText(R.id.agd_name, info.getProductName())
                .setText(R.id.agd_spec_num, String.format("%s种规格", CommonUtils.formatNum(CommonUtils.getInt(info.getSaleSpecNum()))))
                .setGone(R.id.agd_remove, mEditable)
                .setBackgroundRes(R.id.agd_batch, mEditable ? R.drawable.bg_goods_search : 0)
                .setText(R.id.agd_batch, item.getProductBatch());
        ((GlideImageView) helper.getView(R.id.agd_image)).setImageURL(info.getImgUrl());
        TextView textView = helper.getView(R.id.agd_batch);
        textView.setEnabled(mEditable);
        textView.setSingleLine(mEditable);
        textView.setGravity(mEditable ? Gravity.CENTER : Gravity.CENTER_VERTICAL | Gravity.RIGHT);
    }
}
