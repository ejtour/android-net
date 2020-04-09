package com.hll_sc_app.app.contractmanage.contractmount;

public class ContractMountPresent implements IContractMountContract.IPresent {
    private IContractMountContract.IView mView;

    @Override
    public void register(IContractMountContract.IView view) {
        mView = view;
    }

    public static ContractMountPresent newInstance() {
        return new ContractMountPresent();
    }
}
