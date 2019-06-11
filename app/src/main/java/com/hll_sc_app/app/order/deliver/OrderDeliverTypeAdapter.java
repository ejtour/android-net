package com.hll_sc_app.app.order.deliver;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.deliver.DeliverNumResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */

public class OrderDeliverTypeAdapter extends BaseQuickAdapter<DeliverNumResp.DeliverType, BaseViewHolder> {

    private int mSelectPos;

    public OrderDeliverTypeAdapter() {
        super(R.layout.item_order_deliver_type);
    }

    public void setSelectPos(int selectPos) {
        mSelectPos = selectPos;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, DeliverNumResp.DeliverType item) {
        helper.itemView.setSelected(mSelectPos == helper.getAdapterPosition());
        ((TextView) helper.itemView).setText(item.getValue());
    }
}
