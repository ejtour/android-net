package com.hll_sc_app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.bean.AccountBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ClearEditText;
import com.hll_sc_app.citymall.util.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.Optional;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/7
 */

public class LoginView extends LinearLayout {
    @BindView(R.id.vl_phone_edit)
    ClearEditText mPhoneEdit;
    @BindView(R.id.vl_account_arrow)
    ImageView mAccountArrow;
    @BindView(R.id.vl_account_list)
    RecyclerView mAccountList;
    @BindView(R.id.vl_password_edit)
    ClearEditText mPasswordEdit;
    @BindView(R.id.vl_login)
    TextView mLogin;
    @BindView(R.id.vl_operation)
    ViewGroup mOperation;

    public LoginView(Context context) {
        this(context, null);
    }

    public LoginView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
        setFocusable(true);
        setFocusableInTouchMode(true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoginView);
        int layout = array.getResourceId(R.styleable.LoginView_loginLayout, R.layout.view_login);
        array.recycle();
        View view = View.inflate(context, layout, this);
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        List<AccountBean> accountBeans = GreenDaoUtils.queryAllAccount();
        boolean showArrow = accountBeans.size() > 0;
        setPhoneEditStatus(showArrow);
        if (showArrow) {
            mPhoneEdit.setText(accountBeans.get(0).getAccount());
            CountAdapter countAdapter = new CountAdapter(accountBeans);
            mAccountList.setAdapter(countAdapter);
            countAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                CountAdapter cAdapter = (CountAdapter) adapter;
                AccountBean accountBean = cAdapter.getItem(position);
                if (accountBean == null) return;
                if (view.getId() == R.id.txt_phone) {
                    mPhoneEdit.setText(accountBean.getAccount());
                    mPhoneEdit.setSelection(accountBean.getAccount().length());
                    mPasswordEdit.setText("");
                    setAccountListVisible(false);
                } else if (view.getId() == R.id.img_del) {
                    cAdapter.remove(position);
                    GreenDaoUtils.deleteAccount(accountBean.getAccount());
                    if (cAdapter.getData().size() == 0) {
                        setPhoneEditStatus(false);
                        setAccountListVisible(false);
                    }
                }
            });
        }
    }

    private void setPhoneEditStatus(boolean showArrow) {
        mAccountArrow.setVisibility(showArrow ? VISIBLE : GONE);
        mPhoneEdit.setPadding(UIUtils.dip2px(15), 0, UIUtils.dip2px(showArrow ? 60 : 15), 0);
    }

    @OnFocusChange(R.id.vl_phone_edit)
    public void onFocusChange(boolean hasFocus) {
        if (hasFocus) {
            setAccountListVisible(false);
        }
        mAccountArrow.setSelected(hasFocus);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mLogin.setOnClickListener(l);
    }

    @OnClick(R.id.vl_account_arrow)
    public void toggleArrow() {
        setAccountListVisible(mAccountList.getVisibility() == GONE);
    }

    private void setAccountListVisible(boolean visible) {
        if (visible) {
            ViewUtils.clearEditFocus(this);
            requestFocus();
        }
        mAccountList.setVisibility(visible ? VISIBLE : GONE);
        mAccountArrow.setRotation(visible ? -90 : 90);
        mPasswordEdit.setVisibility(visible ? GONE : VISIBLE);
        mLogin.setVisibility(visible ? GONE : VISIBLE);
        if (mOperation != null) {
            mOperation.setVisibility(visible ? GONE : VISIBLE);
        }
    }

    @OnTextChanged(value = {R.id.vl_phone_edit, R.id.vl_password_edit}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged() {
        mLogin.setEnabled(!TextUtils.isEmpty(mPhoneEdit.getText().toString().trim()) && !TextUtils.isEmpty(mPasswordEdit.getText().toString().trim()));
    }

    @Optional
    @OnClick(R.id.vl_find_password)
    public void findPassword() {
        RouterUtil.goToActivity(RouterConfig.USER_FIND);
    }

    @OnClick(R.id.vl_register)
    public void toRegister() {
        RouterUtil.goToActivity(RouterConfig.USER_REGISTER);
    }

    /**
     * 存储已登录过的账号
     */
    private static class CountAdapter extends BaseQuickAdapter<AccountBean, BaseViewHolder> {
        CountAdapter(@Nullable List<AccountBean> data) {
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

    public String getLoginPhone() {
        return mPhoneEdit.getText().toString().trim();
    }

    public String getLoginPassword() {
        return mPasswordEdit.getText().toString().trim();
    }
}
