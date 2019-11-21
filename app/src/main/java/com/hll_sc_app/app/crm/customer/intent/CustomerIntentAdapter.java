package com.hll_sc_app.app.crm.customer.intent;

import android.app.Activity;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.CustomerHelper;
import com.hll_sc_app.app.crm.customer.intent.detail.CustomerDetailActivity;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.customer.CustomerBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

public class CustomerIntentAdapter extends BaseQuickAdapter<CustomerBean, BaseViewHolder> {
    CustomerIntentAdapter() {
        super(R.layout.item_crm_customer_intent);
        setOnItemChildClickListener((adapter, view, position) -> UIUtils.callPhone(view.getContext(), view.getTag().toString()));
        setOnItemClickListener((adapter, view, position) -> {
            CustomerDetailActivity.start(((Activity) view.getContext()), getItem(position));
        });
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.cci_phone);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerBean item) {
        helper.setImageResource(R.id.cci_level, CustomerHelper.getCustomerLevelFlag(item.getCustomerLevel()))
                .setText(R.id.cci_name, item.getCustomerName())
                .setText(R.id.cci_address, item.getCustomerAddress())
                .setText(R.id.cci_contact, item.getCustomerLinkman())
                .setTag(R.id.cci_phone, item.getCustomerPhone())
                .setText(R.id.cci_phone, PhoneUtil.formatPhoneNum(item.getCustomerPhone()));

    }
}
