package com.hll_sc_app.app.cooperation.detail.details.certification;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.details.BaseCooperationDetailsFragment;
import com.hll_sc_app.app.cooperation.detail.details.CooperationButtonView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cooperation.BusinessLicensesBean;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 合作采购商详情-详细资料-认证信息
 *
 * @author zhuyingsong
 * @date 2019/7/22
 */
public class CooperationDetailsCertificationFragment extends BaseCooperationDetailsFragment {
    Unbinder unbinder;
    @BindView(R.id.buttonView)
    CooperationButtonView mButtonView;
    @BindView(R.id.txt_certification)
    TextView mTxtCertification;
    @BindView(R.id.txt_certification2)
    TextView mTxtCertification2;
    @BindView(R.id.ll_certification013)
    LinearLayout mLlCertification013;
    @BindView(R.id.txt_businessEntity)
    TextView mTxtBusinessEntity;
    @BindView(R.id.ll_certification2)
    LinearLayout mLlCertification2;

    private CooperationPurchaserDetail mDetail;

    public static CooperationDetailsCertificationFragment newInstance(CooperationPurchaserDetail bean) {
        Bundle args = new Bundle();
        args.putParcelable("parcelable", bean);
        CooperationDetailsCertificationFragment fragment = new CooperationDetailsCertificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        rootView = inflater.inflate(R.layout.fragment_cooperation_details_cerification, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        mButtonView.showButton(mDetail.getActionType(), mDetail.getStatus(),mDetail.getCooperationActive());
        mButtonView.setListener(this, mDetail);
        if (TextUtils.equals(mDetail.getIsCertified(), "2")) {
            // 已通过
            mLlCertification013.setVisibility(View.GONE);
            mLlCertification2.setVisibility(View.VISIBLE);
            SpannableString spannableString = new SpannableString(mTxtCertification2.getText());
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#1AB394")),
                spannableString.length() - 6, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(1.3f), spannableString.length() - 6,
                spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTxtCertification2.setText(spannableString);
            mTxtBusinessEntity.setText(mDetail.getBusinessEntity());
        } else {
            mLlCertification013.setVisibility(View.VISIBLE);
            mLlCertification2.setVisibility(View.GONE);
            String certificationTitle = null;
            switch (CommonUtils.getInt(mDetail.getIsCertified())) {
                case 0:
                    certificationTitle = "该采购商尚未进行认证！";
                    break;
                case 1:
                    certificationTitle = "该采购商正在审核中！";
                    break;
                case 3:
                    certificationTitle = "该采购商未通过审核";
                    break;
                default:
                    break;
            }
            mTxtCertification.setText(certificationTitle);
        }
    }

    @Override
    public void refreshFragment(CooperationPurchaserDetail detail) {
        this.mDetail = detail;
        setForceLoad(true);
        lazyLoad();
    }

    @OnClick({R.id.ll_imagePath, R.id.ll_licencePhotoUrl, R.id.ll_otherLicenses})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_imagePath:
                // 门头照
                if (!TextUtils.isEmpty(mDetail.getFrontImg())) {
                    RouterUtil.goToActivity(RouterConfig.STORE_FRONT_IMAGE, mDetail.getFrontImg());
                } else {
                    showToast("暂无数据");
                }
                break;
            case R.id.ll_licencePhotoUrl:
                // 营业执照
                BusinessLicensesBean businessLicensesBean = new BusinessLicensesBean();
                businessLicensesBean.setBusinessNo(mDetail.getBusinessNo());
                businessLicensesBean.setLicenseName(mDetail.getLicenseName());
                businessLicensesBean.setLicencePhotoUrl(mDetail.getLicencePhotoUrl());
                businessLicensesBean.setStartTime(mDetail.getStartTime());
                businessLicensesBean.setEndTime(mDetail.getEndTime());
                RouterUtil.goToActivity(RouterConfig.STORE_BUSINESS_LICENSE_IMAGE, businessLicensesBean);
                break;
            case R.id.ll_otherLicenses:
                // 其他证照
                if (!CommonUtils.isEmpty(mDetail.getOtherLicenses())) {
                    RouterUtil.goToActivity(RouterConfig.STORE_OTHER_LICENSE_IMAGE,
                        new ArrayList<>(mDetail.getOtherLicenses()));
                } else {
                    showToast("暂无数据");
                }
                break;
            default:
                break;
        }
    }
}
