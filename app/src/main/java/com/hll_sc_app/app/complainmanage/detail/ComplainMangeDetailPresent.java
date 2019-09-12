package com.hll_sc_app.app.complainmanage.detail;

public class ComplainMangeDetailPresent implements IComplainMangeDetailContract.IPresent {
    private IComplainMangeDetailContract.IView mView;

    public static ComplainMangeDetailPresent newInstance() {
        return new ComplainMangeDetailPresent();
    }

    @Override
    public void register(IComplainMangeDetailContract.IView view) {
        mView = view;
    }
}
