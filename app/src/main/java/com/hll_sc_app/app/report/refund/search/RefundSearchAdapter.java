package com.hll_sc_app.app.report.refund.search;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.report.search.SearchResultItem;


public class RefundSearchAdapter extends BaseQuickAdapter<SearchResultItem, BaseViewHolder> {


    RefundSearchAdapter() {
        super(R.layout.item_report_search_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResultItem item) {
        ((TextView) helper.itemView).setText(item.getName());
    }
}
