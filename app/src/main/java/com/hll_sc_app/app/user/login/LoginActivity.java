package com.hll_sc_app.app.user.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.AccountBean;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ClearEditText;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.KeyboardWatcher;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 *
 * @author zhuyingsong
 * @date 2019/6/4
 */
@Route(path = RouterConfig.USER_LOGIN)
public class LoginActivity extends BaseLoadActivity implements LoginContract.ILoginView, KeyboardWatcher.SoftKeyboardStateListener {
    public static final String CODE_NEED_CHECK = "00120113053";
    public static final String CODE_UN_REGISTER = "该手机号未注册，是否现在注册";
    @BindView(R.id.edt_phone)
    ClearEditText mEdtPhone;
    @BindView(R.id.edt_password)
    ClearEditText mEdtPWD;
    @BindView(R.id.txt_login)
    TextView mTxtLogin;
    @Autowired(name = RouterUtil.DESTINATION)
    String mDestination;
    @BindView(R.id.ll_content)
    RelativeLayout mLlContent;
    @BindView(R.id.ll_login)
    LinearLayout mLlLogin;
    @BindView(R.id.ll_toLogin)
    LinearLayout mLlToLogin;
    @BindView(R.id.list_account_view)
    RecyclerView mListCountView;
    @BindView(R.id.img_arrow_account)
    ImageView mArrowAccount;
    @BindView(R.id.ll_edt_phone)
    LinearLayout mLlEdtPhone;
    @BindView(R.id.ll_operation)
    LinearLayout mLlOperation;
    private LoginPresenter mPresenter;
    private KeyboardWatcher mKeyboardWatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        mPresenter = LoginPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        mKeyboardWatcher.removeSoftKeyboardStateListener(this);
        super.onDestroy();
    }

    private void initView() {
        mKeyboardWatcher = new KeyboardWatcher(this);
        mKeyboardWatcher.addSoftKeyboardStateListener(this);
        mLlContent.post(() -> ObjectAnimator.ofFloat(mLlContent, "translationY", mLlContent.getHeight(),
            UIUtils.dip2px(127)).setDuration(800).start());
        InputTextWatcher textWatcher = new InputTextWatcher();
        mEdtPhone.addTextChangedListener(textWatcher);
        mEdtPWD.addTextChangedListener(textWatcher);
        mEdtPhone.clearFocus();
        //获取存储的已登记的账号并初始化列表
        List<AccountBean> accountBeans = GreenDaoUtils.queryAllAccount();
        mArrowAccount.setVisibility(accountBeans.size() == 0 ? View.GONE : View.VISIBLE);
        if (accountBeans.size() > 0) {
            mEdtPhone.setText(accountBeans.get(0).getAccount());
            CountAdapter countAdapter = new CountAdapter(accountBeans);
            mListCountView.setAdapter(countAdapter);
            countAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                AccountBean accountBean = countAdapter.getItem(position);
                if (view.getId() == R.id.txt_phone) {
                    mEdtPhone.setText(accountBean.getAccount());
                    mEdtPhone.setSelection(accountBean.getAccount().length());
                    mEdtPWD.setText("");
                    setAccountListVisible(false);
                } else if (view.getId() == R.id.img_del) {
                    countAdapter.remove(position);
                    GreenDaoUtils.deleteAccount(accountBean.getAccount());
                    if (countAdapter.getData().size() == 0) {
                        mArrowAccount.setVisibility(View.GONE);
                        setAccountListVisible(false);
                    }
                }
            });
            mEdtPhone.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    setAccountListVisible(false);
                }
                setPhoneEdtBackgroudColor(hasFocus);
            });
        }
    }

    /**
     * 账号历史列表显示和隐藏
     *
     * @param bVisible
     */
    private void setAccountListVisible(boolean bVisible) {
        int isVisible = bVisible ? View.VISIBLE : View.GONE;
        mListCountView.setVisibility(isVisible);
        mArrowAccount.setRotation(bVisible ? 180f : 0f);
        mEdtPWD.setVisibility(isVisible == View.VISIBLE ? View.GONE : View.VISIBLE);
        mTxtLogin.setVisibility(isVisible == View.VISIBLE ? View.GONE : View.VISIBLE);
        mLlOperation.setVisibility(isVisible == View.VISIBLE ? View.GONE : View.VISIBLE);
        if (bVisible) {
            ViewUtils.clearEditFocus(mEdtPhone);
            mEdtPhone.clearFocus();
        }
        setPhoneEdtBackgroudColor(bVisible);
    }

    private void setPhoneEdtBackgroudColor(boolean isFocus) {
        mLlEdtPhone.setBackgroundResource(isFocus ? R.drawable.bg_66white_radius_5:R.drawable.bg_33white_radius_5);
    }

    @Override
    public void loginSuccess(String authType) {
        //存储登录的账号
        AccountBean accountBean = new AccountBean();
        accountBean.setAccount(mEdtPhone.getText().toString());
        accountBean.setTime(Calendar.getInstance().getTimeInMillis());
        GreenDaoUtils.updateAccount(accountBean);

        if (!TextUtils.isEmpty(mDestination)) {
            ARouter.getInstance().build(mDestination).with(getIntent().getExtras()).setProvider(new LoginInterceptor())
                .navigation(LoginActivity.this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        LoginActivity.this.finish();
                    }
                });
            return;
        }
        if (TextUtils.isEmpty(authType)) {
            toHomePage();
            return;
        }
        String[] strings = authType.split(",");
        if (strings.length == 1) {
            toHomePage();
        } else {
            showChoiceDialog();
        }
    }

    private void toHomePage() {
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME, this);
    }

    @OnClick({R.id.txt_login, R.id.txt_findPWD, R.id.txt_register, R.id.txt_toLogin, R.id.txt_toRegister, R.id.img_arrow_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_login:
                mPresenter.toLogin(getLoginPhone(), getLoginPWD(), null);
                break;
            case R.id.txt_findPWD:
                RouterUtil.goToActivity(RouterConfig.USER_FIND);
                break;
            case R.id.txt_register:
            case R.id.txt_toRegister:
                toRegister();
                break;
            case R.id.txt_toLogin:
                toLogin();
                break;
            case R.id.img_arrow_account:
                toggleCountList();
                break;
            default:
                break;
        }
    }

    @Override
    public void showChoiceDialog() {
        LoginChoiceDialog dialog = new LoginChoiceDialog(this);
        dialog.setCancelable(false);
        dialog.setAddClickListener(new LoginChoiceDialog.ItemClickListener() {

            @Override
            public void toManage() {
                UserBean user = GreenDaoUtils.getUser();
                user.setCurRole("2");
                GreenDaoUtils.updateUser(user);
                toHomePage();
            }

            @Override
            public void toBusiness() {
                UserBean user = GreenDaoUtils.getUser();
                user.setCurRole("1");
                GreenDaoUtils.updateUser(user);
                toHomePage();
            }

            @Override
            public void noLogin() {
                UserConfig.clearToken();
            }
        });
        dialog.show();
    }

    @Override
    public void showError(UseCaseException e) {
        if (TextUtils.equals(e.getCode(), CODE_NEED_CHECK)) {
            // 子账号需要验证码登录
            showCheckDialog();
        } else if (TextUtils.equals(e.getMsg(), CODE_UN_REGISTER)) {
            // 帐号未注册
            showTipsDialog(e);
        } else {
            super.showError(e);
        }
    }

    public void showCheckDialog() {
        LoginCheckDialog dialog = new LoginCheckDialog(this, getLoginPhone(), this);
        dialog.setCancelable(false);
        dialog.setAddClickListener(code -> mPresenter.toLogin(getLoginPhone(), getLoginPWD(), code));
        dialog.show();
    }

    public void showTipsDialog(UseCaseException e) {
        SuccessDialog.newBuilder(this)
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("登录失败")
            .setMessage(e.getMessage())
            .setButton((dialog, item) -> {
                if (item == 1) {
                    toRegister();
                }
                dialog.dismiss();
            }, "取消", "去注册")
            .create()
            .show();
    }

    private String getLoginPhone() {
        return mEdtPhone.getText().toString().trim();
    }

    private String getLoginPWD() {
        return mEdtPWD.getText().toString().trim();
    }

    private void toRegister() {
        RouterUtil.goToActivity(RouterConfig.USER_REGISTER);
    }

    private void toLogin() {
        float screenHeight = UIUtils.getScreenHeight(this);
        float translationY = screenHeight - UIUtils.dip2px(130) - mLlContent.getHeight();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mLlContent, "translationY", -translationY);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mLlToLogin, "alpha", 1, 0);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mLlLogin, "alpha", 0, 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1).with(animator2).with(animator3);
        animatorSet.setDuration(500);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mLlToLogin.setVisibility(View.INVISIBLE);
            /*  ViewUtils.getEditFocus(mEdtPhone);
                InputMethodManager imm =
                    (InputMethodManager) LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }*/
            }
        });
        animatorSet.start();
        mLlLogin.setVisibility(View.VISIBLE);
    }

    /**
     * 切换隐藏和显示账号列表
     */
    private void toggleCountList() {
        int visible = mListCountView.getVisibility();
        setAccountListVisible(visible == View.GONE);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        View view = getCurrentFocus();
        if (view instanceof EditText && ((EditText) view).getInputType() == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD)) {
            return;
        }
        GlobalPreference.putParam(Constants.KEYBOARD_KEY, keyboardHeightInPx);
    }

    @Override
    public void onSoftKeyboardClosed() {
        // no-op
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
            mTxtLogin.setEnabled(!TextUtils.isEmpty(getLoginPhone()) && !TextUtils.isEmpty(getLoginPWD()));
        }
    }

    /**
     * 存储已登录过的账号
     */
    private class CountAdapter extends BaseQuickAdapter<AccountBean, BaseViewHolder> {
        public CountAdapter(@Nullable List<AccountBean> data) {
            super(R.layout.list_item_login_account, data);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
            holder.addOnClickListener(R.id.img_del).addOnClickListener(R.id.txt_phone);
            return holder;
        }

        @Override
        protected void convert(BaseViewHolder helper, AccountBean item) {
            helper.setText(R.id.txt_phone, item.getAccount());
        }
    }
}
