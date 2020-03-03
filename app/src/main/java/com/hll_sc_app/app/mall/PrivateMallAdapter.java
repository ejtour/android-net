package com.hll_sc_app.app.mall;

import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.mall.PrivateMallBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/27
 */

public class PrivateMallAdapter extends BaseQuickAdapter<PrivateMallBean, BaseViewHolder> {
    PrivateMallAdapter() {
        super(R.layout.item_private_mall);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.ipm_share)
                .addOnClickListener(R.id.ipm_copy);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, PrivateMallBean item) {
        GlideImageView imageView = helper.getView(R.id.ipm_image);
        TextView title = helper.getView(R.id.ipm_title);
        helper.setText(R.id.ipm_title, item.getTitle())
                .setText(R.id.ipm_desc, item.getDesc())
                .setTag(R.id.ipm_share, imageView)
                .setTag(R.id.ipm_copy, item.getLink());
        imageView.setImageURL(item.getImgUrl());
        title.setCompoundDrawablesWithIntrinsicBounds(item.getIcon(), 0, 0, 0);
    }
}
