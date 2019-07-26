package com.hll_sc_app.app.refundtime;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.refundtime.RefundTimeBean;

public class RefundTimeAdapter extends BaseQuickAdapter<RefundTimeBean, BaseViewHolder> {

    RefundTimeAdapter() {
        super(R.layout.item_refund_time);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundTimeBean item) {
        helper.setText(R.id.txt_product_category_name, item.getCategoryName());
        if(item.getNum() == null || item.getNum() == 0) {
            helper.setText(R.id.txt_refund_time_value, "不可退货 ");
        } else {
            helper.setText(R.id.txt_refund_time_value, item.getNum().toString() + "天 ");
        }
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
        viewHolder.addOnClickListener(R.id.rl_refund_time_detail);
        return viewHolder;
    }
}
