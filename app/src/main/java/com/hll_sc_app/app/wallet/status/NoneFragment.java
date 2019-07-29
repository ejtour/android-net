package com.hll_sc_app.app.wallet.status;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseFragment;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.wallet.WalletStatusResp;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */
@Route(path = RouterConfig.WALLET_STATUS_NONE)
public class NoneFragment extends BaseFragment {
    public static NoneFragment newInstance() {
        return (NoneFragment) RouterUtil.getFragment(RouterConfig.WALLET_STATUS_NONE);
    }
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_status_none, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.wsn_open_account_btn, R.id.wsn_bind_account_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wsn_open_account_btn:
                RouterUtil.goToActivity(RouterConfig.WALLET_ACCOUNT_OPEN);
                break;
            case R.id.wsn_bind_account_btn:
                RouterUtil.goToActivity(RouterConfig.WALLET_ACCOUNT_BIND);
                break;
            default:
                break;
        }
    }
}
