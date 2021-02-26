package com.hll_sc_app.app.crm.customer.seas.detail.shop;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public class CustomerSeasShopFragment extends BaseLazyFragment {
    @BindView(R.id.css_contact)
    TextView mContact;
    @BindView(R.id.css_phone)
    TextView mPhone;
    @BindView(R.id.css_time)
    TextView mTime;
    @BindView(R.id.css_region)
    TextView mRegion;
    @BindView(R.id.css_address)
    TextView mAddress;
    @BindView(R.id.css_group)
    TextView mGroup;
    @BindView(R.id.css_date)
    TextView mDate;
    @BindView(R.id.css_way)
    TextView mWay;
    @BindView(R.id.css_salesman)
    TextView mSalesman;
    @Autowired(name = "parcelable")
    PurchaserShopBean mBean;
    Unbinder unbinder;

    public static CustomerSeasShopFragment newInstance(PurchaserShopBean bean) {
        Bundle args = new Bundle();
        args.putParcelable("parcelable", bean);
        CustomerSeasShopFragment fragment = new CustomerSeasShopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_crm_customer_seas_shop, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        mContact.setText(mBean.getShopAdmin());
        mPhone.setText(PhoneUtil.formatPhoneNum(mBean.getShopPhone()));
        if (!TextUtils.isEmpty(mBean.getBusinessStartTime()) && !TextUtils.isEmpty(mBean.getBusinessEndTime())) {
            mTime.setText(String.format("%s-%s", mBean.getBusinessStartTime(), mBean.getBusinessEndTime()));
        }
        if (!TextUtils.isEmpty(mBean.getShopProvince()) && !TextUtils.isEmpty(mBean.getShopCity()) && !TextUtils.isEmpty(mBean.getShopDistrict())) {
            mRegion.setText(String.format("%s-%s-%s", mBean.getShopProvince(), mBean.getShopCity(), mBean.getShopDistrict()));
        }
        mAddress.setText(mBean.getShopAddress());
        mGroup.setText(mBean.getPurchaserName());
        mDate.setText(DateUtil.getReadableTime(mBean.getCooperationTime(), CalendarUtils.FORMAT_DATE_TIME));
        mWay.setText(CooperationDetailActivity.getSettlementWay(mBean.getSettlementWay()));
        mSalesman.setText(mBean.getSalesmanName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
