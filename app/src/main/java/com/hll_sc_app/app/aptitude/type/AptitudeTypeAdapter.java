package com.hll_sc_app.app.aptitude.type;

import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.aptitude.AptitudeBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/11/26
 */
class AptitudeTypeAdapter extends BaseQuickAdapter<AptitudeBean, BaseViewHolder> {
    public AptitudeTypeAdapter() {
        super(R.layout.item_aptitude_type);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.iat_name).addOnClickListener(R.id.iat_del);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, AptitudeBean item) {
        helper.setText(R.id.iat_name, item.getAptitudeName())
                .setGone(R.id.iat_del, !"0".equals(item.getGroupID()))
                .setTextColor(R.id.iat_name, ContextCompat.getColor(helper.itemView.getContext(),
                        !item.isSelectable() ? R.color.color_999999 :
                                item.isSelected() ? R.color.colorPrimary : R.color.color_666666))
                .getView(R.id.iat_name).setEnabled(item.isSelectable());
    }
}
