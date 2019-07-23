package com.hll_sc_app.app.setting.account.unbindmainaccount;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.bean.GetIdentifyCodeReq;
import com.hll_sc_app.base.widget.IdentifyCodeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 更改集团手机号的第二步
 *
 * @author zc
 */
public class UnbindMainAccountTwoFragment extends Fragment implements IUnbindMainAccountContract.IFragment {
    @BindView(R.id.identify_code_btn)
    IdentifyCodeTextView mIdentifyCodeTextView;
    @BindView(R.id.identify_new_phone)
    TextView mNewPhone;
    @BindView(R.id.identify_code_text)
    EditText mIdentifyCode;
    private Unbinder unbinder;
    private IUnbindMainAccountContract.IView mView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (getUserVisibleHint()) {
                if (mNewPhone.getText().toString().length() > 0 && mIdentifyCode.toString().length() > 0) {
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
        View rootView = inflater.inflate(R.layout.fragment_chang_group_phone_two, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initIdentifyButton();
        bindEditTextChange();
        return rootView;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    /**
     * 传入验证码的参数
     */
    private void initIdentifyButton() {
        mIdentifyCodeTextView.bind(new IdentifyCodeOption());
    }


    /**
     * 更新activity中的下一步按钮禁用状态 和 本fragment里的验证码按钮禁用状态
     */
    private void bindEditTextChange() {
        mIdentifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mNewPhone.getText().toString().length() > 0 && s.toString().length() > 0) {
                    mView.updateNextStepButtonStatus(true);
                } else {
                    mView.updateNextStepButtonStatus(false);
                }

            }
        });

        mNewPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mIdentifyCode.getText().toString().length() > 0 && s.toString().length() > 0) {
                    mView.updateNextStepButtonStatus(true);
                } else {
                    mView.updateNextStepButtonStatus(false);
                }

                mIdentifyCodeTextView.setEnabled(s.toString().length() > 0);
            }
        });
    }

    @Override
    public void stopCountdown() {
        mIdentifyCodeTextView.stopCountdown();
    }

    @Override
    public String getPhoneNumber() {
        return mNewPhone.getText().toString();
    }

    @Override
    public String getIdentifyCode() {
        return mIdentifyCode.getText().toString();
    }

    @Override
    public String getNewPassword() {
        return null;
    }

    @Override
    public String getCheckPassword() {
        return null;
    }

    public void bind(IUnbindMainAccountContract.IView iLoadView) {
        mView = iLoadView;
    }

    /**
     * 倒计时按钮的配置类
     *
     * @author zc
     */
    private class IdentifyCodeOption implements IdentifyCodeTextView.IdentifyCodeOption {
        @Override
        public GetIdentifyCodeReq getParams() {
            GetIdentifyCodeReq req = new GetIdentifyCodeReq();
            req.setFlag(GetIdentifyCodeReq.FLAG.INT_BIND_NEW);
            req.setLoginPhone(mNewPhone.getText().toString());
            return req;
        }

        @Override
        public void onNext(long seconds) {
            mIdentifyCodeTextView.setText("重新获取" + String.valueOf(60 - seconds) + "s");

        }

        @Override
        public void onComplete() {
            mIdentifyCodeTextView.setText("获取验证码");

        }

        @Override
        public void getError(String msg) {
            mIdentifyCodeTextView.setText("获取验证码");
            if (mView != null) {
                mView.showToast(msg);
            }
        }
    }
}
