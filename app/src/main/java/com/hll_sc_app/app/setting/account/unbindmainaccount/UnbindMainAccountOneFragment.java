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
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.widget.IdentifyCodeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 更改集团手机号的第一步
 *
 * @author zc
 */
public class UnbindMainAccountOneFragment extends Fragment implements IUnbindMainAccountContract.IFragment {
    @BindView(R.id.identify_code_btn)
    IdentifyCodeTextView mIdentifyCodeTextView;
    @BindView(R.id.identify_old_phone)
    TextView mOldPhone;
    @BindView(R.id.identify_code_text)
    EditText mIdentifyCode;
    private Unbinder unbinder;
    private IUnbindMainAccountContract.IView mView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (getUserVisibleHint()) {
                mView.updateNextStepButtonStatus(mIdentifyCode.getText().toString().length() > 0);
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
        View rootView = inflater.inflate(R.layout.fragment_chang_group_phone_one, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initIdentifyButton();
        bindIdentifyCodeChange();
        if (GreenDaoUtils.getUser() != null && GreenDaoUtils.getUser().getLoginPhone() != null) {
            mOldPhone.setText(GreenDaoUtils.getUser().getLoginPhone());
        }
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


    private void bindIdentifyCodeChange() {
        mIdentifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mView.updateNextStepButtonStatus(s.toString().length() > 0);
            }
        });
    }

    @Override
    public void stopCountdown() {
        mIdentifyCodeTextView.stopCountdown();
    }

    @Override
    public String getPhoneNumber() {
        return mOldPhone.getText().toString();
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
            req.setFlag(GetIdentifyCodeReq.FLAG.INT_BIND_OLD);
            if (GreenDaoUtils.getUser() != null && GreenDaoUtils.getUser().getLoginPhone() != null) {
                req.setLoginPhone(GreenDaoUtils.getUser().getLoginPhone());
            }
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
