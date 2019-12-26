package com.hll_sc_app.app.contractmanage.detail;

public class ContractManageDetailPresent implements IContractManageDetailContract.IPresent {
    private IContractManageDetailContract.IView mView;

    public static ContractManageDetailPresent newInstance() {
        return new ContractManageDetailPresent();
    }

    @Override
    public void register(IContractManageDetailContract.IView view) {
        mView = view;
    }
}
