package com.hll_sc_app.app.user.change;

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
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码页面
 *
 * @author zhuyingsong
 * @date 2019/6/5
 */
@Route(path = RouterConfig.USER_CHANGE)
public class ChangePasswordActivity extends BaseLoadActivity implements ChangePasswordContract.IChangeView {
    @BindView(R.id.edt_loginPWD)
    EditText mEdtLoginPassword;
    @BindView(R.id.edt_newLoginPWD)
    EditText mEdtNewLoginPassword;
    @BindView(R.id.edt_checkLoginPWD)
    EditText mEdtCheckLoginPassword;
    @BindView(R.id.txt_confirm)
    TextView mTxtConfirm;
    private ChangePasswordPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initView();
        mPresenter = ChangePasswordPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        InputTextWatcher textWatcher = new InputTextWatcher();
        mEdtNewLoginPassword.addTextChangedListener(textWatcher);
        mEdtLoginPassword.addTextChangedListener(textWatcher);
        mEdtCheckLoginPassword.addTextChangedListener(textWatcher);
    }

    @OnClick({R.id.img_close, R.id.txt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_confirm:
                mPresenter.toChange(mEdtLoginPassword.getText().toString().trim(),
                    mEdtNewLoginPassword.getText().toString().trim(),
                    mEdtCheckLoginPassword.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    @Override
    public void changeSuccess() {
        SuccessDialog.newBuilder(this)
            .setCancelable(false)
            .setImageTitle(R.drawable.ic_dialog_success)
            .setImageState(R.drawable.ic_dialog_state_success)
            .setMessageTitle("密码修改成功")
            .setMessage("可以使用新密码登录啦")
            .setButton((dialog, item) -> UserConfig.reLogin(), "去登录")
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
            mTxtConfirm.setEnabled(!TextUtils.isEmpty(mEdtNewLoginPassword.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtLoginPassword.getText().toString().trim())
                && !TextUtils.isEmpty(mEdtCheckLoginPassword.getText().toString().trim()));
        }
    }
}
