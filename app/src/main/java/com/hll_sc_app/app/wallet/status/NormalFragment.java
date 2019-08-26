package com.hll_sc_app.app.wallet.status;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.account.auth.AuthAccountActivity;
import com.hll_sc_app.app.wallet.details.list.DetailsListActivity;
import com.hll_sc_app.app.wallet.recharge.RechargeActivity;
import com.hll_sc_app.app.wallet.withdraw.WithdrawActivity;
import com.hll_sc_app.base.BaseFragment;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.wallet.WalletStatusResp;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.widget.EasingTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */

@Route(path = RouterConfig.WALLET_STATUS_NORMAL)
public class NormalFragment extends BaseFragment {

    public static NormalFragment newInstance(WalletStatusResp resp) {
        return (NormalFragment) RouterUtil.getFragment(RouterConfig.WALLET_STATUS_NORMAL, resp);
    }

    @BindView(R.id.wsn_balance)
    EasingTextView mBalance;
    @BindView(R.id.wsn_withdraw_amount)
    EasingTextView mWithdrawAmount;
    @BindView(R.id.wsn_freeze_amount)
    EasingTextView mFreezeAmount;
    @BindViews({R.id.wsn_tip_bg, R.id.wsn_tip_content, R.id.wsn_tip_arrow})
    List<View> mTipGroup;
    @BindView(R.id.wsn_tip_content)
    TextView mTipContent;
    @BindView(R.id.wsn_tip_arrow)
    ImageView mTipArrow;
    private Unbinder unbinder;
    @Autowired(name = "parcelable")
    WalletStatusResp mResp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_status_normal, container, false);
        unbinder = ButterKnife.bind(this, view);
        ARouter.getInstance().inject(this);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    private void initView() {
        mBalance.easingText(mResp.getBalance(), true);
        mWithdrawAmount.easingText(mResp.getWithdrawalAmount(), true);
        mFreezeAmount.easingText(mResp.getFrozenAmount(), true);
        showTip();
    }

    public void refreshData(WalletStatusResp resp) {
        mResp = resp;
        initView();
    }

    private void showTip() {
        switch (mResp.getCertifyStatus()) {
            case WalletStatusResp.CERTIFY_NOT:
            case WalletStatusResp.CERTIFY_FAIL:
                ButterKnife.apply(mTipGroup, (view, index) -> view.setVisibility(View.VISIBLE));
                mTipContent.setText("请完成企业账务主体实名认证，享受更多服务，马上认证");
                break;
            case WalletStatusResp.CERTIFY_ING:
                ButterKnife.apply(mTipGroup, (view, index) -> view.setVisibility(View.VISIBLE));
                mTipContent.setText("企业实名认证正在审核中，请耐心等待");
                mTipArrow.setVisibility(View.GONE);
                break;
            default:
                ButterKnife.apply(mTipGroup, (view, index) -> view.setVisibility(View.GONE));
                break;
        }
    }

    @OnClick({R.id.wsn_account_btn, R.id.wsn_recharge_btn, R.id.wsn_withdraw_btn, R.id.wsn_details_btn, R.id.wsn_tip_content})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wsn_tip_content:
                if (mResp.getCertifyStatus() == WalletStatusResp.CERTIFY_ING) {
                    return;
                }
                AuthAccountActivity.start(requireActivity());
                break;
            case R.id.wsn_account_btn:
                RouterUtil.goToActivity(RouterConfig.WALLET_ACCOUNT_MY);
                break;
            case R.id.wsn_recharge_btn:
                RechargeActivity.start(requireActivity(), mResp.getSettleUnitID());
                break;
            case R.id.wsn_withdraw_btn:
                switch (mResp.getCertifyStatus()) {
                    case WalletStatusResp.CERTIFY_ING:
                        ToastUtils.showShort(requireActivity(), "企业实名认证正在审核中，请耐心等待，暂不能提现");
                        break;
                    case WalletStatusResp.CERTIFY_NOT:
                    case WalletStatusResp.CERTIFY_FAIL:
                        SpannableString spannableString = new SpannableString("企业账务主体实名认证需要提交企业的\n基本信息、法人信息、结算信息、经营信息\n请您准备好后开始提交认证信息");
                        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimary)),
                                17, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        SuccessDialog.newBuilder(requireActivity())
                                .setImageState(R.drawable.ic_dialog_state_failure)
                                .setImageTitle(R.drawable.ic_dialog_failure)
                                .setMessageTitle("您尚未通过企业认证\n暂时不能提现噢")
                                .setMessage(spannableString)
                                .setButton((dialog, item) -> {
                                    dialog.dismiss();
                                    if (item == 1)
                                        AuthAccountActivity.start(requireActivity());
                                }, "继续准备", "马上认证")
                                .create()
                                .show();
                        break;
                    case WalletStatusResp.CERTIFY_SUCCESS:
                        WithdrawActivity.start(requireActivity(), mResp);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.wsn_details_btn:
                DetailsListActivity.start(mResp.getSettleUnitID());
                break;
            default:
                break;
        }
    }
}
