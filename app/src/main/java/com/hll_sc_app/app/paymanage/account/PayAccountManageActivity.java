package com.hll_sc_app.app.paymanage.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.AccountPayView;
import com.hll_sc_app.widget.TitleBar;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 支付管理-设置账期日
 *
 * @author zhuyingsong
 * @date 2019/8/9
 */
@Route(path = RouterConfig.PAY_MANAGE_ACCOUNT, extras = Constant.LOGIN_EXTRA)
public class PayAccountManageActivity extends BaseLoadActivity implements PayAccountManageContract.IAccountView {
    @Autowired(name = "object0")
    String mPayTermType;
    @Autowired(name = "object1")
    String mPayTerm;
    @Autowired(name = "object2")
    String mSettleDate;
    @Autowired(name = "object3")
    boolean mIsChecked;
    @BindView(R.id.pma_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.pma_account_pay)
    AccountPayView mPayView;
    @BindView(R.id.switch_pay_type)
    SwitchButton mSwitchPayType;
    @BindView(R.id.ll_button_bottom)
    LinearLayout mLlButtonBottom;

    private PayAccountManagePresenter mPresenter;

    /**
     * @param payTermType 账期支付方式;0-未设置,1-按周,2-按月
     * @param payTerm     账期支付具体日期
     * @param settleDate  结算日
     * @param checked     开启状态
     */
    public static void start(String payTermType, String payTerm, String settleDate, boolean checked) {
        RouterUtil.goToActivity(RouterConfig.PAY_MANAGE_ACCOUNT, payTermType, payTerm, settleDate, checked);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_manage_account);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = PayAccountManagePresenter.newInstance();
        mPresenter.register(this);
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        showAccountView();
    }

    @OnCheckedChanged(R.id.switch_pay_type)
    public void toggle(boolean isChecked){
        mPresenter.toggle(isChecked);
    }

    public void showAccountView() {
        //账期日checkbox
        mSwitchPayType.setCheckedNoEvent(mIsChecked);
        showContent(mSwitchPayType.isChecked());
        mPayView.setData(mPayTermType, mPayTerm, mSettleDate);
    }

    private void showContent(boolean isShow) {
        mPayView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mLlButtonBottom.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.txt_save)
    void toSave() {
        String payTermType = mPayView.getType();
        String payTerm = mPayView.getPeriod();
        String settleDate = mPayView.getSettleDate();
        mPresenter.editAccount(payTermType, payTerm, settleDate);
    }

    @Override
    public void onBackPressed() {
        ARouter.getInstance().build(RouterConfig.PAY_MANAGE)
                .setProvider(new LoginInterceptor())
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .navigation(this);
    }

    @Override
    public void editSuccess() {
        showToast("修改支付方式成功");
        onBackPressed();
    }

    @Override
    public void toggleSuccess() {
        showContent(mSwitchPayType.isChecked());
    }

    @Override
    public void toggleFailure() {
        mSwitchPayType.setCheckedNoEvent(!mSwitchPayType.isChecked());
    }
}
