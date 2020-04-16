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
import com.hll_sc_app.app.wallet.WalletActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.bean.wallet.WalletInfo;

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
    @BindView(R.id.ll_small)
    LinearLayout mLlSmall;
    @BindView(R.id.txt_small)
    TextView mTxtSmall;

    private Unbinder unbinder;

    private IAuthenticationContract.IView mView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        WalletInfo info = mView.getWalletInfo();
        if (WalletActivity.getWalletStatus(info) == WalletInfo.STATUS_VERIFY_FAIL) {//审核失败，要按照申请前的类型进行申请：小微和上面两个进行隔离
            if (info.getUnitType() == 4) {//小微只展示小微
                mLlCompany.setVisibility(View.GONE);
                mLlPerson.setVisibility(View.GONE);
            } else {
                mLlSmall.setVisibility(View.GONE);
            }
        }
        setSelectColor(info.getUnitType());
    }

    @OnClick({R.id.ll_company, R.id.ll_person, R.id.ll_small})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_company:
                mView.getWalletInfo().setUnitType(1);
                mView.getWalletInfo().setReceiverName("");
                mView.getWalletInfo().setIndustryCode("ZP139");
                mView.getWalletInfo().setIndustryName("餐饮/食品-餐饮");
                mView.getWalletInfo().setBusinessCategoryName("餐饮/食品-餐饮");
                setSelectColor(1);
                mView.goToNextStep();
                break;
            case R.id.ll_person:
                mView.getWalletInfo().setUnitType(2);
                mView.getWalletInfo().setReceiverName("");
                mView.getWalletInfo().setIndustryCode("ZP139");
                mView.getWalletInfo().setIndustryName("餐饮/食品-餐饮");
                mView.getWalletInfo().setBusinessCategoryName("餐饮/食品-餐饮");
                setSelectColor(2);
                mView.goToNextStep();
                break;
            case R.id.ll_small:
                mView.getWalletInfo().setUnitType(4);
                mView.getWalletInfo().setReceiverName("");
                mView.getWalletInfo().setIndustryCode("XW001");
                mView.getWalletInfo().setIndustryName("餐饮/食品-餐饮");
                mView.getWalletInfo().setBusinessCategoryName("餐饮/食品-餐饮");
                setSelectColor(4);
                mView.goToNextStep();
                break;
            default:
                break;
        }
    }

    private void setSelectColor(int unitType) {
        mLlCompany.setSelected(unitType == 1);
        mLlPerson.setSelected(unitType == 2);
        mLlSmall.setSelected(unitType == 4);
        mTxtCompany.setTextColor(Color.parseColor(unitType == 1 ? "#ffffff" : "#999999"));
        mTxtPerson.setTextColor(Color.parseColor(unitType == 2 ? "#ffffff" : "#999999"));
        mTxtSmall.setTextColor(Color.parseColor(unitType == 4 ? "#ffffff" : "#999999"));
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
