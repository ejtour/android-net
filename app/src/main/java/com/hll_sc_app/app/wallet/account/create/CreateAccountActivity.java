package com.hll_sc_app.app.wallet.account.create;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.utils.adapter.ViewPagerAdapter;
import com.hll_sc_app.widget.ScrollableViewPager;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.wallet.AreaSelectDialog;
import com.hll_sc_app.widget.wallet.WalletProtocolDialog;
import com.hll_sc_app.widget.wallet.create.CreateInfoInputView;
import com.hll_sc_app.widget.wallet.create.CreateNameInputView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/31
 */

@Route(path = RouterConfig.WALLET_ACCOUNT_CREATE)
public class CreateAccountActivity extends BaseLoadActivity implements ICreateAccountContract.ICreateAccountView {
    public static final int REQ_CODE = 0x777;
    @BindView(R.id.wca_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.wca_view_pager)
    ScrollableViewPager mViewPager;
    private CreateNameInputView mNameInputView;
    private CreateInfoInputView mInfoInputView;
    private ICreateAccountContract.ICreateAccountPresenter mPresenter;
    private WalletProtocolDialog mProtocolDialog;
    private AreaSelectDialog mAreaSelectDialog;
    private AuthInfo mAuthInfo = new AuthInfo();

    public static void start(Activity context) {
        RouterUtil.goToActivity(RouterConfig.WALLET_ACCOUNT_CREATE, context, REQ_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_create_account);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mNameInputView = new CreateNameInputView(this);
        mNameInputView.setNextClickListener(v -> {
            mAuthInfo.setCompanyName(mNameInputView.getCompanyName().toString());
            mInfoInputView.setCompanyName(mAuthInfo.getCompanyName());
            mViewPager.setCurrentItem(1);
        });
        mInfoInputView = new CreateInfoInputView(this);
        mInfoInputView.setRegionClickListener(this::showAreaDialog);
        mInfoInputView.setConfirmClickListener(this::createAccount);
        mViewPager.setAdapter(new ViewPagerAdapter(mNameInputView, mInfoInputView));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTitleBar.setHeaderTitle(position == 1 ? "开通账号" : "开通铁金库");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mProtocolDialog = new WalletProtocolDialog(this, this::rejectProtocol);
        mProtocolDialog.show();
    }

    private void initData() {
        mPresenter = CreateAccountPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 1) {
            SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("确定要离开吗")
                .setMessage("离开后将不能保存您当前所做的更改")
                .setCancelable(false)
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 0) {
                        finish();
                    }
                }, "去意已决", "暂不离开")
                .create()
                .show();
            return;
        }
        super.onBackPressed();
    }

    private void showAreaDialog(View view) {
        initAreaDialog();
        mAreaSelectDialog.show();
    }

    private void createAccount(View view) {
        mInfoInputView.updateAuthInfo(mAuthInfo);
        mPresenter.createAccount(mAuthInfo);
    }

    private void rejectProtocol(View view) {
        mProtocolDialog.dismiss();
        finish();
    }

    private void initAreaDialog() {
        if (mAreaSelectDialog == null) {
            mAreaSelectDialog = new AreaSelectDialog(this);
        }
        if (mAreaSelectDialog.getNetAreaWindowEvent() == null) {
            mAreaSelectDialog.addNetAreaWindowEvent(new AreaSelectDialog.NetAreaWindowEvent() {
                @Override
                public void getProvinces() {
                    mPresenter.queryAreaList(ICreateAccountContract.AreaType.PROVINCE, "ZP1");
                }

                @Override
                public void getCitys(AreaInfo areaBean) {
                    mPresenter.queryAreaList(ICreateAccountContract.AreaType.CITY, areaBean.getAreaCode());
                }

                @Override
                public void getDistributes(AreaInfo areaBean) {
                    mPresenter.queryAreaList(ICreateAccountContract.AreaType.DISTRIBUTE, areaBean.getAreaCode());
                }

                @Override
                public void selectAreas(AreaInfo... areaBeans) {
                    String address =
                        areaBeans[0].getAreaName() + "-" + areaBeans[1].getAreaName() + "-" + areaBeans[2].getAreaName();
                    mInfoInputView.setRegion(address);
                    mAuthInfo.setLicenseProvinceCode(areaBeans[0].getAreaCode());
                    mAuthInfo.setLicenseProvinceName(areaBeans[0].getAreaName());
                    mAuthInfo.setLicenseCityCode(areaBeans[1].getAreaCode());
                    mAuthInfo.setLicenseCityName(areaBeans[1].getAreaName());
                    mAuthInfo.setLicenseDistrictCode(areaBeans[2].getAreaCode());
                    mAuthInfo.setLicenseDistrictName(areaBeans[2].getAreaName());
                }

                @Override
                public String getName(AreaInfo item) {
                    return item.getAreaName();
                }

                @Override
                public String getKey(AreaInfo areaBean) {
                    return areaBean.getAreaCode();
                }
            });
        }
    }

    @Override
    public void handleAuthInfo(AuthInfo info) {
        if (info == null) return;
        mAuthInfo = info;
        mNameInputView.setCompanyName(info.getCompanyName());
        mInfoInputView.initData(info);
        if (!TextUtils.isEmpty(info.getLicenseProvinceName())) {
            String licenseAddress =
                info.getLicenseProvinceName() + "-" + info.getLicenseCityName() + "-" + info.getLicenseDistrictName();
            mInfoInputView.setRegion(licenseAddress);
            initAreaDialog();
            AreaInfo provinceArea = new AreaInfo(info.getLicenseProvinceCode(), info.getLicenseProvinceName());
            mAreaSelectDialog.setProvice(provinceArea);
            AreaInfo cityArea = new AreaInfo(info.getLicenseCityCode(), info.getLicenseCityName());
            mAreaSelectDialog.setCity(cityArea);
            AreaInfo distributeArea = new AreaInfo(info.getLicenseDistrictCode(), info.getLicenseDistrictName());
            mAreaSelectDialog.setDistribute(distributeArea);
        }
    }

    @Override
    public void handleAreaList(List<AreaInfo> areaInfoList) {
        if (areaInfoList.size() > 0) {
            switch (areaInfoList.get(0).getAreaType()) {
                case ICreateAccountContract.AreaType.PROVINCE:
                    mAreaSelectDialog.setProvinces(areaInfoList);
                    break;
                case ICreateAccountContract.AreaType.CITY:
                    mAreaSelectDialog.setCitys(areaInfoList);
                    break;
                case ICreateAccountContract.AreaType.DISTRIBUTE:
                    mAreaSelectDialog.setDistributes(areaInfoList);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void createSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
