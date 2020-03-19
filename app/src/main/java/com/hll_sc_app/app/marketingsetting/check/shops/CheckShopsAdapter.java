package com.hll_sc_app.app.marketingsetting.check.shops;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/4
 */

public class CheckShopsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    CheckShopsAdapter(@Nullable List<String> data) {
        super(R.layout.item_report_search_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ((TextView) helper.itemView).setText(item);
    }
}
