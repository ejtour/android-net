package com.hll_sc_app.app.wallet.details.list;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.wallet.details.DetailsRecordWrapper;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/6
 */
public class DetailsListAdapter extends BaseSectionQuickAdapter<DetailsRecordWrapper, BaseViewHolder> {
    DetailsListAdapter() {
        super(R.layout.item_wallet_details, R.layout.item_wallet_details_header, null);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
        viewHolder.itemView.setTag(viewType == SECTION_HEADER_VIEW);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (getDefItemViewType(position) == SECTION_HEADER_VIEW) {
            setFullSpan(holder);
            convertHead(holder, getItem(position - getHeaderLayoutCount()));
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    protected void convertHead(BaseViewHolder helper, DetailsRecordWrapper item) {
        ((TextView) helper.itemView).setText(item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailsRecordWrapper item) {
        helper.setText(R.id.iwd_time, item.t.getAccountTime())
                .setText(R.id.iwd_delta, item.t.getDelta())
                .setText(R.id.iwd_balance, "余额：" + item.t.getTransAfterBalance())
                .setText(R.id.iwd_label, item.t.getTransTypeDesc());
    }
}
