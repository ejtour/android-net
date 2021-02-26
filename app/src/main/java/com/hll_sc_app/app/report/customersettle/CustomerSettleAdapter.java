package com.hll_sc_app.app.report.customersettle;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleBean;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleResp;
import com.hll_sc_app.bean.window.NameValue;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

class CustomerSettleAdapter extends BaseQuickAdapter<CustomerSettleBean, BaseViewHolder> {
    CustomerSettleAdapter() {
        super(R.layout.item_report_customer_settle);
        setNewData(new CustomerSettleResp().convertToBeanList());
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        ((RecyclerView) helper.getView(R.id.rcs_list_view)).setAdapter(new SubCustomerSettleAdapter());
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerSettleBean item) {
        helper.setText(R.id.rcs_label, item.getLabel());
        ((SubCustomerSettleAdapter) ((RecyclerView) helper.getView(R.id.rcs_list_view)).getAdapter()).setNewData(item.getList());
    }

    static class SubCustomerSettleAdapter extends BaseQuickAdapter<NameValue, BaseViewHolder> {
        SubCustomerSettleAdapter() {
            super(R.layout.item_report_customer_settle_sub);
        }

        @Override
        protected void convert(BaseViewHolder helper, NameValue item) {
            helper.itemView.setBackgroundResource(helper.getAdapterPosition() % 2 == 0 ? R.drawable.bg_fafafa_radius_5_solid : android.R.color.transparent);
            helper.setText(R.id.css_label, item.getName())
                    .setText(R.id.css_amount, item.getValue());
        }
    }
}
