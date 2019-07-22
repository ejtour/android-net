package com.hll_sc_app.app.cooperation.detail.details.certification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.details.BaseCooperationDetailsFragment;
import com.hll_sc_app.app.cooperation.detail.details.CooperationButtonView;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 合作采购商详情-详细资料-认证信息
 *
 * @author zhuyingsong
 * @date 2019/7/22
 */
public class CooperationDetailsCertificationFragment extends BaseCooperationDetailsFragment implements CooperationDetailsCertificationContract.ICertificationView {
    Unbinder unbinder;
    @BindView(R.id.buttonView)
    CooperationButtonView mButtonView;
    @BindView(R.id.txt_certification)
    TextView mTxtCertification;
    @BindView(R.id.ll_certification013)
    LinearLayout mLlCertification013;

    private CooperationPurchaserDetail mDetail;
    private CooperationDetailsCertificationPresenter mPresenter;

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
        mPresenter = CooperationDetailsCertificationPresenter.newInstance();
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
        rootView = inflater.inflate(R.layout.fragment_cooperation_details_cerification, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        mButtonView.showButton(mDetail.getActionType(), mDetail.getStatus());
        mButtonView.setListener(this, mDetail);
        if (TextUtils.equals(mDetail.getIsCertified(), "2")) {
            // 已通过
            mLlCertification013.setVisibility(View.GONE);
        } else {
            mLlCertification013.setVisibility(View.VISIBLE);
            String certificationTitle = null;
            switch (mDetail.getIsCertified()) {
                case "0":
                    certificationTitle = "该采购商尚未进行认证！";
                    break;
                case "1":
                    certificationTitle = "该采购商正在审核中！";
                    break;
                case "3":
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
        if (isFragmentVisible()) {
            lazyLoad();
        }
    }
}
