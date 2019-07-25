package com.hll_sc_app.app.setting.account.unbindmainaccount;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hll_sc_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 更改集团手机号的第三步
 *
 * @author zc
 */
public class UnbindMainAccountThreeFragment extends Fragment implements IUnbindMainAccountContract.IFragment {
    @BindView(R.id.identify_new_pwd)
    EditText mNewPwd;
    @BindView(R.id.identify_pwd_again)
    EditText mPwdAgain;
    private Unbinder unbinder;
    private IUnbindMainAccountContract.IView mView;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (getUserVisibleHint()) {
                if (mPwdAgain.getText().toString().length() > 0 && mNewPwd.toString().length() > 0) {
                    mView.updateNextStepButtonStatus(true);
                } else {
                    mView.updateNextStepButtonStatus(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chang_group_phone_three, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        bindEditTextChange();
        return rootView;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }


    /**
     * 更新activity中的下一步按钮禁用状态 和 本fragment里的验证码按钮禁用状态
     */
    private void bindEditTextChange() {
        mNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateNextStepButtonStatus();
            }
        });

        mPwdAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateNextStepButtonStatus();

            }
        });
    }

    private void updateNextStepButtonStatus() {
        String newPwd = mNewPwd.getText().toString();
        String checkPwd = mPwdAgain.getText().toString();
        if (newPwd.length() > 0 && checkPwd.length() > 0 && TextUtils.equals(newPwd, checkPwd)) {
            mView.updateNextStepButtonStatus(true);
        } else {
            mView.updateNextStepButtonStatus(false);
        }
    }

    @Override
    public void stopCountdown() {
        //no-op
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public String getIdentifyCode() {
        return null;
    }

    @Override
    public String getNewPassword() {
        return mNewPwd.getText().toString();
    }

    @Override
    public String getCheckPassword() {
        return mPwdAgain.getText().toString();
    }

    public void bind(IUnbindMainAccountContract.IView iLoadView) {
        mView = iLoadView;
    }

}
