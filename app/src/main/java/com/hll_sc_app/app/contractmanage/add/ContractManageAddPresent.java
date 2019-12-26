package com.hll_sc_app.app.contractmanage.add;

public class ContractManageAddPresent implements IContractManageAddContract.IPresent {
    private IContractManageAddContract.IView mView;

    public static ContractManageAddPresent newInstance() {
        return new ContractManageAddPresent();
    }

    @Override
    public void register(IContractManageAddContract.IView view) {
        mView = view;
    }
}
