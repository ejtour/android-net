package com.hll_sc_app.app.aptitude.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.AptitudeActivity;
import com.hll_sc_app.app.aptitude.AptitudeHelper;
import com.hll_sc_app.app.aptitude.IAptitudeCallback;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.aptitude.AptitudeInfoResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.aptitude.AptitudeFactoryInfoView;
import com.hll_sc_app.widget.aptitude.AptitudeNormalInfoView;
import com.hll_sc_app.widget.aptitude.IAptitudeInfoCallback;
import com.zhihu.matisse.Matisse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeInfoFragment extends BaseLazyFragment implements IAptitudeInfoContract.IAptitudeInfoView, IAptitudeCallback {
    @BindView(R.id.fai_property)
    TextView mProperty;
    @BindView(R.id.fai_root_group)
    RelativeLayout mRootGroup;
    private IAptitudeInfoCallback mAptitudeInfo;
    private IAptitudeInfoContract.IAptitudeInfoPresenter mPresenter;
    private boolean mEditable;

    public static AptitudeInfoFragment newInstance() {
        return new AptitudeInfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = AptitudeInfoPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_aptitude_info, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void rightClick() {
        if (mAptitudeInfo != null) {
            if (mEditable) {
                mPresenter.save(mAptitudeInfo.getReq());
            } else {
                setEditable(true);
            }
        }
    }

    @Override
    public void saveSuccess() {
        setEditable(false);
    }

    public void setEditable(boolean editable) {
        mAptitudeInfo.setEditable(editable);
        mEditable = editable;
        ((AptitudeActivity) requireActivity()).onPageSelected(0);
    }

    public boolean isEditable() {
        return mEditable;
    }

    @NonNull
    private RelativeLayout.LayoutParams getLP() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.fai_property_label);
        return layoutParams;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null
                && requestCode == ImgUploadBlock.REQUEST_CODE_CHOOSE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) mPresenter.upload(list.get(0));
        }
    }

    @Override
    public void setData(AptitudeInfoResp resp) {
        int type = CommonUtils.getInt(CommonUtils.formatNumber(resp.getGroupInfo().get("companyType").toString()));
        mProperty.setText(AptitudeHelper.getCompanyType(type));
        if (type == 1) {
            mAptitudeInfo = new AptitudeFactoryInfoView(getContext());
        } else if (type > 1) {
            mAptitudeInfo = new AptitudeNormalInfoView(getContext());
        }
        mRootGroup.addView(mAptitudeInfo.getView(), getLP());
        Object photoUrl = resp.getGroupInfo().get("licencePhotoUrl");
        ((AptitudeActivity) requireActivity()).setLicenseUrl(photoUrl == null ? null : photoUrl.toString());
        mAptitudeInfo.getView().setVisibility(View.VISIBLE);
        mAptitudeInfo.withData(resp);
    }

    @Override
    public void setImageUrl(String url) {
        if (mAptitudeInfo instanceof AptitudeNormalInfoView) {
            ((AptitudeNormalInfoView) mAptitudeInfo).showImage(url);
        }
    }
}
