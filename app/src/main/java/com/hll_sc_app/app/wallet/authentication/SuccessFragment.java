package com.hll_sc_app.app.wallet.authentication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 结算信息
 *
 * @author zc
 */
public class SuccessFragment extends BaseLazyFragment implements IAuthenticationContract.IFragment {

    @BindView(R.id.txt_process)
    TextView mTxtAlter;
    @BindView(R.id.txt_ok)
    TextView mTxtOk;


    private Unbinder unbinder;
    private IAuthenticationContract.IView mView;

    @Override
    public void registerView(IAuthenticationContract.IView view) {
        mView = view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_open_account_success, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        String alter = "审核时间一般为1-3个工作日\n" +
                "请耐心等待，我们将尽快审核您的相关信息！\n";
        mTxtAlter.setText(alter);
        mTxtOk.setOnClickListener(v -> {
            mView.goToNextStep();
        });
    }
}
