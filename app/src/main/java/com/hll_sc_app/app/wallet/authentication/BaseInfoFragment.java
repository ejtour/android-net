package com.hll_sc_app.app.wallet.authentication;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
 * 实名认证-公司信息
 *
 * @author zc
 */
public class BaseInfoFragment extends BaseLazyFragment implements IAuthenticationContract.IBaseInfoFragment,
        IAuthenticationContract.IUploadFragment, IAuthenticationContract.IOcrFragment {
    @BindView(R.id.edt_group_name)
    EditText mGroupName;
    @BindView(R.id.edt_company_name)
    EditText mCompanyName;
    @BindView(R.id.edt_short_company_name)
    EditText mCompanyNameShort;
    @BindView(R.id.edt_license_address)
    EditText mLicenseAddress;
    @BindView(R.id.edt_license_code)
    EditText mLicenseCode;
    @BindView(R.id.txt_begin_date)
    TextView mLicenseBeginDate;
    @BindView(R.id.txt_end_date)
    TextView mLicensePeriod;
    @BindView(R.id.edt_busis_cope)
    EditText mBusiScope;
    @BindView(R.id.edt_business_address)
    EditText mEdtBusinessAddress;
    @BindView(R.id.upload_img)
    ImgUploadBlock mUpImgBlock;
    @BindView(R.id.txt_real_address)
    TextView mTxtRealAddress;


    private IAuthenticationContract.IView mView;
    private IAuthenticationContract.IPresent mPresent;
    private Unbinder unbinder;
    private AreaSelectDialog mNetAreaSelectDialog;
    private WalletInfo mWalletInfo;
    private boolean isStartDate;
    private DateWindow mDateWindow;

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
        mUpImgBlock.showImage(mWalletInfo.getImgLicense());
        mCompanyName.setText(mWalletInfo.getCompanyName());
        mLicenseCode.setText(mWalletInfo.getLicenseCode());
        mLicenseBeginDate.setText(transformDate(mWalletInfo.getLicenseBeginDate()));
        mLicensePeriod.setText(transformDate(mWalletInfo.getLicensePeriod()));
        mLicenseAddress.setText(mWalletInfo.getLicenseAddress());
        mBusiScope.setText(mWalletInfo.getBusiScope());
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
                && !TextUtils.isEmpty(mView.getWalletInfo().getImgLicense())
                && !TextUtils.isEmpty(mView.getWalletInfo().getLicenseCode())
                && !TextUtils.isEmpty(mView.getWalletInfo().getLicenseBeginDate())
                && !TextUtils.isEmpty(mView.getWalletInfo().getLicensePeriod())
                && !TextUtils.isEmpty(mView.getWalletInfo().getLicenseAddress())
                && !TextUtils.isEmpty(mView.getWalletInfo().getBusiScope())
                && !TextUtils.isEmpty(mView.getWalletInfo().getLicenseProvinceCode())
                && !TextUtils.isEmpty(mView.getWalletInfo().getBusinessAddress());
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
        return inflater.inflate(R.layout.fragment_wallet_base_info, null);
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
        mUpImgBlock.showImage(url);
        mView.getWalletInfo().setImgLicense(url);
        mView.ocrImage(3, url);
        isSubmit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initUploadImg();
        addInputWatcher();
    }

    private void initUploadImg() {
        setUploadImg(mUpImgBlock, "点击上传营业执照", v -> {
            mView.getWalletInfo().setImgLicense("");
            isSubmit();
        }, uploadImgBlock -> {
            return true;
        });
    }

    /**
     * 检测是否能提交
     * 每次更改都及时更新walletinfo的值
     */
    private void addInputWatcher() {
        mGroupName.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setSettleUnitName(value);
        }));
        mCompanyNameShort.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setCompanyShortName(value);
        }));
        mCompanyName.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setCompanyName(value);
        }));
        mLicenseCode.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setLicenseCode(value);
        }));
        mLicenseAddress.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setLicenseAddress(value);
        }));
        mBusiScope.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setBusiScope(value);
        }));
        mEdtBusinessAddress.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setBusinessAddress(value);
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

    @OnClick({R.id.txt_real_address, R.id.txt_begin_date, R.id.txt_end_date, R.id.txt_demo})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.txt_real_address:
                initAreaWindow();
                mNetAreaSelectDialog.show();
                break;
            case R.id.txt_begin_date:
                CommonMethod.showLongValidDateDialog(getActivity(), (dialog, index) -> {
                    dialog.dismiss();
                    mLicensePeriod.setText("");
                    if (index == 0) {
                        mWalletInfo.setLicenseBeginDate(PERMANENT_DATE);
                        mWalletInfo.setLicensePeriod(PERMANENT_DATE);
                        mLicenseBeginDate.setText("长期有效");
                        mLicensePeriod.setText("长期有效");
                    } else {
                        isStartDate = true;
                        showDateWindow(true);
                    }
                });
                break;
            case R.id.txt_end_date:
                CommonMethod.showLongValidDateDialog(getActivity(), (dialog, index) -> {
                    dialog.dismiss();
                    if (index == 0) {
                        mWalletInfo.setLicensePeriod(PERMANENT_DATE);
                        mLicensePeriod.setText("长期有效");
                    } else {
                        isStartDate = false;
                        showDateWindow(false);
                    }
                });
                break;
            case R.id.txt_demo:
                new DemoImgWindow(getActivity()).showDemo(DemoImgWindow.TYPE_LICENSE);
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

    private void showDateWindow(boolean isStar) {
        CommonMethod.showDate((BaseLoadActivity) mView, mDateWindow, isStar, mWalletInfo.getLicenseBeginDate(), mWalletInfo.getLicensePeriod(), (oDate, sDate) -> {
            if (isStartDate) {
                mWalletInfo.setLicenseBeginDate(oDate);
                mLicenseBeginDate.setText(transformDate(oDate));
            } else {
                mWalletInfo.setLicensePeriod(oDate);
                mLicensePeriod.setText(transformDate(oDate));
            }
        });
    }

    @Override
    public void showOcrInfo(OcrImageResp resp) {
        OcrImageResp.ImgLicenseBean frontBean  = JsonUtil.fromJson(resp.getImgLicense(), OcrImageResp.ImgLicenseBean.class);
        mLicenseAddress.setText(frontBean.getAddress());
        mBusiScope.setText(frontBean.getBusiness());
        mCompanyName.setText(frontBean.getName());
        mLicenseCode.setText(frontBean.getReg_num());
        mLicenseBeginDate.setText(transformDate(frontBean.getEstablish_date()));
        mLicensePeriod.setText(transformDate(frontBean.getValid_period()));
        mView.getWalletInfo().setLicenseBeginDate(frontBean.getEstablish_date());
        mView.getWalletInfo().setLicensePeriod(frontBean.getValid_period());
        mView.getWalletInfo().setLicenseCode(frontBean.getReg_num());
        mView.getWalletInfo().setCompanyName(frontBean.getName());
        mView.getWalletInfo().setLicenseAddress(frontBean.getAddress());
        mView.getWalletInfo().setBusiScope(frontBean.getBusiness());
        isSubmit();
    }
}

