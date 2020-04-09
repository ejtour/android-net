package com.hll_sc_app.app.contractmanage.contractmount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContractMountActivity  extends BaseLoadActivity implements IContractMountContract.IView{
    private Unbinder unbinder;

    public static void start() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_mount);
        unbinder= ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
