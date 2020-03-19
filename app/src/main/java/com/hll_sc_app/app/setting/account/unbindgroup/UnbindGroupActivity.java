package com.hll_sc_app.app.setting.account.unbindgroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.GetIdentifyCodeReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.IdentifyCodeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 解绑集团
 *
 * @author zc
 */
@Route(path = RouterConfig.ACTIVITY_UNBIND_GROUP)
public class UnbindGroupActivity extends BaseLoadActivity implements IUnbindGroupContract.IView {
    @BindView(R.id.identify_code_text)
    EditText mIdenfityCode;
    @BindView(R.id.account_phone)
    TextView mAccountPhone;
    @BindView(R.id.identify_code_btn)
    IdentifyCodeTextView mIdentifyCodeBtn;
    @BindView(R.id.unbind_group)
    TextView mUnbindGroup;
    private Unbinder unbinder;
    private IUnbindGroupContract.IPresent mPresent;
    private UserBean mUserBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbind_group);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        unbinder = ButterKnife.bind(this);
        mPresent = new UnbindGroupPresent();
        mPresent.register(this);
        initTextChangeListener();
        initView();
    }

    @Override
    protected void onDestroy() {
        mIdentifyCodeBtn.stopCountdown();
        unbinder.unbind();
        super.onDestroy();

    }

    private void initTextChangeListener() {
        mIdenfityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mUnbindGroup.setEnabled(s.toString().length() > 0);
            }
        });

    }

    private void initView() {
        mUserBean = GreenDaoUtils.getUser();
        if (mUserBean != null && mUserBean.getLoginPhone() != null) {
            mAccountPhone.setText(mUserBean.getLoginPhone());
            mIdentifyCodeBtn.bind(new IdenfityOption());
        }
    }

    @OnClick(R.id.unbind_group)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.unbind_group:
                showUnbindAlertDialog();
                break;
            default:
                break;
        }
    }

    private void showUnbindAlertDialog() {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setCancelable(false)
                .setMessageTitle("确认解绑吗？")
                .setMessage("解除绑定集团将失去所有操作权限\n账号变为未注册")
                .setButton((dialog, item) -> {
                    if (item == 1) {
                        mPresent.unbindGroup();
                    }
                    dialog.dismiss();
                }, "我再想想", "确认解绑")
                .create()
                .show();
    }

    @Override
    public void unbindSuccess() {
        mPresent.logout();


    }

    @Override
    public String getIdentifyCode() {
        return mIdenfityCode.getText().toString();
    }

    @Override
    public void logoutSuccess() {
        UserConfig.reLogin();
    }

    private class IdenfityOption implements IdentifyCodeTextView.IdentifyCodeOption {
        @Override
        public GetIdentifyCodeReq getParams() {
            GetIdentifyCodeReq getIdentifyCodeReq = new GetIdentifyCodeReq();
            getIdentifyCodeReq.setFlag(GetIdentifyCodeReq.FLAG.INT_CHILD);
            getIdentifyCodeReq.setLoginPhone(mUserBean.getLoginPhone());
            return getIdentifyCodeReq;
        }

        @Override
        public void onNext(long seconds) {
            mIdentifyCodeBtn.setText("重新获取" + (60 - seconds) + "s");
        }

        @Override
        public void onComplete() {
            mIdentifyCodeBtn.setText("获取验证码");
        }

        @Override
        public void getError(String msg) {
            mIdentifyCodeBtn.setText("获取验证码");
            showToast(msg);
        }
    }
}
