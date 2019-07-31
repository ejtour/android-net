package com.hll_sc_app.app.wallet.withdraw;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.wallet.WalletStatusResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/30
 */
@Route(path = RouterConfig.WALLET_WITHDRAW)
public class WithdrawActivity extends BaseLoadActivity implements IWithdrawContract.IWithdrawView {
    public static final int REQ_CODE = 0x95;

    /**
     * @param resp 钱包状态信息
     */
    public static void start(Activity context, WalletStatusResp resp) {
        RouterUtil.goToActivity(RouterConfig.WALLET_WITHDRAW, context, REQ_CODE, resp);
    }

    @Autowired(name = "parcelable", required = true)
    WalletStatusResp walletStatus;
    @BindView(R.id.aww_money_edit)
    EditText mMoneyEdit;
    @BindView(R.id.aww_freeze_tip)
    TextView mFreezeTip;
    @BindView(R.id.aww_confirm)
    TextView mConfirm;
    private Unbinder unbinder;
    private IWithdrawContract.IWithdrawPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_withdraw);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        mPresenter = WithdrawPresenter.newInstance(walletStatus.getSettleUnitID());
        mPresenter.register(this);
        initView();
    }

    private void initView() {
        String frozenMoney = CommonUtils.formatMoney(walletStatus.getFrozenAmount());
        String frozenAmount = String.format("有%s元不可提现", frozenMoney);
        SpannableString spannableString = new SpannableString(frozenAmount);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_666666)),
                1, frozenMoney.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mFreezeTip.setText(spannableString);
        mMoneyEdit.setHint(String.format("可提现金额%s元", CommonUtils.formatMoney(walletStatus.getWithdrawalAmount())));
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.aww_input_all, R.id.aww_freeze_why, R.id.aww_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aww_input_all:
                String text = CommonUtils.formatNumber(walletStatus.getWithdrawalAmount());
                mMoneyEdit.setText(text);
                mMoneyEdit.setSelection(text.length());
                break;
            case R.id.aww_freeze_why:
                TipsDialog.newBuilder(this)
                        .setTitle("不可提现")
                        .setMessage("当天收取的款项不可立即提现哦~")
                        .setButton((dialog, item) -> dialog.dismiss(), "我知道了")
                        .create().show();
                break;
            case R.id.aww_confirm:
                withdraw(Float.parseFloat(mMoneyEdit.getText().toString()));
                break;
        }
    }

    private void withdraw(float money) {
        mPresenter.withdraw(money);
    }

    @Override
    public void withdrawSuccess() {
        SuccessDialog.newBuilder(this)
                .setImageState(R.drawable.ic_dialog_state_success)
                .setImageTitle(R.drawable.ic_dialog_good)
                .setMessageTitle("提现申请已提交至铁金库")
                .setMessage("系统将在1-3个工作日内将金额转至您的银行卡账户\n具体时间以铁金库实际到账日期为准！")
                .setCancelable(false)
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    setResult(RESULT_OK);
                    finish();
                }, "我知道了")
                .create()
                .show();
    }

    @OnTextChanged(value = R.id.aww_money_edit, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged(Editable s) {
        if (s.toString().startsWith(".")) s.insert(0, "0");
        if (!CommonUtils.checkMoenyNum(s.toString()) && s.length() > 1)
            s.delete(s.length() - 1, s.length());
        if (TextUtils.isEmpty(s)) mConfirm.setEnabled(false);
        else {
            float curValue = Float.parseFloat(s.toString());
            mConfirm.setEnabled(curValue > 0 && curValue <= walletStatus.getWithdrawalAmount());
        }
    }

    @OnEditorAction(R.id.aww_money_edit)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO && mConfirm.isEnabled())
            withdraw(Float.parseFloat(mMoneyEdit.getText().toString()));
        return true;
    }
}
