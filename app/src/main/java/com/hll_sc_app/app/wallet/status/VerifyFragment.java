package com.hll_sc_app.app.wallet.status;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.account.create.CreateAccountActivity;
import com.hll_sc_app.base.BaseFragment;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.wallet.WalletStatusResp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */
@Route(path = RouterConfig.WALLET_STATUS_VERIFY)
public class VerifyFragment extends BaseFragment {
    public static VerifyFragment newInstance(int status) {
        return (VerifyFragment) RouterUtil.getFragment(RouterConfig.WALLET_STATUS_VERIFY, status);
    }

    @BindView(R.id.wsv_icon)
    ImageView mIcon;
    @BindView(R.id.wsv_result_title)
    TextView mResultTitle;
    @BindView(R.id.wsv_result_desc)
    TextView mResultDesc;
    @BindView(R.id.wsv_btn)
    TextView mBtn;
    @Autowired(name = "object0", required = true)
    int mStatus;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_status_verify, container, false);
        unbinder = ButterKnife.bind(this, view);
        ARouter.getInstance().inject(this);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView() {
        switch (mStatus) {
            case WalletStatusResp.STATUS_VERIFYING:
                mIcon.setImageResource(R.drawable.ic_dialog_good);
                mResultTitle.setText("您的账号申请已提交至铁金库！");
                mResultDesc.setText("审核时间一般为1-3个工作日\n请耐心等待，我们将尽快审核您的相关信息！");
                mBtn.setVisibility(View.GONE);
                break;
            case WalletStatusResp.STATUS_VERIFY_FAIL:
                mIcon.setImageResource(R.drawable.ic_dialog_failure);
                mResultTitle.setText("您的账号申请暂未通过！");
                mResultTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_ed5655));
                mResultDesc.setText("您提交的铁金库账号申请资料因\n审核未通过！");
                mBtn.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.wsv_btn)
    public void reApply() {
        CreateAccountActivity.start(requireActivity());
    }
}
