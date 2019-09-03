package com.hll_sc_app.app.report.refund.search;

import android.support.annotation.Nullable;
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
       helper.setText(R.id.txt_search_name,item.getName());
    }
}
