package com.hll_sc_app.app.user.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.GetIdentifyCodeReq;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.AreaSelectWindow;
import com.hll_sc_app.base.widget.IdentifyCodeTextView;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.user.PageParams;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册页面
 *
 * @author zhuyingsong
 * @date 2019/6/5
 */
@Route(path = RouterConfig.USER_REGISTER)
public class RegisterActivity extends BaseLoadActivity implements RegisterContract.IFindView {
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
    @BindView(R.id.edt_liknman)
    EditText mEdtLinkMan;
    @BindView(R.id.txt_groupDistrict)
    TextView mTxtGroupDistrict;
    @BindView(R.id.rl_groupDistrict)
    RelativeLayout mRlGroupDistrict;
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
    @BindView(R.id.fl_bottom)
    FrameLayout mFlBottom;
    private RegisterPresenter mPresenter;

    private AreaSelectWindow mAreaWindow;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && requestCode == ImgUploadBlock.REQUEST_CODE_CHOOSE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) {
                mPresenter.uploadImg(new File(list.get(0)));
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = RegisterPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        setAgreement();
        InputTextWatcher textWatcher = new InputTextWatcher();
        mEdtPhone.addTextChangedListener(textWatcher);
        mEdtCode.addTextChangedListener(textWatcher);
        mEdtLoginPWD.addTextChangedListener(textWatcher);
        mEdtCheckLoginPWD.addTextChangedListener(textWatcher);
        // 验证码
        mGetIdentifyCode.bind(new IdentifyCodeTextView.IdentifyCodeOption() {
            @Override
            public GetIdentifyCodeReq getParams() {
                GetIdentifyCodeReq req = new GetIdentifyCodeReq();
                req.setFlag(GetIdentifyCodeReq.FLAG.INT_LOGIN);
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
    }

    private void setAgreement() {
        String content = mTxtAgreement.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                PageParams pageParams = new PageParams();
                pageParams.setTitle("服务条款");
                pageParams.setProtocolUrl("file:////android_asset/registerLegal.html");
                RouterUtil.goToActivity(RouterConfig.WEB_VIEW_PROTOCOL, pageParams);
            }
        }, content.length() - 10, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTxtAgreement.setText(spannableString);
        mTxtAgreement.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGetIdentifyCode.stopCountdown();
    }

    @OnClick({R.id.img_close, R.id.txt_confirm, R.id.rl_groupDistrict})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                uploadSuccess("group3/M03/81/60/wKgVe1z4kSTO3vJTAAF41OLoqb0759.jpg");
//                finish();
                break;
            case R.id.txt_confirm:
                mPresenter.toFind(mEdtPhone.getText().toString().trim(),
                    mEdtCode.getText().toString().trim(),
                    mEdtLoginPWD.getText().toString().trim(),
                    mEdtCheckLoginPWD.getText().toString().trim());
                break;
            case R.id.rl_groupDistrict:
                showAreaWindow();
                break;
            default:
                break;
        }
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
        mAreaWindow.showAtLocation(getWindow().getDecorView(), Gravity.END, 0, 0);
    }

    @Override
    public void registerSuccess() {
        showToast("密码修改成功");
        finish();
    }

    @Override
    public void uploadSuccess(String url) {
        mImgLicencePhotoUrl.showImage(url);
    }

    private class InputTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // no-op
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // no-op
        }

        @Override
        public void afterTextChanged(Editable s) {
            mTxtConfirm.setEnabled(!TextUtils.isEmpty(mEdtPhone.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtCode.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtLoginPWD.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtCheckLoginPWD.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtGroupName.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtLinkMan.getText().toString().trim())
                && !TextUtils.isEmpty(mTxtGroupDistrict.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtGroupAddress.getText().toString().trim())
            );
        }
    }
}
