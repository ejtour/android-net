package com.hll_sc_app.app.wallet.account.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.wallet.WalletInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/30
 */

@Route(path = RouterConfig.WALLET_ACCOUNT_MY)
public class MyAccountActivity extends BaseLoadActivity implements IMyAccountContract.IMyAccountView {
    @BindView(R.id.wma_logo)
    GlideImageView mLogo;
    @BindView(R.id.wma_account)
    TextView mAccount;
    @BindView(R.id.wma_company_name)
    TextView mCompanyName;
    @BindView(R.id.wma_company_type)
    TextView mCompanyType;
    @BindView(R.id.wma_region)
    TextView mRegion;
    @BindView(R.id.wma_register_address)
    TextView mRegisterAddress;
    @BindView(R.id.wma_industry)
    TextView mIndustry;
    @BindView(R.id.wma_license_image)
    GlideImageView mLicenseImage;
    @BindView(R.id.wma_doorway_image)
    GlideImageView mDoorwayImage;
    @BindView(R.id.wma_indoor_image)
    GlideImageView mIndoorImage;
    @BindView(R.id.wma_cashier_image)
    GlideImageView mCashierImage;
    @BindView(R.id.wma_license_group)
    Group mLicenseGroup;
    @BindView(R.id.wma_doorway_group)
    Group mDoorwayGroup;
    @BindView(R.id.wma_indoor_group)
    Group mIndoorGroup;
    @BindView(R.id.wma_cashier_group)
    Group mCashierGroup;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_my_account);
        unbinder = ButterKnife.bind(this);
        IMyAccountContract.IMyAccountPresenter presenter = MyAccountPresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void handleWalletInfo(WalletInfo resp) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            mLogo.setImageURL(userBean.getGroupLogoUrl());
        }
        mAccount.setText(String.format("账号: %s", resp.getMerchantNo()));
        mCompanyName.setText(String.format("公司名称: %s", resp.getCompanyName()));
        mCompanyType.setText(resp.getUnit());
        mRegion.setText(resp.getAddress());
        mRegisterAddress.setText(resp.getBusinessAddress());
        mIndustry.setText(resp.getIndustry());

        if (TextUtils.isEmpty(resp.getImgLicense())) {
            mLicenseGroup.setVisibility(View.GONE);
        } else {
            mLicenseGroup.setVisibility(View.VISIBLE);
            mLicenseImage.isPreview(true);
            mLicenseImage.setImageURL(resp.getImgLicense());
            mLicenseImage.setUrls(getAllImgUrl(resp));
        }

        if (TextUtils.isEmpty(resp.getImgBusiDoor())) {
            mDoorwayGroup.setVisibility(View.GONE);
        } else {
            mDoorwayGroup.setVisibility(View.VISIBLE);
            mDoorwayImage.isPreview(true);
            mDoorwayImage.setImageURL(resp.getImgBusiDoor());
            mDoorwayImage.setUrls(getAllImgUrl(resp));
        }

        if (TextUtils.isEmpty(resp.getImgBusiEnv())) {
            mIndoorGroup.setVisibility(View.GONE);
        } else {
            mIndoorGroup.setVisibility(View.VISIBLE);
            mIndoorImage.isPreview(true);
            mIndoorImage.setImageURL(resp.getImgBusiEnv());
            mIndoorImage.setUrls(getAllImgUrl(resp));
        }

        if (TextUtils.isEmpty(resp.getImgBusiCounter())) {
            mCashierGroup.setVisibility(View.GONE);
        } else {
            mCashierGroup.setVisibility(View.VISIBLE);
            mCashierImage.isPreview(true);
            mCashierImage.setImageURL(resp.getImgBusiCounter());
            mCashierImage.setUrls(getAllImgUrl(resp));
        }
        mRegion.getParent().requestLayout();
    }

    private List<String> getAllImgUrl(WalletInfo resp) {
        List<String> imgs = new ArrayList<>();
        if (!TextUtils.isEmpty(resp.getImgLicense())) {
            imgs.add(resp.getImgLicense());
        }
        if (!TextUtils.isEmpty(resp.getImgBusiDoor())) {
            imgs.add(resp.getImgBusiDoor());
        }
        if (!TextUtils.isEmpty(resp.getImgBusiEnv())) {
            imgs.add(resp.getImgBusiEnv());
        }
        if (!TextUtils.isEmpty(resp.getImgBusiCounter())) {
            imgs.add(resp.getImgBusiCounter());
        }
        return imgs;
    }
}
