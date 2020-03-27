package com.hll_sc_app.app.wallet.authentication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BusinessTypeFragment extends BaseLazyFragment implements IAuthenticationContract.IFragment {
    @BindView(R.id.ll_company)
    LinearLayout mLlCompany;
    @BindView(R.id.ll_person)
    LinearLayout mLlPerson;
    @BindView(R.id.txt_person)
    TextView mTxtPerson;
    @BindView(R.id.txt_company)
    TextView mTxtCompany;

    private Unbinder unbinder;

    private IAuthenticationContract.IView mView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setSelectColor(mView.getWalletInfo().getUnitType());
    }


    @OnClick({R.id.ll_company, R.id.ll_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_company:
                mView.getWalletInfo().setUnitType(1);
                mView.getWalletInfo().setReceiverName("");
                setSelectColor(1);
                mView.goToNextStep();
                break;
            case R.id.ll_person:
                mView.getWalletInfo().setUnitType(99);
                mView.getWalletInfo().setReceiverName("");
                setSelectColor(99);
                mView.goToNextStep();
                break;
            default:
                break;
        }
    }

    private void setSelectColor(int unitType) {
        mLlCompany.setSelected(unitType == 1);
        mLlPerson.setSelected(unitType == 99);
        mTxtCompany.setTextColor(Color.parseColor(unitType == 1 ? "#ffffff" : "#999999"));
        mTxtPerson.setTextColor(Color.parseColor(unitType == 99 ? "#ffffff" : "#999999"));
    }

    @Override
    protected void onVisible() {
        super.onVisible();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet_business_type, null);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void registerView(IAuthenticationContract.IView view) {
        mView = view;
    }
}
