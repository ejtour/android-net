package com.hll_sc_app.app.cooperation.detail.details.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.app.cooperation.detail.details.BaseCooperationDetailsFragment;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 合作采购商详情-详细资料-基本信息
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public class CooperationDetailsBasicFragment extends BaseCooperationDetailsFragment implements CooperationDetailsBasicContract.IGoodsRelevanceListView {
    Unbinder unbinder;
    @BindView(R.id.img_logoUrl)
    GlideImageView mImgLogoUrl;
    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.txt_resourceType)
    TextView mTxtResourceType;
    @BindView(R.id.txt_groupCity)
    TextView mTxtGroupCity;
    @BindView(R.id.txt_groupAddress)
    TextView mTxtGroupAddress;
    @BindView(R.id.txt_linkman)
    TextView mTxtLinkman;
    @BindView(R.id.txt_mobile)
    TextView mTxtMobile;
    @BindView(R.id.txt_fax)
    TextView mTxtFax;
    @BindView(R.id.txt_groupMail)
    TextView mTxtGroupMail;
    @BindView(R.id.txt_defaultSettlementWay)
    TextView mTxtDefaultSettlementWay;
    @BindView(R.id.txt_maintainLevel)
    TextView mTxtMaintainLevel;
    @BindView(R.id.txt_defaultDeliveryWay)
    TextView mTxtDefaultDeliveryWay;
    @BindView(R.id.txt_customerLevel)
    TextView mTxtCustomerLevel;
    @BindView(R.id.txt_agreeTime)
    TextView mTxtAgreeTime;
    @BindView(R.id.txt_deliveryPeriod)
    TextView mTxtDeliveryPeriod;
    @BindView(R.id.ll_status0)
    LinearLayout mLlStatus0;
    private CooperationDetailsBasicPresenter mPresenter;
    private CooperationPurchaserDetail mDetail;

    public static CooperationDetailsBasicFragment newInstance(CooperationPurchaserDetail bean) {
        Bundle args = new Bundle();
        args.putParcelable("parcelable", bean);
        CooperationDetailsBasicFragment fragment = new CooperationDetailsBasicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = CooperationDetailsBasicPresenter.newInstance();
        mPresenter.register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDetail = bundle.getParcelable("parcelable");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cooperation_details_basic, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        showView();
        return rootView;
    }

    private void showView() {
        mImgLogoUrl.setImageURL(mDetail.getLogoUrl());
        mTxtName.setText(mDetail.getName());
        mTxtResourceType.setText(getResourceType(mDetail.getResourceType()));

        mTxtGroupCity.setText(String.format("%s-%s", mDetail.getGroupCity(), mDetail.getGroupDistrict()));
        mTxtGroupAddress.setText(checkNull(mDetail.getGroupAddress()));
        mTxtLinkman.setText(checkNull(mDetail.getLinkman()));
        mTxtMobile.setText(checkNull(mDetail.getMobile()));
        mTxtFax.setText(checkNull(mDetail.getFax()));
        mTxtGroupMail.setText(checkNull(mDetail.getGroupMail()));

        mTxtDefaultSettlementWay.setText(CooperationDetailActivity.getSettlementWay(mDetail.getDefaultSettlementWay()));
        mTxtMaintainLevel.setText(getMaintainLevel(mDetail.getMaintainLevel()));
        mTxtDefaultDeliveryWay.setText(CooperationDetailActivity.getDeliveryWay(mDetail.getDefaultDeliveryWay()));
        mTxtCustomerLevel.setText(getCustomerLevel(mDetail.getCustomerLevel()));

        Date date = CalendarUtils.parse(mDetail.getAgreeTime(), CalendarUtils.FORMAT_HH_MM_SS);
        String dateString = null;
        if (date != null) {
            dateString = CalendarUtils.format(date, "yyyy/MM/dd");
        }
        mTxtAgreeTime.setText(checkNull(dateString));
        mTxtDeliveryPeriod.setText(checkNull(mDetail.getDefaultDeliveryPeriod()));
    }

    private String getResourceType(String type) {
        String resourceType = "无";
        if (TextUtils.equals(type, "0")) {
            resourceType = "二十二城";
        } else if (TextUtils.equals(type, "1")) {
            resourceType = "哗啦啦供应链";
        } else if (TextUtils.equals(type, "2")) {
            resourceType = "天财供应链";
        }
        return resourceType;
    }

    private String checkNull(String s) {
        if (TextUtils.isEmpty(s)) {
            return "暂无";
        }
        return s;
    }

    private String getMaintainLevel(String level) {
        String resourceType = "无";
        if (TextUtils.equals(level, "0")) {
            resourceType = "门店级维护";
        } else if (TextUtils.equals(level, "1")) {
            resourceType = "集团级别维护";
        }
        return resourceType;
    }

    private String getCustomerLevel(String customerLevel) {
        String result = "无";
        if (TextUtils.equals(customerLevel, "0")) {
            result = "普通客户";
        } else if (TextUtils.equals(customerLevel, "1")) {
            result = "VIP客户";
        }
        return result;
    }

    @Override
    protected void initData() {
    }
}
