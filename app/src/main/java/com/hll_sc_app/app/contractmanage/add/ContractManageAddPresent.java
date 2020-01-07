package com.hll_sc_app.app.contractmanage.add;

import com.hll_sc_app.api.ContractService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ContractManageAddPresent implements IContractManageAddContract.IPresent {
    private IContractManageAddContract.IView mView;

    public static ContractManageAddPresent newInstance() {
        return new ContractManageAddPresent();
    }

    @Override
    public void register(IContractManageAddContract.IView view) {
        mView = view;
    }


    @Override
    public void addContract() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("actionType", mView.getActionType())
                .put("attachment", mView.getAttachment())
                .put("contractCode", mView.getContractCode())
                .put("contractName", mView.getContractName())
                .put("endDate", mView.getEndDate())
                .put("groupID", userBean.getGroupID())
                .put("groupName", userBean.getGroupName())
                .put("purchaserID", mView.getPurchaserID())
                .put("purchaserName", mView.getPurchaserName())
                .put("purchaserType", String.valueOf(mView.getPurchaserType()))
                .put("remarks", mView.getRemarks())
                .put("signDate", mView.getSignDate())
                .put("signEmployeeName", mView.getSignEmployeeName())
                .put("signEmployeeID", mView.getSignEmployeeID())
                .put("startDate", mView.getStartDate())
                .create();

        ContractService.INSTANCE
                .addContract(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.addSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
