package com.hll_sc_app.app.paymanage.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.shopsettlement.AccountPeriodSelectWindow;
import com.hll_sc_app.app.cooperation.detail.shopsettlement.CooperationShopSettlementActivity;
import com.hll_sc_app.app.paymanage.PayManagePresenter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.dialog.InputDialog;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付管理-设置账期日
 *
 * @author zhuyingsong
 * @date 2019/8/9
 */
@Route(path = RouterConfig.PAY_MANAGE_ACCOUNT, extras = Constant.LOGIN_EXTRA)
public class PayAccountManageActivity extends BaseLoadActivity implements PayAccountManageContract.IAccountView {
    @BindView(R.id.txt_accountPeriod)
    TextView mTxtAccountPeriod;
    @BindView(R.id.txt_settleDate)
    TextView mTxtSettleDate;
    @Autowired(name = "object0")
    String mPayTermType;
    @Autowired(name = "object1")
    String mPayTerm;
    @Autowired(name = "object2")
    String mSettleDate;
    @BindView(R.id.switch_pay_type)
    SwitchButton mSwitchPayType;
    @BindView(R.id.rl_accountPeriod)
    RelativeLayout mRlAccountPeriod;
    @BindView(R.id.rl_settleDate)
    RelativeLayout mRlSettleDate;
    @BindView(R.id.txt_alter)
    TextView mTxtAlter;
    @BindView(R.id.ll_button_bottom)
    LinearLayout mLlButtonBottom;

    private PayAccountManagePresenter mPresenter;

    public static void start(String payTermType, String payTerm, String settleDate) {
        RouterUtil.goToActivity(RouterConfig.PAY_MANAGE_ACCOUNT, payTermType, payTerm, settleDate);
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
        showAccountView();
    }

    public void showAccountView() {
        //账期日checkbox
        mSwitchPayType.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PayManagePresenter.editSettlement("2", isChecked ? "1" : "0", new SimpleObserver<MsgWrapper<Object>>(this) {
                @Override
                public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                    showToast(objectMsgWrapper.getMessage());
                    showContent(isChecked);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mSwitchPayType.setCheckedNoEvent(!isChecked);
                    showToast(e.getMessage());
                }
            });
        });
        mSwitchPayType.setCheckedNoEvent(!TextUtils.equals("0", mPayTermType));
        showContent(!TextUtils.equals("0", mPayTermType));

        // 账期日
        if (TextUtils.equals(mPayTermType, CooperationShopSettlementActivity.TERM_WEEK)) {
            // 周结
            mTxtAccountPeriod.setTag(R.id.date_start, 1);
            mTxtAccountPeriod.setTag(R.id.date_end, mPayTerm);
            mTxtAccountPeriod.setText(String.format("周结,%s",
                CooperationShopSettlementActivity.getPayTermStr(CommonUtils.getInt(mPayTerm))));
        } else if (TextUtils.equals(mPayTermType, CooperationShopSettlementActivity.TERM_MONTH)) {
            // 月结
            mTxtAccountPeriod.setTag(R.id.date_start, 2);
            mTxtAccountPeriod.setTag(R.id.date_end, mPayTerm);
            mTxtAccountPeriod.setText(String.format("月结，每月%s号", mPayTerm));
        } else {
            mTxtAccountPeriod.setText(null);
        }

        // 结算日
        mTxtSettleDate.setText(String.format("对账单产生后%s日", mSettleDate));
        mTxtSettleDate.setTag(mSettleDate);
    }

    private void showContent(boolean isShow) {
        mRlAccountPeriod.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mRlSettleDate.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mTxtAlter.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mLlButtonBottom.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.img_close, R.id.txt_save, R.id.rl_accountPeriod, R.id.rl_settleDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                toSave();
                break;
            case R.id.rl_accountPeriod:
                showAccountPeriodWindow();
                break;
            case R.id.rl_settleDate:
                showInputDialog();
                break;
            default:
                break;
        }
    }

    private void toSave() {
        String payTermType;
        String payTerm = null;
        String settleDate = null;
        if (mTxtAccountPeriod.getTag(R.id.date_start) != null) {
            // 账期日类型,0-未设置,1-按周,2-按月
            payTermType = String.valueOf(mTxtAccountPeriod.getTag(R.id.date_start));
        } else {
            payTermType = "0";
        }
        if (mTxtAccountPeriod.getTag(R.id.date_end) != null) {
            payTerm = String.valueOf(mTxtAccountPeriod.getTag(R.id.date_end));
        }
        if (mTxtSettleDate.getTag() != null) {
            settleDate = String.valueOf(mTxtSettleDate.getTag());
        }
        mPresenter.editAccount(payTermType, payTerm, settleDate);
    }

    private void showAccountPeriodWindow() {
        AccountPeriodSelectWindow window = new AccountPeriodSelectWindow(this);
        window.setSelectListener((payTermType, payTerm) -> {
            if (TextUtils.equals(payTermType, "周结")) {
                mTxtAccountPeriod.setTag(R.id.date_start, 1);
                mTxtAccountPeriod.setTag(R.id.date_end, payTerm);
                mTxtAccountPeriod.setText(String.format("周结,%s",
                    CooperationShopSettlementActivity.getPayTermStr(payTerm)));
            } else {
                mTxtAccountPeriod.setTag(R.id.date_start, 2);
                mTxtAccountPeriod.setTag(R.id.date_end, payTerm);
                mTxtAccountPeriod.setText(String.format("月结，每月%s号", payTerm));
            }
        });
        window.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void showInputDialog() {
        InputDialog.newBuilder(this)
            .setCancelable(false)
            .setTextTitle("输入结算日")
            .setHint("输入在对账单产生后多少日")
            .setInputType(InputType.TYPE_CLASS_NUMBER)
            .setMaxLength(7)
            .setButton((dialog, item) -> {
                if (item == 1) {
                    if (TextUtils.isEmpty(dialog.getInputString())) {
                        showToast("输入不能为空");
                        return;
                    }
                    mTxtSettleDate.setText(String.format("对账单产生后%s日", dialog.getInputString()));
                    mTxtSettleDate.setTag(dialog.getInputString());
                }
                dialog.dismiss();
            }, "取消", "确定")
            .create()
            .show();
    }

    @Override
    public void editSuccess() {
        showToast("修改支付方式成功");
        ARouter.getInstance().build(RouterConfig.PAY_MANAGE)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }
}
