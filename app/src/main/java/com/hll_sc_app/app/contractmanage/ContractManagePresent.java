package com.hll_sc_app.app.contractmanage;

public class ContractManagePresent implements IContractManageContract.IPresent {
    private IContractManageContract.IView mView;

    public static ContractManagePresent newInstance() {
        return new ContractManagePresent();
    }

    @Override
    public void register(IContractManageContract.IView view) {
        mView = view;
    }
}
