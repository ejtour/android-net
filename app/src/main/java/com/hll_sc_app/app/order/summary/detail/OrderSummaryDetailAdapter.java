package com.hll_sc_app.app.order.summary.detail;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.summary.SummaryProductBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/28
 */

class OrderSummaryDetailAdapter extends BaseQuickAdapter<SummaryProductBean, BaseViewHolder> {
    public OrderSummaryDetailAdapter(@Nullable List<SummaryProductBean> data) {
        super(R.layout.item_order_summary_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SummaryProductBean item) {
        helper.itemView.setBackgroundResource(helper.getAdapterPosition() % 2 == 0 ? R.color.color_fafafa : android.R.color.transparent);
        helper.setText(R.id.osd_name, item.getProductName())
                .setText(R.id.osd_spec, item.getProductSpec())
                .setText(R.id.osd_num, CommonUtils.formatNum(item.getProductNum()) + item.getSaleUnitName());
    }
}
