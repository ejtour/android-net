package com.hll_sc_app.widget.customer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.CustomerHelper;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.bean.customer.CustomerBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/21
 */

public class CustomerIntentDetailView extends ScrollView {
    @BindView(R.id.cid_type)
    TextView mType;
    @BindView(R.id.cid_num)
    TextView mNum;
    @BindView(R.id.cid_region)
    TextView mRegion;
    @BindView(R.id.cid_address)
    TextView mAddress;
    @BindView(R.id.cid_contact)
    TextView mContact;
    @BindView(R.id.cid_phone)
    TextView mPhone;

    public CustomerIntentDetailView(Context context) {
        this(context, null);
    }

    public CustomerIntentDetailView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerIntentDetailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_crm_customer_intent_detail, this);
        ButterKnife.bind(this, view);
    }

    public void setData(CustomerBean data) {
        mType.setText(CustomerHelper.getCustomerType(data.getCustomerType()));
        mNum.setText(String.valueOf(data.getShopCount()));
        mRegion.setText(String.format("%s%s%s", data.getCustomerProvince(), data.getCustomerCity(), data.getCustomerDistrict()));
        mAddress.setText(data.getCustomerAddress());
        mContact.setText(data.getCustomerLinkman());
        mPhone.setText(PhoneUtil.formatPhoneNum(data.getCustomerPhone()));
    }
}
