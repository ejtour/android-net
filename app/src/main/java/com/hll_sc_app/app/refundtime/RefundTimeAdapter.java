package com.hll_sc_app.app.refundtime;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.refundtime.RefundTimeBean;

public class RefundTimeAdapter extends BaseQuickAdapter<RefundTimeBean, BaseViewHolder> {
    private int status;

    RefundTimeAdapter() {
        super(R.layout.item_refund_time);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundTimeBean item) {
        helper
            .setText(R.id.txt_product_category_name, item.getCategoryName())
            .setGone(R.id.img_arrow, status == 1);
        if (item.getNum() == null || item.getNum() == 0) {
            helper.setText(R.id.txt_refund_time_value, "不可退货 ");
        } else {
            helper.setText(R.id.txt_refund_time_value, item.getNum().toString() + "天 ");
        }
    }
}
