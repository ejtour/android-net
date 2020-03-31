package com.hll_sc_app.app.wallet.authentication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AreaListReq;
import com.hll_sc_app.bean.wallet.OcrImageResp;
import com.hll_sc_app.bean.wallet.WalletInfo;
import com.hll_sc_app.widget.wallet.AreaSelectDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.app.wallet.authentication.CommonMethod.PERMANENT_DATE;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.setUploadImg;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.transformDate;


/**
 * 经营信息
 * 小微
 *
 * @author zc
 */
public class OperateInfoSmallFragment extends BaseLazyFragment implements IAuthenticationContract.IBaseInfoFragment,
        IAuthenticationContract.IUploadFragment{
    @BindView(R.id.edt_group_name)
    EditText mGroupName;
    @BindView(R.id.edt_company_name)
    EditText mCompanyName;
    @BindView(R.id.edt_short_company_name)
    EditText mCompanyNameShort;
    @BindView(R.id.edt_business_address)
    EditText mEdtBusinessAddress;
    @BindView(R.id.upload_img_head)
    ImgUploadBlock mUpImgHead;
    @BindView(R.id.upload_img_inner)
    ImgUploadBlock mUpImgInner;
    @BindView(R.id.txt_real_address)
    TextView mTxtRealAddress;
    @BindView(R.id.edt_link)
    EditText mEdtLink;
    @BindView(R.id.edt_mail)
    EditText mEdtMail;

    private IAuthenticationContract.IView mView;
    private IAuthenticationContract.IPresent mPresent;
    private Unbinder unbinder;
    private AreaSelectDialog mNetAreaSelectDialog;
    private WalletInfo mWalletInfo;
    private  ImgUploadBlock mCurrentImgShowDelBlock;

    @Override
    public void getAddressSuccess(List<AreaInfo> areaBeans) {
        if (areaBeans.size() > 0) {
            switch (areaBeans.get(0).getAreaType()) {
                case AreaListReq.PROVINCE:
                    mNetAreaSelectDialog.setProvinces(areaBeans);
                    break;
                case AreaListReq.CITY:
                    mNetAreaSelectDialog.setCitys(areaBeans);
                    break;
                case AreaListReq.DISTRIBUTE:
                    mNetAreaSelectDialog.setDistributes(areaBeans);
                    break;
                default:
                    break;

            }
        }
    }

    @Override
    public void getAddressFail() {
        mView.showToast("获取地区信息失败");
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        initView();
        isSubmit();
    }

    /**
     * 初始化表格内的数据
     */
    private void initView() {
        //反显数据
        mWalletInfo = mView.getWalletInfo();
        mGroupName.setText(mWalletInfo.getSettleUnitName());
        mCompanyNameShort.setText(mWalletInfo.getCompanyShortName());
        mUpImgHead.showImage(mWalletInfo.getImgBusiDoor());
        mUpImgInner.showImage(mWalletInfo.getImgBusiEnv());
        mCompanyName.setText(mWalletInfo.getCompanyName());
        mEdtLink.setText(mWalletInfo.getOperatorName());
        mEdtMail.setText(mWalletInfo.getOperatorEmail());

        if (!TextUtils.isEmpty(mWalletInfo.getLicenseProvinceName())) {
            String licenseAddress = mWalletInfo.getLicenseProvinceName() + "-" + mWalletInfo.getLicenseCityName() + "-" + mWalletInfo.getLicenseDistrictName();
            mTxtRealAddress.setText(licenseAddress);
            initAreaWindow();
            AreaInfo provinceArea = new AreaInfo(mWalletInfo.getLicenseProvinceCode(), mWalletInfo.getLicenseProvinceName());
            mNetAreaSelectDialog.setProvice(provinceArea);
            AreaInfo cityArea = new AreaInfo(mWalletInfo.getLicenseCityCode(), mWalletInfo.getLicenseCityName());
            mNetAreaSelectDialog.setCity(cityArea);
            AreaInfo distributeArea = new AreaInfo(mWalletInfo.getLicenseDistrictCode(), mWalletInfo.getLicenseDistrictName());
            mNetAreaSelectDialog.setDistribute(distributeArea);
        }
        mEdtBusinessAddress.setText(mWalletInfo.getBusinessAddress());


    }

    private void isSubmit() {
        mView.setSubmitButtonEnable(isInputComplete());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean isInputComplete() {
        return !TextUtils.isEmpty(mView.getWalletInfo().getCompanyName())
                && !TextUtils.isEmpty(mView.getWalletInfo().getCompanyShortName())
                && !TextUtils.isEmpty(mView.getWalletInfo().getImgBusiDoor())
                && !TextUtils.isEmpty(mView.getWalletInfo().getImgBusiEnv())
                && !TextUtils.isEmpty(mView.getWalletInfo().getLicenseProvinceCode())
                && !TextUtils.isEmpty(mView.getWalletInfo().getBusinessAddress())
                && !TextUtils.isEmpty(mView.getWalletInfo().getOperatorEmail())
                && !TextUtils.isEmpty(mView.getWalletInfo().getSettleUnitName())
                && !TextUtils.isEmpty(mView.getWalletInfo().getOperatorName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresent = AuthenticationPresent.newInstance();
        mPresent.register(this);
        rootView = inflater.inflate(R.layout.fragment_wallet_small_step_1, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void registerView(IAuthenticationContract.IView view) {
        mView = view;
    }

    @Override
    public void setUploadUrl(String url) {
        mCurrentImgShowDelBlock.showImage(url);
        if (mCurrentImgShowDelBlock == mUpImgHead) {
            mView.getWalletInfo().setImgBusiDoor(url);
        } else if (mCurrentImgShowDelBlock == mUpImgInner) {
            mView.getWalletInfo().setImgBusiEnv(url);
        }
        mCurrentImgShowDelBlock.showImage(url);
        isSubmit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUploadImg();
        addInputWatcher();
    }

    /**
     * 设置上传组件的公共配置
     *
     * @param title
     */
    private void initUpload(ImgUploadBlock imgUploadBlock, String title) {
        setUploadImg(imgUploadBlock, title, v -> {
            imgUploadBlock.showImage("");
            WalletInfo walletInfo = mView.getWalletInfo();
            if (imgUploadBlock == mUpImgHead) {
                walletInfo.setImgBusiDoor("");
            } else if (imgUploadBlock == mUpImgInner) {
                walletInfo.setImgBusiEnv("");
            }
            isSubmit();
        }, uploadImgBlock -> {
            mCurrentImgShowDelBlock = imgUploadBlock;
            return true;
        });
    }


    private void initUploadImg() {
        initUpload(mUpImgHead,"点击上传门头照");
        initUpload(mUpImgInner,"点击上传店内照");
    }

    /**
     * 检测是否能提交
     * 每次更改都及时更新walletinfo的值
     */
    private void addInputWatcher() {
        mGroupName.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setSettleUnitName(value);
            mView.getWalletInfo().setGroupName(value);
        }));
        mCompanyNameShort.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setCompanyShortName(value);
        }));
        mCompanyName.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setCompanyName(value);
        }));
        mEdtBusinessAddress.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setBusinessAddress(value);
        }));
        mEdtLink.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setOperatorName(value);
        }));
        mEdtMail.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setOperatorEmail(value);
        }));

    }

    private TextWatcher generateWatcher(CommonMethod.AddInputChangeWalletInfo addInputChangeWalletInfo) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addInputChangeWalletInfo.onChanged(s.toString());
                isSubmit();
            }
        };
    }

    @OnClick({R.id.txt_real_address,})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.txt_real_address:
                initAreaWindow();
                mNetAreaSelectDialog.show();
                break;
            default:
                break;
        }
    }


    public void initAreaWindow() {
        if (mNetAreaSelectDialog == null) {
            mNetAreaSelectDialog = new AreaSelectDialog((Activity) mView);
        }
        if (mNetAreaSelectDialog.getNetAreaWindowEvent() == null) {
            mNetAreaSelectDialog.addNetAreaWindowEvent(new AreaSelectDialog.NetAreaWindowEvent() {
                @Override
                public void getProvinces() {
                    AreaListReq areaListReq = new AreaListReq();
                    areaListReq.setAreaType(AreaListReq.PROVINCE);
                    mPresent.getAddress(areaListReq);
                }

                @Override
                public void getCitys(AreaInfo areaBean) {
                    AreaListReq areaListReq = new AreaListReq();
                    areaListReq.setAreaType(AreaListReq.CITY);
                    areaListReq.setAreaParentId(areaBean.getAreaCode());
                    mPresent.getAddress(areaListReq);
                }

                @Override
                public void getDistributes(AreaInfo areaBean) {
                    AreaListReq areaListReq = new AreaListReq();
                    areaListReq.setAreaType(AreaListReq.DISTRIBUTE);
                    areaListReq.setAreaParentId(areaBean.getAreaCode());
                    mPresent.getAddress(areaListReq);
                }

                @Override
                public void selectAreas(AreaInfo... areaBeans) {
                    mWalletInfo.setLicenseProvinceCode(areaBeans[0].getAreaCode());
                    mWalletInfo.setLicenseProvinceName(areaBeans[0].getAreaName());
                    mWalletInfo.setLicenseCityCode(areaBeans[1].getAreaCode());
                    mWalletInfo.setLicenseCityName(areaBeans[1].getAreaName());
                    mWalletInfo.setLicenseDistrictCode(areaBeans[2].getAreaCode());
                    mWalletInfo.setLicenseDistrictName(areaBeans[2].getAreaName());
                    String address = areaBeans[0].getAreaName() + "-" + areaBeans[1].getAreaName() + "-" + areaBeans[2].getAreaName();
                    mTxtRealAddress.setText(address);
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

}

