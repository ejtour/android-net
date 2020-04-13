package com.hll_sc_app.app.wallet;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.details.list.DetailsListActivity;
import com.hll_sc_app.app.wallet.recharge.RechargeActivity;
import com.hll_sc_app.app.wallet.withdraw.WithdrawActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.RefreshWalletStatus;
import com.hll_sc_app.bean.wallet.WalletInfo;
import com.hll_sc_app.citymall.util.CommonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 钱包入口页面-新版
 *
 * @author zc
 */
@Route(path = RouterConfig.WALLET, extras = Constant.LOGIN_EXTRA)
public class WalletActivity extends BaseLoadActivity implements IWalletContract.IView {
    @BindView(R.id.cst_status_unusable)
    ConstraintLayout mUnsableUArea;
    @BindView(R.id.cst_status_usable)
    ConstraintLayout mUsableUArea;
    @BindView(R.id.txt_status)
    TextView mTxtStatus;
    @BindView(R.id.txt_sub_status)
    TextView mTxtSubStatus;
    @BindView(R.id.txt_option)
    TextView mTxtOption;
    @BindView(R.id.txt_balance)
    TextView mTxtBalance;
    @BindView(R.id.txt_usable)
    TextView mTxtUsable;
    @BindView(R.id.txt_unusable)
    TextView mTxtUnusable;
    @BindView(R.id.ll_container)
    LinearLayout mLLContainer;
    private ValueAnimator animator_balance;
    private ValueAnimator animator_withdrawal;
    private ValueAnimator animator_frozen;
    private IWalletContract.IPresent mPresent;
    private Unbinder unbinder;
    private WalletInfo mWalletInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        mPresent = WalletPresent.newInstance();
        mPresent.register(this);
        mPresent.getWalletInfo(true);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getInfoSuccess(WalletInfo walletInfo) {
        mLLContainer.setVisibility(View.VISIBLE);
        mWalletInfo = walletInfo;
        int status = getWalletStatus(walletInfo);
        switch (status) {
            case WalletInfo.STATUS_NOT_OPEN:
                mUnsableUArea.setVisibility(View.VISIBLE);
                mUsableUArea.setVisibility(View.GONE);
                mTxtStatus.setText("您尚未激活企业钱包");
                mTxtSubStatus.setText("点击立刻激活前往开通");
                mTxtOption.setText("立刻激活");
                mTxtOption.setOnClickListener(v -> {
                    if (TextUtils.isEmpty(mWalletInfo.getSettleUnitID())) {
                        RouterUtil.goToActivity(RouterConfig.ACTIVTY_WALLET_CREATE_ACCOUNT);
                    } else {
                        RouterUtil.goToActivity(RouterConfig.ACTIVITY_WALLET_AUTHEN_ACCOUNT);
                    }
                });
                break;
            case WalletInfo.STATUS_VERIFYING:
                mUnsableUArea.setVisibility(View.VISIBLE);
                mUsableUArea.setVisibility(View.GONE);
                mTxtOption.setVisibility(View.GONE);
                mTxtStatus.setText("您的企业钱包在审核中");
                mTxtSubStatus.setText("审核时间一般为1-3个工作日");
                break;
            case WalletInfo.STATUS_VERIFY_FAIL:
                mUnsableUArea.setVisibility(View.VISIBLE);
                mUsableUArea.setVisibility(View.GONE);
                mTxtStatus.setText("您的申请暂未通过！");
                mTxtOption.setText("重新申请");
                mTxtOption.setOnClickListener(v -> {
                    RouterUtil.goToActivity(RouterConfig.ACTIVITY_WALLET_AUTHEN_ACCOUNT);
                });
                SpannableString phone = new SpannableString("请联系客服：" + getString(R.string.contact_phone));
                phone.setSpan(new ForegroundColorSpan(Color.parseColor("#5695D2")), 6, phone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                phone.setSpan(new UnderlineSpan(), 6, phone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTxtSubStatus.setText(phone);
                mTxtSubStatus.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri info = Uri.parse("tel:" + getString(R.string.contact_phone));
                    intent.setData(info);
                    startActivity(intent);
                });
                break;
            case WalletInfo.STATUS_VERIFY_SUCCESS:
                break;
            case WalletInfo.STATUS_AUTHEN_SUCCESS:
                mUnsableUArea.setVisibility(View.GONE);
                mUsableUArea.setVisibility(View.VISIBLE);
                animator_balance = generatorAnimator(mTxtBalance, walletInfo.getSettleBalance());
                animator_withdrawal = generatorAnimator(mTxtUsable, walletInfo.getWithdrawalAmount());
                animator_frozen = generatorAnimator(mTxtUnusable, walletInfo.getFreezeBalance());
                animator_balance.start();
                animator_withdrawal.start();
                animator_frozen.start();
                break;
            default:
                break;
        }
    }

    public static int getWalletStatus(WalletInfo walletInfo) {
        if ((walletInfo.getOpenPayStatus() == 40 ||
                walletInfo.getSignStatus() == 2)) {
            return WalletInfo.STATUS_AUTHEN_SUCCESS;//钱包页面
        } else if ((walletInfo.getOpenPayStatus() == 10 ||
                walletInfo.getOpenPayStatus() == 0) &&
                walletInfo.getSignStatus() == 0 &&
                walletInfo.getProcessStatus() == 0) {//未激活
            return WalletInfo.STATUS_NOT_OPEN;
        } else if (walletInfo.getOpenPayStatus() == 30 || walletInfo.getProcessStatus() == 4) {//未通过
            return WalletInfo.STATUS_VERIFY_FAIL;
        } else {//审核中
            return WalletInfo.STATUS_VERIFYING;
        }
    }

    private ValueAnimator generatorAnimator(TextView textView, float mEndValue) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, mEndValue);
        animator.setDuration(500);
        animator.addUpdateListener(animation -> {
            String value = animation.getAnimatedValue().toString();
            textView.setText(CommonUtils.formatNumber(value));
        });
        return animator;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @OnClick({R.id.txt_my_account, R.id.txt_recharge, R.id.txt_cash, R.id.txt_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_my_account:
                RouterUtil.goToActivity(RouterConfig.WALLET_ACCOUNT_MY);
                break;
            case R.id.txt_recharge:
                RechargeActivity.start(this, mWalletInfo.getSettleUnitID());
                break;
            case R.id.txt_cash:
                WithdrawActivity.start(this, mWalletInfo);
                break;
            case R.id.txt_detail:
                DetailsListActivity.start(mWalletInfo.getSettleUnitID());
                break;
            default:
                break;
        }
    }


    @Subscribe(sticky = true)
    public void SubscribeEvent(RefreshWalletStatus refreshWalletStatus) {
        mPresent.getWalletInfo(refreshWalletStatus.isShowLoading());
    }

}
