package com.hll_sc_app.app.user.find;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.GetIdentifyCodeReq;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.IdentifyCodeTextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码页面
 *
 * @author zhuyingsong
 * @date 2019/6/5
 */
@Route(path = RouterConfig.USER_FIND, extras = Constant.AUTH_PROCESS)
public class FindPasswordActivity extends BaseLoadActivity implements FindPasswordContract.IFindView {
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
    private FindPasswordPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        initView();
        mPresenter = FindPasswordPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGetIdentifyCode.stopCountdown();
    }

    @OnClick({R.id.img_close, R.id.txt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_confirm:
                mPresenter.toFind(mEdtPhone.getText().toString().trim(),
                    mEdtCode.getText().toString().trim(),
                    mEdtLoginPWD.getText().toString().trim(),
                    mEdtCheckLoginPWD.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    @Override
    public void findSuccess() {
        SuccessDialog.newBuilder(this)
            .setCancelable(false)
            .setImageTitle(R.drawable.ic_dialog_success)
            .setImageState(R.drawable.ic_dialog_state_success)
            .setMessageTitle("密码修改成功")
            .setMessage("可以使用新密码登录啦")
            .setButton((dialog, item) -> finish(), "去登录")
            .create().show();
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
                && !TextUtils.isEmpty(mEdtCheckLoginPWD.getText().toString().trim()));
        }
    }
}
