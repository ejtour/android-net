package com.hll_sc_app.app.user.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.AptitudeHelper;
import com.hll_sc_app.app.web.WebActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.AreaDtoBean;
import com.hll_sc_app.base.bean.GetIdentifyCodeReq;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.AreaSelectWindow;
import com.hll_sc_app.base.widget.IdentifyCodeTextView;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.user.RegisterReq;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.zhihu.matisse.Matisse;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.ViewCollections;

/**
 * 注册页面
 *
 * @author zhuyingsong
 * @date 2019/6/5
 */
@Route(path = RouterConfig.USER_REGISTER, extras = Constant.AUTH_PROCESS)
public class RegisterActivity extends BaseLoadActivity implements RegisterContract.IFindView {
    public static final String CODE_FROM_MALL = "00120113061";
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.get_identify_code)
    IdentifyCodeTextView mGetIdentifyCode;
    @BindView(R.id.edt_loginPWD)
    EditText mEdtLoginPWD;
    @BindView(R.id.edt_checkLoginPWD)
    EditText mEdtCheckLoginPWD;
    @BindView(R.id.txt_confirm)
    TextView mTxtConfirm;
    @BindView(R.id.edt_groupName)
    EditText mEdtGroupName;
    @BindView(R.id.txt_groupType)
    TextView mTxtGroupType;
    @BindView(R.id.edt_liknman)
    EditText mEdtLinkMan;
    @BindView(R.id.txt_groupDistrict)
    TextView mTxtGroupDistrict;
    @BindView(R.id.edt_groupAddress)
    EditText mEdtGroupAddress;
    @BindView(R.id.img_licencePhotoUrl)
    ImgUploadBlock mImgLicencePhotoUrl;
    @BindView(R.id.ll_licencePhotoUrl)
    LinearLayout mLlLicencePhotoUrl;
    @BindView(R.id.edt_operationGroupID)
    EditText mEdtOperationGroupID;
    @BindView(R.id.txt_agreement)
    TextView mTxtAgreement;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindViews({R.id.ar_code_div, R.id.ar_code_group, R.id.ll_licencePhotoUrl, R.id.txt_groupType, R.id.div_groupType})
    List<View> mNeedlessViews;
    private RegisterPresenter mPresenter;
    private SingleSelectionDialog mTypeDialog;
    private AreaSelectWindow mAreaWindow;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && requestCode == Constant.IMG_SELECT_REQ_CODE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) {
                mPresenter.uploadImg(list.get(0));
            }
        }
    }

    @Override
    public void onBackPressed() {
        SuccessDialog.newBuilder(this)
            .setCancelable(false)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("确定要离开嘛")
            .setMessage("您已经填写了部分数据，离开会\n丢失当前已填写的数据")
            .setButton((dialog, item) -> {
                if (item == 0) {
                    finish();
                }
                dialog.dismiss();
            }, "去意已决", "暂不离开")
            .create().show();
    }

    @OnClick({R.id.img_close, R.id.txt_confirm, R.id.txt_groupDistrict, R.id.txt_groupType})
    public void onViewClicked(View view) {
        ViewUtils.clearEditFocus(view);
        switch (view.getId()) {
            case R.id.img_close:
                onBackPressed();
                break;
            case R.id.txt_confirm:
                toRegister();
                break;
            case R.id.txt_groupDistrict:
                showAreaWindow();
                break;
            case R.id.txt_groupType:
                showTypeSelectWindow();
                break;
            default:
                break;
        }
    }

    private void showTypeSelectWindow() {
        if (mTypeDialog == null) {
            mTypeDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .refreshList(AptitudeHelper.getCompanyTypeList())
                    .setTitleText("请选择公司性质")
                    .setOnSelectListener(nameValue -> {
                        mTxtGroupType.setText(nameValue.getName());
                        mTxtGroupType.setTag(nameValue.getValue());
                    })
                    .create();
        }
        mTypeDialog.show();
    }

    private void toRegister() {
        RegisterReq req = new RegisterReq();
        req.setLoginPhone(mEdtPhone.getText().toString().trim());
        req.setCheckCode(mEdtCode.getText().toString().trim());
        req.setLoginPWD(mEdtLoginPWD.getText().toString().trim());
        req.setCheckLoginPWD(mEdtCheckLoginPWD.getText().toString().trim());
        req.setGroupName(mEdtGroupName.getText().toString().trim());
        req.setLinkman(mEdtLinkMan.getText().toString().trim());
        AreaDtoBean bean = (AreaDtoBean) mTxtGroupDistrict.getTag();
        if (bean != null) {
            req.setGroupProvince(bean.getShopProvince());
            req.setGroupProvinceCode(bean.getShopProvinceCode());
            req.setGroupCity(bean.getShopCity());
            req.setGroupCityCode(bean.getShopCityCode());
            req.setGroupDistrict(bean.getShopDistrict());
            req.setGroupDistrictCode(bean.getShopDistrictCode());
        }
        req.setCompanyType(mTxtGroupType.getTag() != null ? mTxtGroupType.getTag().toString() : null);
        req.setGroupAddress(mEdtGroupAddress.getText().toString().trim());
        req.setLicencePhotoUrl(mImgLicencePhotoUrl.getImgUrl());
        req.setOperationGroupID(mEdtOperationGroupID.getText().toString().trim());
        mPresenter.toRegister(req);
    }

    /**
     * 地区选择
     */
    private void showAreaWindow() {
        if (mAreaWindow == null) {
            mAreaWindow = new AreaSelectWindow(this);
            mAreaWindow.setResultSelectListener(bean -> {
                mTxtGroupDistrict.setText(String.format("%s-%s-%s",
                    bean.getShopProvince(), bean.getShopCity(), bean.getShopDistrict()));
                mTxtGroupDistrict.setTag(bean);
            });
        }
        mAreaWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        mPresenter = RegisterPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        setAgreement();
        // 验证码
        mGetIdentifyCode.bind(new IdentifyCodeTextView.IdentifyCodeOption() {
            @Override
            public GetIdentifyCodeReq getParams() {
                GetIdentifyCodeReq req = new GetIdentifyCodeReq();
                req.setFlag(GetIdentifyCodeReq.FLAG.INT_REGISTER);
                req.setLoginPhone(mEdtPhone.getText().toString().trim());
                return req;
            }

            @Override
            public void onNext(long seconds) {
                mGetIdentifyCode.setText(String.format(Locale.getDefault(), "重新获取%ds", 60 - seconds));
            }

            @Override
            public void onComplete() {
                mGetIdentifyCode.setText("获取验证码");
            }

            @Override
            public void getError(String msg) {
                mGetIdentifyCode.setText("获取验证码");
                showToast(msg);
            }
        });
        mImgLicencePhotoUrl.setOnDeleteListener(v -> checkEnable());
        if (UserConfig.crm()) {
            mEdtCode.setText("x");
            mEdtOperationGroupID.setEnabled(false);
            ViewCollections.run(mNeedlessViews, (view, index) -> view.setVisibility(View.GONE));
        }
    }

    private void setAgreement() {
        String content = mTxtAgreement.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new CSpan(), 10, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTxtAgreement.setText(spannableString);
    }

    @Override
    public void registerSuccess() {
        boolean crm = UserConfig.crm();
        SuccessDialog.newBuilder(this)
                .setCancelable(false)
                .setImageTitle(R.drawable.ic_dialog_success)
                .setImageState(R.drawable.ic_dialog_state_success)
                .setMessageTitle("注册完成")
                .setMessage(crm ? "需等待" + BuildConfig.ODM_NAME + "审核成功后即可登录" : "提交成功，审核结果会以短信形式发送到您的手机，请耐心等待~")
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    finish();
                }, crm ? "返回上一页" : "去登录")
                .create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGetIdentifyCode.stopCountdown();
    }

    @Override
    public void showError(UseCaseException e) {
        if (TextUtils.equals(e.getCode(), CODE_FROM_MALL) && e.getTag() != null) {
            RegisterReq req = (RegisterReq) e.getTag();
            SuccessDialog.newBuilder(this)
                .setCancelable(false)
                .setImageTitle(R.drawable.ic_dialog_success)
                .setImageState(R.drawable.ic_dialog_state_success)
                .setMessageTitle("提交注册成功")
                    .setMessage("您已是" + BuildConfig.ODM_NAME + "商城用户\n补充部分资料即可成为供应商用户")
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    RouterUtil.goToActivity(RouterConfig.USER_REGISTER_COMPLEMENT, this, req);
                }, "补充资料")
                .create().show();
        } else {
            super.showError(e);
        }
    }

    @Override
    public void setCode(String code) {
        mEdtOperationGroupID.setText(code);
    }

    @Override
    public void uploadSuccess(String url) {
        mImgLicencePhotoUrl.showImage(url);
        checkEnable();
    }

    @OnTextChanged(value = {R.id.edt_phone, R.id.edt_code, R.id.edt_loginPWD,
            R.id.edt_checkLoginPWD, R.id.edt_groupName, R.id.txt_groupType,
            R.id.edt_liknman, R.id.txt_groupDistrict, R.id.edt_groupAddress}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void checkEnable() {
        mTxtConfirm.setEnabled(!TextUtils.isEmpty(mEdtPhone.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtLoginPWD.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtCheckLoginPWD.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtGroupName.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtLinkMan.getText().toString().trim())
                && !TextUtils.isEmpty(mTxtGroupDistrict.getText())
                && !TextUtils.isEmpty(mEdtGroupAddress.getText().toString().trim())
                && (UserConfig.crm() || !TextUtils.isEmpty(mImgLicencePhotoUrl.getImgUrl())
                && !TextUtils.isEmpty(mEdtCode.getText().toString().trim())
                && !TextUtils.isEmpty(mTxtGroupType.getText().toString().trim())));
    }

    static class CSpan extends ClickableSpan {
        @Override
        public void onClick(@NonNull View widget) {
            WebActivity.start("服务条款", BuildConfig.isOdm ? "file:////android_asset/registerLegal.html" : "file:////android_asset/userAgreement.html");
        }
    }
}
