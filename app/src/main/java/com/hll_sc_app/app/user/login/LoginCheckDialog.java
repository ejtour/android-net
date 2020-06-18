package com.hll_sc_app.app.user.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.MyApplication;
import com.hll_sc_app.R;
import com.hll_sc_app.base.bean.GetIdentifyCodeReq;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.IdentifyCodeTextView;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 子账号登录需要验证码
 *
 * @author zhuyingsong
 * @date 20190604
 */
public class LoginCheckDialog extends BaseDialog {
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.get_identify_code)
    IdentifyCodeTextView mGetIdentifyCode;
    @BindView(R.id.txt_login)
    TextView mTxtLogin;
    private LoginContract.ILoginView mView;
    private ItemClickListener mListener;
    private String mLoginPhone;

    LoginCheckDialog(@NonNull Activity context, String loginPhone, LoginContract.ILoginView view) {
        super(context);
        this.mView = view;
        this.mLoginPhone = loginPhone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.dialog_login_check, null);
    }

    @OnClick(R.id.txt_no_login)
    @Override
    public void dismiss() {
        mGetIdentifyCode.stopCountdown();
        super.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = UIUtils.dip2px(320);
        params.height = UIUtils.dip2px(340);
        params.gravity = Gravity.TOP;
        params.y = (int) (UIUtils.getScreenHeight(MyApplication.getInstance()) * 0.08);
        initView();
    }

    private void initView() {
        mEdtCode.addTextChangedListener(new TextWatcher() {
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
                mTxtLogin.setEnabled(!TextUtils.isEmpty(s.toString()));
            }
        });
        // 验证码
        mGetIdentifyCode.bind(new IdentifyCodeTextView.IdentifyCodeOption() {
            @Override
            public GetIdentifyCodeReq getParams() {
                GetIdentifyCodeReq req = new GetIdentifyCodeReq();
                req.setFlag(GetIdentifyCodeReq.FLAG.INT_LOGIN);
                req.setLoginPhone(mLoginPhone);
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
                if (mView != null) {
                    mView.showToast(msg);
                }
            }
        });
    }

    void setAddClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.txt_login})
    void onViewClicked(View view) {
        if (mListener != null) {
            mListener.login(getCode());
        }
        dismiss();
    }

    private String getCode() {
        return mEdtCode.getText().toString().trim();
    }

    public interface ItemClickListener {
        /**
         * 去登录
         *
         * @param code 验证码
         */
        void login(String code);
    }
}
