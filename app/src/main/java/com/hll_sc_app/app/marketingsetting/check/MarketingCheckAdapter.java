package com.hll_sc_app.app.marketingsetting.check;

import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/4
 */

public class MarketingCheckAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private final boolean mClickable;

    public MarketingCheckAdapter(boolean clickable) {
        super(R.layout.item_purchaser_item);
        mClickable = clickable;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        if (!mClickable) {
            ((TextView) helper.itemView).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ((TextView) helper.itemView).setText(item);
    }
}
